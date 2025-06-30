package me.clefal.whats_your_build.client.screen;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenu;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.buildscreen.RenderContext;

public interface IBuildMenuContainer {

    List<BuildMenuTab<?, ?>> getTabs();

    void setNewMenu(BuildMenu<?> menu);

}
