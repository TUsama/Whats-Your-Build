package me.clefal.whats_your_build.client.screen;

import com.clefal.nirvana_lib.client.render.rendertype.RenderTypeCreator;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.function.Function;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;

public class BuildRenderType extends RenderTypeCreator {

    public static final Function<ResourceLocation, RenderType> gui = Util.memoize(resourceLocation -> RenderTypeCreator.createRenderType("nl_normal_gui", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 1024, false, false,
            CompositeState.builder()
                    .setShaderState(new ShaderStateShard(GameRenderer::getPositionColorTexLightmapShader))
                    .setTextureState(new TextureStateShard(resourceLocation, false, false))
                    .setTransparencyState(new TransparencyStateShard("normal_blend", RenderSystem::enableBlend, RenderSystem::disableBlend))
                    .setDepthTestState(new DepthTestStateShard("nl_normal_gui_depth", GL11.GL_LEQUAL))
                    .createCompositeState(false)));

    public static final RenderType guiOverlay = RenderTypeCreator.createRenderType("gui_overlay", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_GUI_OVERLAY_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setDepthTestState(LEQUAL_DEPTH_TEST).setWriteMaskState(COLOR_WRITE).createCompositeState(false));


    public BuildRenderType(String $$0, VertexFormat $$1, VertexFormat.Mode $$2, int $$3, boolean $$4, boolean $$5, Runnable $$6, Runnable $$7) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
    }
}
