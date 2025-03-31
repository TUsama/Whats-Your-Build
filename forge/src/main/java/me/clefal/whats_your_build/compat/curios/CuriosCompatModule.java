package me.clefal.whats_your_build.compat.curios;

import com.clefal.nirvana_lib.utils.SideUtils;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.handler.HandlerManager;
import me.clefal.whats_your_build.modules.ICompatModule;
import me.clefal.whats_your_build.modules.IModule;


public class CuriosCompatModule implements ICompatModule {
    private static CuriosCompatModule INSTANCE;

    public static CuriosCompatModule getInstance() {
        if (INSTANCE == null){
            INSTANCE = new CuriosCompatModule();
        }
        return INSTANCE;
    }

    @Override
    public String getModID() {
        return "curios";
    }

    @Override
    public void whenEnable() {
        Constants.LOG.info("enable CuriosCompatModule!");
        HandlerManager instance = HandlerManager.getInstance();
        if (SideUtils.isClient()) {
            instance.clientHandlers.add(CuriosClientHandler.getInstance().getIndex(), CuriosClientHandler.getInstance());
        }
        instance.serverHandlers.add(CuriosServerHandler.getInstance().getIndex(), CuriosServerHandler.getInstance());
    }
}
