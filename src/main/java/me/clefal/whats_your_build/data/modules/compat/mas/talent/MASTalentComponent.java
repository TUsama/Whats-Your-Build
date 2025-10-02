//? mas {
/*package me.clefal.whats_your_build.data.modules.compat.mas.talent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.saveclasses.PointData;
import com.robertx22.mine_and_slash.saveclasses.perks.SchoolData;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.mixinhelper.ISchoolDataGetter;

import java.util.ArrayList;
import java.util.Map;


public class MASTalentComponent implements IBuildComponent<MASTalentComponent> {
    public static final String ID = "mine_and_slash";
    public static final Codec<PointData> POINT_DATA_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("x").forGetter(x -> x.x),
                    Codec.INT.fieldOf("y").forGetter(x -> x.y)
            ).apply(instance, PointData::new)
    );

    public static final Codec<SchoolData> SCHOOL_DATA_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    POINT_DATA_CODEC.listOf().fieldOf("points").forGetter(x -> new ArrayList<>(((ISchoolDataGetter) x).getPoints()))
            ).apply(instance, x ->{
                SchoolData schoolData = new SchoolData();
                x.forEach(schoolData::allocate);
                return schoolData;
            })
    );
    public static final MapCodec<MASTalentComponent> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.unboundedMap(Codec.STRING.xmap(TalentTree.SchoolType::valueOf, TalentTree.SchoolType::toString), SCHOOL_DATA_CODEC).fieldOf("map").forGetter(x -> x.perks)
            ).apply(instance, MASTalentComponent::new)
    );
    public Map<TalentTree.SchoolType, SchoolData> perks;


    public MASTalentComponent(Map<TalentTree.SchoolType, SchoolData> perks) {
        this.perks = perks;
    }



    @Override
    public byte getHandlerIndex() {
        return ComponentType.MAS_TALENT;
    }

    @Override
    public String getIdentifier() {
        return ID;
    }

    @Override
    public MapCodec<MASTalentComponent> getCodec() {
        return CODEC;
    }



}
*///?}