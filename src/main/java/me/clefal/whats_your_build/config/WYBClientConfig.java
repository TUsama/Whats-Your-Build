package me.clefal.whats_your_build.config;

import com.clefal.nirvana_lib.config.StringListValue;
import com.clefal.nirvana_lib.network.packets.C2SSendSyncingConfigPacket;
import com.clefal.nirvana_lib.utils.NetworkUtils;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.event.client.ClientAddConfigChoiceEvent;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedChoiceList;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import net.minecraft.client.Minecraft;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class WYBClientConfig extends Config {
    public static WYBClientConfig config = ConfigApiJava.registerAndLoadConfig(WYBClientConfig::new, RegisterType.CLIENT);
    public float globalScale = 1.3f;
    public ValidatedChoiceList<String> showYourBuildFor = make(() -> {
        ClientAddConfigChoiceEvent clientAddConfigChoiceEvent = CommonClass.post(new ClientAddConfigChoiceEvent());
        String[] alls = ArrayUtils.addFirst(clientAddConfigChoiceEvent.configs.toArray(String[]::new), "all");
        String[] noOnes = ArrayUtils.add(alls, "no_one");
        return ValidatedList.ofString(noOnes);
    })
            .toChoiceList(List.of("all"), ValidatedChoiceList.WidgetType.SCROLLABLE, (x, y) -> Component.translatable(y + "." + x.toLowerCase()));

    public WYBClientConfig() {
        super(CommonClass.id("wyb_client_config"));
    }

    public static <T> T make(Supplier<T> supplier) {
        return supplier.get();
    }

    public static void init() {

    }

    @Override
    public void onUpdateClient() {
        syncConfig();
    }

    @Override
    public void onSyncClient() {
        syncConfig();
    }

    private void syncConfig() {
        if (Minecraft.getInstance().getConnection() != null) {
            NetworkUtils.sendToServer(new C2SSendSyncingConfigPacket(UUIDUtil.getOrCreatePlayerUUID(Minecraft.getInstance().getUser().getGameProfile()), Map.of(
                    "showYourBuildFor", new StringListValue(showYourBuildFor)
            )));
        }
    }
}
