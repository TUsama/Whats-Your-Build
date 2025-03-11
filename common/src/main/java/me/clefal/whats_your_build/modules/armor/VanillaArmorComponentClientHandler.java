package me.clefal.whats_your_build.modules.armor;

import com.mojang.serialization.Codec;
import me.clefal.whats_your_build.handler.IComponentClientHandler;

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
        return VanillaArmorComponent.TYPE;
    }

    @Override
    public Codec<VanillaArmorComponent> getCodeC() {
        return VanillaArmorComponent.CODEC;
    }
}
