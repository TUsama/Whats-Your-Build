package me.clefal.whats_your_build.data.modules.armor;

import com.mojang.serialization.Codec;
import me.clefal.whats_your_build.client.screen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import me.clefal.whats_your_build.client.screen.vanilla.VanillaArmorMenuTab;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IComponentClientHandler;

import java.util.function.Function;

public class VanillaArmorComponentClientHandler implements IComponentClientHandler<VanillaArmorComponent> {
    private static VanillaArmorComponentClientHandler INSTANCE;

    public static VanillaArmorComponentClientHandler getInstance() {
        if (INSTANCE == null){
            INSTANCE = new VanillaArmorComponentClientHandler();
        }
        return INSTANCE;
    }

    @Override
    public byte getIndex() {
        return ComponentType.VANILLA_ARMOR;
    }

    @Override
    public Function<PlayerBuildScreen, BuildMenuTab<?, ?>> getBuildMenuTabFunction(Object component) {
        return playerBuildScreen -> new VanillaArmorMenuTab(((VanillaArmorComponent) component), playerBuildScreen);
    }


    @Override
    public Codec<VanillaArmorComponent> getCodeC() {
        return VanillaArmorComponent.CODEC;
    }
}
