package me.clefal.whats_your_build.client.screen.loadoutscreen.components;

import com.clefal.nirvana_lib.utils.NetworkUtils;
import me.clefal.whats_your_build.client.screen.loadoutscreen.LoadoutScreen;
import me.clefal.whats_your_build.network.c2s.C2SLoadLoadoutPacket;
import me.clefal.whats_your_build.world.loadout.load.VanillaItemLoadDescriber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;

import java.util.List;

public class WearButton extends PlainTextButton {
    private BlockPos blockPos;
    private LoadoutScreen screen;

    public WearButton(BlockPos blockPos, LoadoutScreen screen) {
        super(0, 0, 16, 8, Component.translatable("wyb.screen.loadout.button.wear"), button -> {}, Minecraft.getInstance().font);
        this.blockPos = blockPos;
        this.screen = screen;
    }


    @Override
    public void onPress() {
        NetworkUtils.sendToServer(new C2SLoadLoadoutPacket(blockPos, new VanillaItemLoadDescriber(screen.safeGetCurrentSlots().stream().filter(Slot::hasItem).map(x -> x.getItem().copy()).toList())));
    }
}
