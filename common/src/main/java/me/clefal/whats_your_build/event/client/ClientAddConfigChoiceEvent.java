package me.clefal.whats_your_build.event.client;

import java.util.ArrayList;
import java.util.List;

public class ClientAddConfigChoiceEvent extends ClientEvent {
    public final List<String> configs = new ArrayList<>();
}
