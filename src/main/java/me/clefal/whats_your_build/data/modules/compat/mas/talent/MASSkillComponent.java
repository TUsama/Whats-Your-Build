//? mas {
/*package me.clefal.whats_your_build.data.modules.compat.mas.talent;

import com.clefal.nirvana_lib.relocated.io.vavr.API;
import com.clefal.nirvana_lib.relocated.io.vavr.Tuple2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.saveclasses.PointData;
import com.robertx22.mine_and_slash.saveclasses.perks.SchoolData;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.mixinhelper.ISchoolDataGetter;

import java.util.*;


public class MASSkillComponent implements IBuildComponent<MASSkillComponent> {
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
    public static final MapCodec<MASSkillComponent> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.unboundedMap(Codec.STRING.xmap(TalentTree.SchoolType::valueOf, TalentTree.SchoolType::toString), SCHOOL_DATA_CODEC).fieldOf("skills").forGetter(x -> x.perks)
            ).apply(instance, MASSkillComponent::new)
    );
    private Map<TalentTree.SchoolType, SchoolData> perks;


    public MASSkillComponent(Map<TalentTree.SchoolType, SchoolData> perks) {
        this.perks = perks;
    }

    public Map<TalentTree.SchoolType, SchoolData> getPerks() {
        HashMap<TalentTree.SchoolType, SchoolData> map = new HashMap<>();
        perks.entrySet().stream()
                .map(x -> {
                    Set<PointData> points = ((ISchoolDataGetter) x.getValue()).getPoints();
                    SchoolData schoolData = new SchoolData();
                    for (PointData point : points) {
                        schoolData.allocate(new PointData(point.x, point.y));
                    }
                    return API.Tuple(x.getKey(), schoolData);
                })
                .forEach(x -> map.put(x._1(), x._2()));
        return map;
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
    public MapCodec<MASSkillComponent> getCodec() {
        return CODEC;
    }



}
*///?}