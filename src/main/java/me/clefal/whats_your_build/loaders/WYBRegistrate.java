package me.clefal.whats_your_build.loaders;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.MenuEntry;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.client.screen.loadoutscreen.LoadoutScreen;
import me.clefal.whats_your_build.world.block.LoadoutChest;
import me.clefal.whats_your_build.world.block.entity.LoadoutChestEntity;
import me.clefal.whats_your_build.world.loadout.LoadoutMenu;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class WYBRegistrate {
    public static final BlockEntityEntry<LoadoutChestEntity> loadoutEntity = Constants.REGISTRATE.object("loadout_chest_entity")
            .blockEntity(LoadoutChestEntity::new)
            .register();

    public static final BlockEntry<LoadoutChest> loadoutChest = Constants.REGISTRATE
            .object("loadout_chest")
            .block(properties -> new LoadoutChest(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.5F)
                            .sound(SoundType.WOOD), loadoutEntity::get))
            .simpleItem()
            .lang("Loadout Chest")
            .register();

    public static final MenuEntry<LoadoutMenu> loadoutMenu = Constants.REGISTRATE
            .<LoadoutMenu, LoadoutScreen>menu(
                    "loadout_menu",
                    (type, windowId, inv) -> new LoadoutMenu(type, windowId, inv),
                    () -> (menu, inv, displayName) -> new LoadoutScreen(menu, inv)

            ).register();


    public static void register() {
    }
}
