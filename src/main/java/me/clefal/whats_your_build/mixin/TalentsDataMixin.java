package me.clefal.whats_your_build.mixin;

import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.saveclasses.perks.SchoolData;
import com.robertx22.mine_and_slash.saveclasses.perks.TalentsData;
import me.clefal.whats_your_build.mixinhelper.ITalentDataGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;

@Mixin(value = TalentsData.class)
public abstract class TalentsDataMixin implements ITalentDataGetter {


    @Shadow private HashMap<TalentTree.SchoolType, SchoolData> perks;

    @Override
    public HashMap<TalentTree.SchoolType, SchoolData> getPerks() {
        return perks;
    }
}
