//? mas {
/*package me.clefal.whats_your_build.data.modules.compat.mas.talent;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import me.clefal.whats_your_build.data.handler.IComponentClientHandler;
import me.clefal.whats_your_build.data.modules.ICompatModule;
import me.clefal.whats_your_build.event.server.ServerGatherHandlerEvent;

import java.util.function.Supplier;

public class MASCompatModule implements ICompatModule {

    private static MASCompatModule INSTANCE;

    public static MASCompatModule getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MASCompatModule();
        }
        return INSTANCE;
    }
    @Override
    public String getModID() {
        return "mmorpg";
    }

    @Override
    @SubscribeEvent
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