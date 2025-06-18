package me.clefal.whats_your_build.mixin;

import me.clefal.whats_your_build.utils.IBufferSourceProvider;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

//? =1.20.1 || fabric {
/*@Mixin(GuiGraphics.class)
*///?} else {
@Mixin(value = GuiGraphics.class, remap = false)
//?}
public class GuiGraphicsMixin implements IBufferSourceProvider {

    @Shadow @Final private MultiBufferSource.BufferSource bufferSource;

    @Override
    public MultiBufferSource whats_Your_Build$getBufferSource() {
        return bufferSource;
    }
}
