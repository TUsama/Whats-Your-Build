package me.clefal.whats_your_build.client.keybind;

import com.clefal.nirvana_lib.relocated.io.vavr.Lazy;
import com.clefal.nirvana_lib.utils.DevUtils;
import com.clefal.nirvana_lib.utils.NetworkUtils;
import com.mojang.blaze3d.platform.InputConstants;
import me.clefal.whats_your_build.config.WYBClientConfig;
import me.clefal.whats_your_build.network.c2s.C2SAskBuildPacket;
import me.clefal.whats_your_build.network.c2s.C2SSendGlobalBuildPacket;
import me.clefal.whats_your_build.platform.Services;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.apache.http.io.SessionOutputBuffer;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.function.Consumer;

public class WYBKeys {


    static final List<Lazy<WYBKey>> KEYS = List.of(

            Lazy.of(() -> new WYBKey("key.wyb.check", GLFW.GLFW_MOUSE_BUTTON_MIDDLE, client -> {
                HitResult hitResult = client.hitResult;
                DevUtils.runOnDifference(() -> {
                    if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY && client.hitResult instanceof EntityHitResult entityHitResult) {
                        NetworkUtils.sendToServer(new C2SAskBuildPacket(entityHitResult.getEntity().getUUID()));
                    }
                }, () -> {
                    if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY && client.hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof Player player) {
                        NetworkUtils.sendToServer(new C2SAskBuildPacket(player.getUUID()));
                    }
                });

            })),
            Lazy.of(() -> new WYBKey("key.wyb.modifier_key", GLFW.GLFW_KEY_LEFT_ALT, client -> {}, InputConstants.Type.KEYSYM)),
            Lazy.of(() -> new WYBKey("key.wyb.send_build_to_chat", GLFW.GLFW_KEY_I, client -> {}, InputConstants.Type.KEYSYM))


    );



    public static void registerAllKey(Consumer<List<KeyMapping>> handle) {
        handle.accept(KEYS.stream().map(x -> x.get().keyBinding).toList());
    }

    public static void consumerKeys() {
        Lazy<WYBKey> check = KEYS.get(0);
        if (check.get().keyBinding.consumeClick()) {
            Minecraft client = Minecraft.getInstance();
            check.get().onPress.execute(client);
        }
        if (KEYS.get(1).get().keyBinding.consumeClick()){
            if (KEYS.get(2).get().keyBinding.consumeClick()) {
                NetworkUtils.sendToServer(new C2SSendGlobalBuildPacket());
            }
        }

    }

    public static class WYBKey {

        final KeyMapping keyBinding;
        final OnPress onPress;

        private WYBKey(String keyName, int keyBind, OnPress action) {
            keyBinding = new KeyMapping(
                    keyName,
                    InputConstants.Type.MOUSE,
                    keyBind,
                    "key.category.wyb"
            );
            onPress = action;
        }

        private WYBKey(String keyName, int keyBind, OnPress action, InputConstants.Type type) {
            keyBinding = new KeyMapping(
                    keyName,
                    type,
                    keyBind,
                    "key.category.wyb"
            );
            onPress = action;
        }


        public String getLocalizedName() {
            return keyBinding.getTranslatedKeyMessage().getString();
        }

        @FunctionalInterface
        public interface OnPress {
            void execute(Minecraft client);
        }
    }
}
