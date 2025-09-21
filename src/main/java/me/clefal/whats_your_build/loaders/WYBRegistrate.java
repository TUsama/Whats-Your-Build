package me.clefal.whats_your_build.loaders;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.MenuEntry;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.client.renderer.LoadoutChestRenderer;
import me.clefal.whats_your_build.client.screen.buildscreen.PlayerBuildScreen;
import me.clefal.whats_your_build.client.screen.loadoutscreen.LoadoutScreen;
import me.clefal.whats_your_build.world.block.LoadoutChest;
import me.clefal.whats_your_build.world.block.entity.LoadoutChestEntity;
import me.clefal.whats_your_build.world.loadout.LoadoutMenu;
import me.clefal.whats_your_build.world.player_build.PlayerBuildMenu;
import net.minecraft.world.level.block.Blocks;

public class WYBRegistrate {


    public static final BlockEntry<LoadoutChest> loadoutChest = Constants.REGISTRATE
            .block("loadout_chest", LoadoutChest::new)
            .initialProperties(() -> Blocks.CHEST)
            .simpleItem()
            .lang("Loadout Chest")
            .register();


    public static final BlockEntityEntry<LoadoutChestEntity> loadoutEntity = Constants.REGISTRATE.object("loadout_chest_entity")
            .blockEntity(LoadoutChestEntity::new)
            .renderer(() -> LoadoutChestRenderer::new)
            .validBlock(loadoutChest::get)
            .register();

    public static final MenuEntry<LoadoutMenu> loadoutMenu = Constants.REGISTRATE
            .<LoadoutMenu, LoadoutScreen>menu(
                    "loadout_menu",
                    (type, windowId, inv, buf) -> new LoadoutMenu(type, windowId, inv, buf),
                    () -> (menu, inv, displayName) -> new LoadoutScreen(menu, inv)

            ).register();

    public static final MenuEntry<PlayerBuildMenu> playerBuildMenu = Constants.REGISTRATE
            .menu(
                    "player_build_menu",
                    (type, windowId, inv, buf) -> new PlayerBuildMenu(type, windowId, buf),
                    () -> PlayerBuildScreen::new

            ).register();


    public static void register() {
    }
}
