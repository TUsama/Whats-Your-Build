package me.clefal.whats_your_build.client.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.data.buildobject.Build;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoadoutsClientHandler {
    public final static Path buildsLocation = Path.of(Minecraft.getInstance().gameDirectory + "/wyb/builds/");

    public static void writeToLocal(Build build) throws IOException {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            UUID uuid = player.getUUID();

            // 创建以玩家 UUID 命名的文件夹路径
            Path playerFolder = buildsLocation.resolve(uuid.toString());
            Files.createDirectories(playerFolder); // 确保文件夹存在

            // 创建构筑文件的路径（你可以根据 build 中的某个字段命名）
            String buildFileName = build.name + ".json";
            Path buildFile = playerFolder.resolve(buildFileName);

            // 将 build 编码为 JSON
            JsonElement json = Build.CODEC.encodeStart(JsonOps.INSTANCE, build)
                    //? if 1.20.1 {
                    /*.getOrThrow(false, Constants.LOG::error);
                    *///?} else {
                    .getOrThrow();
                    //?}


            // 写入文件
            try (BufferedWriter writer = Files.newBufferedWriter(buildFile)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(json, writer);
            }
        }
    }

    public static List<Build> readAllFromLocal(UUID playerUUID) throws IOException {
        List<Build> builds = new ArrayList<>();

        Path playerFolder = buildsLocation.resolve(playerUUID.toString());

        if (!Files.exists(playerFolder) || !Files.isDirectory(playerFolder)) {
            Constants.LOG.debug("non-exist directory! : {}, return empty list.", playerFolder);
            return builds;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(playerFolder, "*.json")) {
            for (Path file : stream) {
                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    Build.CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(reader))
                            .resultOrPartial(string -> Constants.LOG.error("Invalid element: {}", string))
                            .map(Pair::getFirst)
                            .ifPresent(builds::add);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return builds;
    }
}
