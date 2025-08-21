package me.clefal.whats_your_build.client.screen.loadoutscreen;

import me.clefal.whats_your_build.client.screen.buildscreen.vanilla.ArmorHolder;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class InMenuMutableArmorHolder extends ArmorHolder {

    public ItemStack itemStack;
    @Nullable
    private AbstractContainerMenu menu;

    public InMenuMutableArmorHolder(int radius, @Nullable ItemStack itemStack) {
        super(radius, itemStack);
    }

    public void initMenu(AbstractContainerMenu menu){
        this.menu = menu;
    }

    

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (menu != null) itemStack = menu.getCarried();
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public static InMenuMutableArmorHolder fromImmutable(ArmorHolder holder){
        return new InMenuMutableArmorHolder(holder.getWidth(), holder.getItemStack());
    }
}
