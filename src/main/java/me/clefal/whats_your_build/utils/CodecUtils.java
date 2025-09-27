package me.clefal.whats_your_build.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CodecUtils {
    //? 1.20.1 {
    /*public <A> Codec<A> transform(MapCodec<A> codec){
        return codec.codec();
    }
    *///?} else {

    public <A> MapCodec<A> transform(MapCodec<A> codec){
        return codec;
    }
    //?}
}
