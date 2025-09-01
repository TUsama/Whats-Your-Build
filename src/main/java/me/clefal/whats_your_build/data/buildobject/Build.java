package me.clefal.whats_your_build.data.buildobject;

import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.HashMap;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.Map;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import me.clefal.whats_your_build.data.IPersistedObject;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.network.INetworkObject;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Comparator;

@Getter
public class Build implements INetworkObject, IPersistedObject<Build> {

    public static final Codec<Build> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("name").forGetter(x->x.name),
                    IBuildComponent.COMPONENT_CODEC.listOf().fieldOf("components").forGetter(x -> x.components.values().asJava())
            ).apply(instance, (x, y) -> new Build(List.ofAll(y), x))
    );

    public final static Build EMPTY = new Build(List.empty(), "empty");

    private Map<Byte, IBuildComponent<?>> components;
    public final String name;

    public Build(List<IBuildComponent<?>> components, String name) {
        this.components = components.toSortedMap(Comparator.naturalOrder(), x -> Tuple.of(x.getHandlerIndex(), x));
        this.name = name;
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

    public Build copy(){
        return new Build(this.components.values().toList(), name);
    }

    public Build cleanCopy(){
        return new Build(List.narrow(components.mapValues(x -> ((IBuildComponent<?>) x.makeCleanCopy())).values().toList()), name);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Build build)) return false;
        return Objects.equal(components, build.components) && Objects.equal(name, build.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(components, name);
    }
}
