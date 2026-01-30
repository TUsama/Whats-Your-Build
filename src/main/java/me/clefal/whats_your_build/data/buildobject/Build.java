package me.clefal.whats_your_build.data.buildobject;

import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.Map;
import com.google.common.base.Objects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import me.clefal.whats_your_build.data.IPersistedObject;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.data.handler.IItemBuildComponent;
//? mas
//import me.clefal.whats_your_build.data.modules.compat.mas.talent.MASSkillComponent;
import me.clefal.whats_your_build.network.INetworkObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;

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
    @Getter
    private Map<Byte, IBuildComponent<?>> components;
    public String name;

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
        return new Build(List.narrow(components.mapValues(x -> {
            if (x instanceof IItemBuildComponent<?> itemBuildComponent){
                return itemBuildComponent.makeCleanCopy();
            }
            return x;
        }).values().toList()), name);
    }
/*
    public Build createNewBuild(String updateTarget, Container container){
        Map<String, ? extends IBuildComponent<?>> map = components.map((b, c) -> Tuple.of(c.getIdentifier(), c));
        var current = map.get(updateTarget)
                .getOrElseThrow(() -> new RuntimeException("can't find the component with this identifier: " + updateTarget));

        if (current instanceof IItemBuildComponent<?> itemBuildComponent){
            IBuildComponent<?> fromContainer = itemBuildComponent.getFromContainer(container);
            return new Build(Map.<String, IBuildComponent<?>>narrow(map.remove(updateTarget)).put(updateTarget, fromContainer).values().toList(), this.name);
        }
        //? mas {
        else if (current instanceof MASSkillComponent skillComponent) {

        }
        //? }
        /*else {
            throw new RuntimeException("try to update a non-IItemBuildComponent component: " + updateTarget);
        }
        return this;

    }
*/
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
