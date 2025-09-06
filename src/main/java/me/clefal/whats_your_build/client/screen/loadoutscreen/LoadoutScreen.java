//? neoforge {
package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.clefal.nirvana_lib.client.render.batch.DrawStringBufferInfo;
import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import com.clefal.nirvana_lib.client.render.rendertype.RenderTypeCreator;
import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.Map;
import lombok.Getter;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.client.components.RightClickMenu;
import me.clefal.whats_your_build.client.components.WYBImageButton;
import me.clefal.whats_your_build.client.screen.WYBScreen;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.client.storage.LoadoutsClientHandler;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.data.modules.armor.VanillaArmorComponent;
import me.clefal.whats_your_build.data.modules.compat.curios.CuriosComponent;
import me.clefal.whats_your_build.world.BuildMenu;
import me.clefal.whats_your_build.world.IBuildHandler;
import me.clefal.whats_your_build.world.IRewritable;
import me.clefal.whats_your_build.world.loadout.LoadoutMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class LoadoutScreen extends WYBScreen<LoadoutMenu> implements IBuildHandler, IRewritable {
    public static final ResourceLocation INVENTORY_LOCATION = CommonClass.id("textures/gui/container/background.png");
    public static final ResourceLocation ARMORY = CommonClass.id("textures/gui/container/armory.png");
    public final LoadoutSelectionList buildList;
    public VertexContainer vertexContainer = new VertexContainer();
    protected LinkedHashMap<String, BuildMenu.SlotPlacer> placePlan = new LinkedHashMap<>();
    //a list only exists on the client!
    @Getter
    private LinkedHashMap<String, NonNullList<Slot>> slots = new LinkedHashMap<>();
    private List<BuildMenuTab<?>> tabs;
    private WYBImageButton addNewEntry;
    private RightClickMenu rightClickMenu;

    public LoadoutScreen(LoadoutMenu menu, Inventory playerInventory) {
        super(menu, playerInventory, Component.literal(""));
        this.buildList = new LoadoutSelectionList(80, 60, 20, 100, this);
        this.tabs = HandlerManager.getInstance().getImmutableBuildMenuTabFunction(menu.getSelfBuild()).map(x -> x.apply(this, this));
        this.addNewEntry = new WYBImageButton(0, 0, 8, 8, button -> {
            buildList.addSelfBuildEntry(menu.getSelfBuild().copy());
            }, "add_new_entry");
        this.addNewEntry.setTooltip(Tooltip.create(Component.translatable("wyb.screen.loadout.tab.new")));
        this.rightClickMenu = new RightClickMenu(0, 0, 0, 0, Component.literal(""));
    }


    @Override
    protected void init() {
        super.init();
        try {
            buildList.addBuildsOnInit(LoadoutsClientHandler.readAllFromLocal(minecraft.player.getUUID()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        buildList.setX(leftPos);
        buildList.setY(topPos);
        addRenderableWidget(buildList);

        addNewEntry.setPosition(leftPos + buildList.getWidth() - 8, topPos - 8);
        addRenderableWidget(addNewEntry);

        tabs.forEachWithIndex((buildMenuTab, value) -> {
            buildMenuTab.setPosition(leftPos + 80 + 8 * value, topPos - 8);
            this.addRenderableWidget(buildMenuTab);
        });

    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (buildList.noBuild()) {
            this.tabs.forEach(x -> x.active = false);
            vertexContainer.putString(DrawStringBufferInfo.of(Component.translatable("wyb.screen.loadout.no_loadout").getString(), leftPos + 100, topPos + buildList.getHeight() / 2, ChatFormatting.GRAY.getColor(), guiGraphics.pose().last().pose()));
        } else {
            this.tabs.forEach(x -> x.active = true);
            NonNullList<Slot> slots1 = safeGetCurrentSlots();
            for (int k = 0; k < slots1.size(); k++) {
                Slot slot = slots1.get(k);
                if (slot.isActive()) {
                    this.renderSlot(guiGraphics, slot);
                }
                boolean hovering = this.isHovering1(slot.x, slot.y, 16, 16, mouseX, mouseY);
                if (hovering) {
                    this.hoveredSlot = slot;
                    this.renderSlotHighlight(guiGraphics, slot, mouseX, mouseY, partialTick);
                }
            }
            renderTooltip(guiGraphics, mouseX, mouseY);
        }
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        vertexContainer.draw(guiGraphics.bufferSource(), RenderTypeCreator.guiBlend);
    }

    protected boolean isHovering1(int x, int y, int width, int height, double mouseX, double mouseY) {
        return mouseX >= (double)(x - 1)
                && mouseX < (double)(x + width + 1)
                && mouseY >= (double)(y - 1)
                && mouseY < (double)(y + height + 1);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(INVENTORY_LOCATION, leftPos, topPos + buildList.getHeight() + 12, 0, 0, 256, 256);
        guiGraphics.blit(ARMORY, leftPos + imageWidth, topPos, 0, 0, 256, 256);
    }


    @Override
    public VertexContainer getVertexContainer() {
        return vertexContainer;
    }

    public void addButtonForMenu(WYBImageButton button){
        this.rightClickMenu.buttons.add(button);
    }

    public void removeAllButtonsInMenu(){
        this.rightClickMenu.buttons.clear();
    }

    private void tryRemoveCurrentMenu(){
        if (getFocused() != null && getFocused().equals(rightClickMenu)) {
            setFocused(null);
            removeWidget(rightClickMenu);
            removeAllButtonsInMenu();
        }
    }

    public void tryAddMenu(double mouseX, double mouseY){
        LoadoutSelectionList.BuildEntry currentEntry = buildList.getCurrentEntry(mouseX, mouseY);
        if (currentEntry != null){
            tryRemoveCurrentMenu();
            int width = 0;
            int height = 0;
            for (WYBImageButton wybImageButton : currentEntry.currentState.provideButtons()) {
                addButtonForMenu(wybImageButton);
                width = wybImageButton.getWidth();
                height += wybImageButton.getHeight();
            }
            rightClickMenu.setPosition(((int) mouseX), ((int) mouseY));
            rightClickMenu.setSize(width, height);
            addRenderableOnly(rightClickMenu);
            addRenderableWidget(rightClickMenu);
            setFocused(rightClickMenu);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        //always handle the right click menu first.
        if (rightClickMenu.isMouseOver(mouseX, mouseY) && rightClickMenu.mouseClicked(mouseX, mouseY, button)){
            System.out.println("trigger early!");
            return true;
        }
        boolean b = super.mouseClicked(mouseX, mouseY, button);
        tryRemoveCurrentMenu();
        //try to add right click menu
        if (button == 1){
            tryAddMenu(mouseX, mouseY);
        }
        //handle slot
        for (Slot slot : safeGetCurrentSlots()) {
            if (isHovering1(slot.x, slot.y, 16, 16, mouseX, mouseY)) {
                ItemStack carried = menu.getCarried();
                slot.set(carried.copy());
            }
        }

        return b;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        tryRemoveCurrentMenu();
        return super.charTyped(codePoint, modifiers);
    }

    private void addSlot(String id, Container container, int index, int x, int y) {
        NonNullList<Slot> slots1 = this.slots.get(id);
        Slot slot = new Slot(container, index, x, y) {
            @Override
            public boolean mayPickup(Player player) {
                return false;
            }

            @Override
            public ItemStack safeInsert(ItemStack stack) {
                this.setByPlayer(stack);
                return stack;
            }

            @Override
            public boolean isFake() {
                return true;
            }

            @Override
            public ItemStack safeTake(int count, int decrement, Player player) {
                return ItemStack.EMPTY;
            }
        };

        if (slots1 == null){
            NonNullList<Slot> objects = NonNullList.create();
            objects.add(slot);
            this.slots.put(id, objects);
        } else {
            slots1.add(slot);
        }
    }

    @Override
    public void handleBuild(Build build) {
        Map<String, ? extends IBuildComponent<?>> map = build.getComponents()
                .map((aByte, iBuildComponent) -> Tuple.of(iBuildComponent.getIdentifier(), iBuildComponent));

        map
                .get(VanillaArmorComponent.ID)
                .forEach(iBuildComponent -> placePlan.put(VanillaArmorComponent.ID,
                        () -> {
                            var simpleContainer = iBuildComponent.asContainer();
                            int k = 0;
                            int j = 0;
                            for (int i = 0; i < simpleContainer.getContainerSize(); i++) {
                                if (k >= 4) {
                                    j++;
                                    k = 0;
                                }
                                addSlot(VanillaArmorComponent.ID, simpleContainer, i, leftPos + j * 18 + 90, topPos + k * 18);
                                k++;
                            }
                        }));

        map
                .get(CuriosComponent.ID)
                .forEach(iBuildComponent -> placePlan.put(CuriosComponent.ID,
                        () -> {
                            var simpleContainer = iBuildComponent.asContainer();

                            int k = 0;
                            int j = 0;
                            for (int i = 0; i < simpleContainer.getContainerSize(); i++) {
                                if (k >= 4) {
                                    j++;
                                    k = 0;
                                }
                                addSlot(CuriosComponent.ID, simpleContainer, i, j * 18 + 24, k * 18 + 52);
                                k++;
                            }
                        }));
    }
    protected String currentAt = "";

    public NonNullList<Slot> safeGetCurrentSlots(){
        Logger log = Constants.LOG;
        if (currentAt.isEmpty()){
            if (slots.isEmpty()){
                rewriteSlots();
                log.debug("both empty!");
            }
            return slots.firstEntry().getValue();
        } else {
            return slots.get(currentAt);
        }
    }
    @Override
    public void rewriteSlots(String identifier) {
        this.slots.clear();
        placePlan.get(identifier).place();
        currentAt = identifier;
    }

    public void rewriteSlots() {
        this.slots.clear();
        java.util.Map.Entry<String, BuildMenu.SlotPlacer> stringSlotPlacerEntry = placePlan.firstEntry();
        stringSlotPlacerEntry.getValue().place();
        currentAt = stringSlotPlacerEntry.getKey();
    }
}
//?}