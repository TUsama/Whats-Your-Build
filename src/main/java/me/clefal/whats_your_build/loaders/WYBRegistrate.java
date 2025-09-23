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
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
//? neoforge
import net.neoforged.neoforge.common.Tags;

public class WYBRegistrate {


    public static final BlockEntry<LoadoutChest> loadoutChest = Constants.REGISTRATE
            .defaultCreativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
            .block("loadout_chest", LoadoutChest::new)
            .initialProperties(() -> Blocks.CHEST)
            .simpleItem()
            .lang("Loadout Barrel")
            .recipe((blockLoadoutChestDataGenContext, registrateRecipeProvider) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, blockLoadoutChestDataGenContext.get())
                        .pattern("PCP")
                        .pattern("PLP")
                        .pattern("PBP")
                        .define('P', ItemTags.PLANKS)
                        .define('C', Items.IRON_CHESTPLATE)
                        .define('L', Items.IRON_LEGGINGS)
                        .save(registrateRecipeProvider);
            })
            .tag(BlockTags.MINEABLE_WITH_AXE
                    //? neoforge
                    ,Tags.Blocks.STORAGE_BLOCKS
            )
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
        Constants.LOG.info("starting WYB registration!");
    }
}
