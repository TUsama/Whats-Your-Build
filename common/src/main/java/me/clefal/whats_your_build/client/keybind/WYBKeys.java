package me.clefal.whats_your_build.client.keybind;

import com.clefal.nirvana_lib.utils.NetworkUtil;
import com.mojang.blaze3d.platform.InputConstants;
import me.clefal.whats_your_build.network.c2s.C2SAskBuildPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.function.Consumer;

public class WYBKeys {

    public static final WYBKey CHECK = new WYBKey("key.wyb.check", GLFW.GLFW_MOUSE_BUTTON_MIDDLE, client -> {
        HitResult hitResult = client.hitResult;
        if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY && client.hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof Player player) {
            NetworkUtil.sendToServer(new C2SAskBuildPacket(player.getUUID()));
        }
    });
    static final WYBKey[] KEYS = {
            CHECK,

    };

    public static void registerAllKey(Consumer<KeyMapping[]> handle) {
        handle.accept(Arrays.stream(KEYS).map(x -> x.keyBinding).toArray(KeyMapping[]::new));
    }

    public static void consumerKeys() {
        for (var key : KEYS) {
            if (key.keyBinding.consumeClick()) {
                key.onPress.execute(Minecraft.getInstance());
            }
        }
    }

    public static class WYBKey {

        final KeyMapping keyBinding;
        final OnPress onPress;

        private WYBKey(String keyName, int keyBind, OnPress action) {
            keyBinding = new KeyMapping(
                    keyName,
                    InputConstants.Type.KEYSYM,
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
