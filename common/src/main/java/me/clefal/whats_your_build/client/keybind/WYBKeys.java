package me.clefal.whats_your_build.client.keybind;

import com.clefal.nirvana_lib.relocated.io.vavr.Lazy;
import com.clefal.nirvana_lib.utils.NetworkUtil;
import com.mojang.blaze3d.platform.InputConstants;
import me.clefal.whats_your_build.network.c2s.C2SAskBuildPacket;
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

                if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY && client.hitResult instanceof EntityHitResult entityHitResult /*&& entityHitResult.getEntity() instanceof Player player*/) {
                    NetworkUtil.sendToServer(new C2SAskBuildPacket(entityHitResult.getEntity().getUUID()));
                }
            }))

    );

    public static void registerAllKey(Consumer<List<KeyMapping>> handle) {
        handle.accept(KEYS.stream().map(x -> x.get().keyBinding).toList());
    }

    public static void consumerKeys() {
        for (var key : KEYS) {
            if (key.get().keyBinding.consumeClick()) {
                Minecraft client = Minecraft.getInstance();
                key.get().onPress.execute(client);
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
