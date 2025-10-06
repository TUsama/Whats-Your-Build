//? mas {
/*package me.clefal.whats_your_build.data.modules.compat.mas.talent;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.saveclasses.perks.SchoolData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IComponentServerHandler;
import me.clefal.whats_your_build.event.server.ServerGatherBuildComponentEvent;
import me.clefal.whats_your_build.mixinhelper.ITalentDataGetter;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;

public class MASTalentComponentServerHandler implements IComponentServerHandler {
    @Override
    @SubscribeEvent
    public void onGather(ServerGatherBuildComponentEvent event) {
        ServerPlayer player = event.target;
        HashMap<TalentTree.SchoolType, SchoolData> perks = ((ITalentDataGetter) Load.player(player).talents).getPerks();
        event.addComponent(new MASSkillComponent(perks));
    }

    @Override
    public byte getIndex() {
        return ComponentType.MAS_TALENT;
    }
}
*///?}