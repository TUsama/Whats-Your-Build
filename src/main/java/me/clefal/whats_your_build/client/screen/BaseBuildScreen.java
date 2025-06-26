package me.clefal.whats_your_build.client.screen;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenu;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.buildscreen.RenderContext;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.function.Function;

public abstract class BaseBuildScreen extends Screen {
    public final Player targetPlayer;
    protected final List<BuildMenuTab<?, ?>> tabs;
    @Nullable
    private BuildMenu<?> currentMenu;

    protected BaseBuildScreen(Player targetPlayer, List<Function<BaseBuildScreen, BuildMenuTab<?, ?>>> tabs) {
        super(Component.literal(""));
        this.targetPlayer = targetPlayer;
        this.tabs = tabs.map(x -> x.apply(this));
    }

    public void setNewMenu(BuildMenu<?> menu) {
        this.currentMenu = menu;
    }

    public abstract RenderContext generateRenderContext();
}
