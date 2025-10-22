package me.clefal.whats_your_build.loaders;

import me.clefal.whats_your_build.Constants;
import net.minecraft.network.chat.MutableComponent;

public class WYBLang {
    public static final MutableComponent armor = create("wyb.screen.tab.armor", "Armor");
    public static final MutableComponent armory = create("wyb.container.title.armory", "Armory");
    public static final MutableComponent curios = create("wyb.compat.screen.tab.curios", "Curios");
    public static final MutableComponent mas_talent = create("wyb.compat.screen.tab.mas_talent", "Talent");
    public static final MutableComponent view_talent = create("wyb.compat.mas.view_talent", "Talent...");
    public static final MutableComponent view_asc = create("wyb.compat.mas.view_asc", "Ascendancy...");
    public static final MutableComponent used_point = create("wyb.compat.mas.used_point", "Used Point(s): %1$s");


    public static final MutableComponent player_build = create("wyb.screen.player_build.player_build", "Player Build");


    public static final MutableComponent showYourBuildFor_all = create("whats_your_build.wyb_client_config.showYourBuildFor.all", "All");
    public static final MutableComponent showYourBuildFor_no_one = create("whats_your_build.wyb_client_config.showYourBuildFor.no_one", "No one");
    public static final MutableComponent showYourBuildFor_teammates = create("whats_your_build.wyb_client_config.showYourBuildFor.teammates", "Teammates");


    public static final MutableComponent key_category = create("key.category.wyb", "What's Your Build?");
    public static final MutableComponent key_check = create("key.wyb.check", "Check Build");
    public static final MutableComponent key_modifier_key = create("key.wyb.modifier_key", "Modifier Key");
    public static final MutableComponent key_send_build_to_chat = create("key.wyb.send_build_to_chat", "Send build to chat");


    public static final MutableComponent ask_reject = create("wyb.ask.reject", "You aren't allowed to see this player's build.");
    public static final MutableComponent chat_receive_build = create("wyb.chat.receive_build", "%1$s shares their build to you: %2$s");
    public static final MutableComponent chat_build = create("wyb.chat.build", "[%1$s's build]");
    public static final MutableComponent chat_click_to_show_build = create("wyb.chat.click_to_show_build", "Click here to show the build.");


    public static final MutableComponent loadout_no_loadout = create("wyb.screen.loadout.no_loadout", "No Loadout");
    public static final MutableComponent loadout_initial_build_name = create("wyb.screen.loadout.initial_build_name", "Loadout %1$s");
    public static final MutableComponent loadout_tab_new = create("wyb.screen.loadout.tab.new", "New Loadout");
    public static final MutableComponent loadout_right_click_menu_save = create("wyb.screen.loadout.right_click_menu.save", "Save");
    public static final MutableComponent loadout_right_click_menu_reset = create("wyb.screen.loadout.right_click_menu.reset", "Reset");
    public static final MutableComponent loadout_right_click_menu_clear = create("wyb.screen.loadout.right_click_menu.clear", "Clear");
    public static final MutableComponent loadout_right_click_menu_delete = create("wyb.screen.loadout.right_click_menu.delete", "Delete");
    public static final MutableComponent loadout_save_tip = create("wyb.screen.loadout.save_tip", "Right Click To Save");
    public static final MutableComponent loadout_button_wear = create("wyb.screen.loadout.button.wear", "Wear");

    private static MutableComponent create(String key, String value){
        return Constants.REGISTRATE.addRawLang(key, value);
    }

    protected static void register() {

    }

}
