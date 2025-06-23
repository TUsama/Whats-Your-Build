//? forge || neoforge {
package me.clefal.whats_your_build.loaders;

import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.world.loadout.BuildManagementMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WYBMenuType {
    public static final DeferredRegister<MenuType<?>> MENU_TYPE_DEFERRED_REGISTER = DeferredRegister.create(
            BuiltInRegistries.MENU,
            Constants.MOD_ID
    );

    public static final DeferredHolder<MenuType<?>, MenuType<BuildManagementMenu>> buildManagementMenuType = MENU_TYPE_DEFERRED_REGISTER.register("build_management_menu", () -> new MenuType<>((BuildManagementMenu::new), FeatureFlags.DEFAULT_FLAGS));

}
//?}
