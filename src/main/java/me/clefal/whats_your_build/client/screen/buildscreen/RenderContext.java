package me.clefal.whats_your_build.client.screen.buildscreen;

import net.minecraft.world.entity.player.Player;

public record RenderContext(int tabOriginalX, int tabOriginalY, float scale, Player player) {
}
