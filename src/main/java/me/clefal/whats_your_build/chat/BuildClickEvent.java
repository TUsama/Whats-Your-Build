package me.clefal.whats_your_build.chat;

import me.clefal.whats_your_build.Constants;
import net.minecraft.network.chat.ClickEvent;

import java.util.UUID;

public class BuildClickEvent extends ClickEvent {
    public BuildClickEvent(UUID uuid) {
        super(Action.SUGGEST_COMMAND, Constants.MOD_ID + uuid);
    }


}
