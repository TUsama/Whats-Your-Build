package me.clefal.whats_your_build.chat;

import net.minecraft.network.chat.ClickEvent;

import java.util.UUID;

public class BuildClickEvent extends ClickEvent {
    public BuildClickEvent(UUID uuid) {
        super(Action.SUGGEST_COMMAND, uuid.toString());
    }


}
