package me.clefal.whats_your_build.data.buildobject;

import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.Map;
import com.mojang.serialization.Codec;
import lombok.Getter;
import me.clefal.whats_your_build.data.IPersistedObject;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.network.INetworkObject;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Comparator;

@Getter
public class Build implements INetworkObject, IPersistedObject<Build> {

    public static final Codec<Build> CODEC = Codec.unboundedMap(
            Codec.STRING, IBuildComponent.COMPONENT_CODEC
    ).xmap(x -> new Build(List.ofAll(x.values())), build -> {
        Map<Byte, IBuildComponent<?>> components1 = Map.narrow(build.components);
        return components1
                .mapKeys(String::valueOf)
                .toJavaMap();
    });

    private Map<Byte, ? extends IBuildComponent<?>> components;

    public Build(List<IBuildComponent<?>> components) {
        this.components = components.toSortedMap(Comparator.naturalOrder(), x -> Tuple.of(x.getHandlerIndex(), x));
    }

    public boolean isEmpty(){
        return components.isEmpty();
    }

    @Override
    public Codec<Build> getCodec() {
        return CODEC;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeJsonWithCodec(CODEC, this);
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        this.components = buf.readJsonWithCodec(CODEC).components;
    }
}
