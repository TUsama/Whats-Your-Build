//? neoforge {
package me.clefal.whats_your_build.loaders.neoforge;


import me.clefal.whats_your_build.world.loadout.LoadoutMenu;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static me.clefal.whats_your_build.Constants.MOD_ID;

public class NeoforgeDataAttachmentRegister {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);



    public static final Supplier<AttachmentType<ItemStackHandler>> HANDLER = ATTACHMENT_TYPES.register(
            "handler", () -> AttachmentType.serializable(() -> new ItemStackHandler(LoadoutMenu.size)).copyOnDeath().build()
    );

    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
    }
}
//? }