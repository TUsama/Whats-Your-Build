package me.clefal.whats_your_build.mixin;

import com.robertx22.mine_and_slash.saveclasses.PointData;
import com.robertx22.mine_and_slash.saveclasses.perks.SchoolData;
import me.clefal.whats_your_build.mixinhelper.ISchoolDataGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(value = SchoolData.class)
public abstract class SchoolDataMixin implements ISchoolDataGetter {
    @Shadow private Set<PointData> list;

    @Override
    public Set<PointData> getPoints() {
        return list;
    }
}
