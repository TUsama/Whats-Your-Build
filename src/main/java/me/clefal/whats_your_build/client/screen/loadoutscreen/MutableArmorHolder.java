package me.clefal.whats_your_build.client.screen.loadoutscreen;

import me.clefal.whats_your_build.client.screen.buildscreen.vanilla.ArmorHolder;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MutableArmorHolder extends ArmorHolder {

    public ItemStack itemStack;
    private final AbstractContainerMenu menu;

    public MutableArmorHolder(int radius, @Nullable ItemStack itemStack, AbstractContainerMenu menu) {
        super(radius, itemStack);
        this.menu = menu;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        itemStack = menu.getCarried();
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public static MutableArmorHolder fromImmutable(ArmorHolder holder, AbstractContainerMenu menu){
        return new MutableArmorHolder(holder.getWidth(), holder.getItemStack(), menu);
    }
}
