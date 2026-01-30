package me.clefal.whats_your_build.client.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.data.buildobject.Build;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoadoutsClientHandler {
    public final static Path buildsLocation = Path.of(Minecraft.getInstance().gameDirectory + "/wyb/builds/");

    public static void writeToLocal(Build build) throws IOException {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            UUID uuid = player.getUUID();
            Path playerFolder = buildsLocation.resolve(uuid.toString());
            Files.createDirectories(playerFolder); // 确保文件夹存在

            String buildFileName = build.name + ".json";
            Path buildFile = playerFolder.resolve(buildFileName);

            JsonElement json = Build.CODEC.encodeStart(JsonOps.INSTANCE, build)
                    //? if 1.20.1 {
                    /*.getOrThrow(false, x -> Constants.LOG.error(x));
                     *///?} else {
                    .getOrThrow();
            //?}


            try (BufferedWriter writer = Files.newBufferedWriter(buildFile)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(json, writer);

            }
        }
    }

    public static void deleteFromLocal(String buildFileName) throws IOException {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            UUID uuid = player.getUUID();

            Path playerFolder = buildsLocation.resolve(uuid.toString());

            if (!buildFileName.endsWith(".json")) {
                buildFileName += ".json";
            }
            Path buildFile = playerFolder.resolve(buildFileName);

            if (Files.exists(buildFile)) {
                Files.delete(buildFile);
                Constants.LOG.info("Deleted build file: {}", buildFile);
            } else {
                Constants.LOG.warn("Build file not found: {}", buildFile);
            }
        }
    }

    public static List<Build> readAllFromLocal(UUID playerUUID) throws IOException {
        List<Build> builds = new ArrayList<>();

        Path playerFolder = buildsLocation.resolve(playerUUID.toString());

        if (!Files.exists(playerFolder) || !Files.isDirectory(playerFolder)) {
            Constants.LOG.debug("non-exist directory: {}, return empty list.", playerFolder);
            Files.createDirectories(playerFolder);
            return builds;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(playerFolder, "*.json")) {
            for (Path file : stream) {
                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    Build.CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(reader))
                            .resultOrPartial(string -> Constants.LOG.error("Invalid element while loading build file {}: {}", file, string))
                            .map(Pair::getFirst)
                            .ifPresent(builds::add);
                } catch (Exception e) {
                    Constants.LOG.error("Failed to load build file: {}", file, e);
                }
            }
        }

        return builds;
    }


    public static void openBuildFolder() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            UUID uuid = player.getUUID();

            Path playerFolder = buildsLocation.resolve(uuid.toString());
            try {
                Files.createDirectories(playerFolder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Util.getPlatform().openUri(playerFolder.toUri());
        }

    }

    public static boolean rename(String oldName, String newName) throws IOException {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return false;

        UUID uuid = player.getUUID();
        Path playerFolder = buildsLocation.resolve(uuid.toString());
        Files.createDirectories(playerFolder);

        Path backupFolder = playerFolder.resolve("backup");
        Files.createDirectories(backupFolder);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");

        Path oldPath = playerFolder.resolve(oldName + ".json");
        Path newPath = playerFolder.resolve(newName + ".json");

        if (!Files.exists(oldPath)) {
            player.sendSystemMessage(Component.translatable("wyb.screen.loadout.rename_info.3", oldPath.toString()));
            return false;
        } else {
            if (Files.exists(newPath)) {
                player.sendSystemMessage(Component.translatable("wyb.screen.loadout.rename_info.duplicated_name", newPath.toString()));
                return false;
            }

            Path backupPath = backupFolder.resolve(
                    oldName + "_" + LocalDateTime.now().format(formatter) + ".json"
            );

            Files.copy(oldPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            player.sendSystemMessage(Component.translatable("wyb.screen.loadout.rename_info.2", backupPath.toString()));


            Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
            player.sendSystemMessage(Component.translatable("wyb.screen.loadout.rename_info.1", oldPath.toString(), newPath.toString()));

            return true;
        }


    }

}
