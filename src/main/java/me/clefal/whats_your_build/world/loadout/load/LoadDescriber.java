package me.clefal.whats_your_build.world.loadout.load;


import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;

public abstract class LoadDescriber<T> {
    public static final Codec<LoadDescriber<?>> CODEC = Codec.STRING.dispatch(LoadDescriber::getType, x ->
            switch (x) {
                case VanillaItemLoadDescriber.TYPE -> VanillaItemLoadDescriber.CODEC;
                default -> throw new IllegalStateException("Unexpected value: " + x);
            }
    );
    public T target;

    public LoadDescriber(T target) {
        this.target = target;
    }

    public abstract String getType();

    public abstract void tryWear(ServerPlayer player, Container armory);
}
