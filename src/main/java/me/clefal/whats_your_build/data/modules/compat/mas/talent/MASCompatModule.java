//? mas {
/*package me.clefal.whats_your_build.data.modules.compat.mas.talent;

import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import me.clefal.whats_your_build.data.handler.IComponentClientHandler;
import me.clefal.whats_your_build.data.modules.ICompatModule;
import me.clefal.whats_your_build.event.server.ServerGatherHandlerEvent;

import java.util.function.Supplier;

public class MASCompatModule implements ICompatModule {
    @Override
    public String getModID() {
        return "mmorpg";
    }

    @Override
    public void onRegister(ServerGatherHandlerEvent event) {
        event.modules.add(this);
    }

    @Override
    public void whenEnable() {
        Constants.LOG.info("enable MASCompatModule!");
        HandlerManager instance = HandlerManager.getInstance();
        instance.addHandlers(new MASTalentComponentServerHandler(), new HandlerManager.safeInvoker() {
            @Override
            public Supplier<IComponentClientHandler> get() {
                return MASTalentComponentClientHandler::new;
            }
        });
    }
}
*///?}