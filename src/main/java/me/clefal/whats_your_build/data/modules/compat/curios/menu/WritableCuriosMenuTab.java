package me.clefal.whats_your_build.data.modules.compat.curios.menu;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.data.modules.compat.curios.CuriosComponent;
import net.minecraft.world.inventory.Slot;

public class WritableCuriosMenuTab extends CuriosMenuTab{
    @Override
    public List<Slot> getNewSlots() {
        return () -> new WritableCuriosMenu(component, holder.generateRenderContext());
    }

    public WritableCuriosMenuTab(CuriosComponent component, IBuildMenuContainerHolder<?> holder) {
        super(component, holder);
    }
}
