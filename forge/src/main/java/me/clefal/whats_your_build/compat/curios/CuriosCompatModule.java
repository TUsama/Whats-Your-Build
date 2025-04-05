package me.clefal.whats_your_build.compat.curios;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.event.server.ServerGatherHandlerEvent;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import me.clefal.whats_your_build.data.handler.IComponentClientHandler;
import me.clefal.whats_your_build.data.modules.ICompatModule;

import java.util.function.Supplier;


public class CuriosCompatModule implements ICompatModule {
    private static CuriosCompatModule INSTANCE;

    public static CuriosCompatModule getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CuriosCompatModule();
        }
        return INSTANCE;
    }

    @Override
    public String getModID() {
        return "curios";
    }

    @Override
    @SubscribeEvent
    public void onRegister(ServerGatherHandlerEvent event) {
        event.modules.add(this);
    }

    @Override
    public void whenEnable() {
        Constants.LOG.info("enable CuriosCompatModule!");
        HandlerManager instance = HandlerManager.getInstance();
        instance.addHandlers(CuriosServerHandler.getInstance(), new HandlerManager.safeInvoker() {
            @Override
            public Supplier<IComponentClientHandler<?>> get() {
                return CuriosClientHandler::getInstance;
            }
        });
    }
}
