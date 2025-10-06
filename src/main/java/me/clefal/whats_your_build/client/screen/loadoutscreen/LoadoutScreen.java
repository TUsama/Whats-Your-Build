package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.clefal.nirvana_lib.client.render.batch.DrawStringBufferInfo;
import com.clefal.nirvana_lib.client.render.batch.TextureBufferInfo;
import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import com.clefal.nirvana_lib.client.render.rendertype.RenderTypeCreator;
import com.clefal.nirvana_lib.relocated.io.vavr.API;
import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.Map;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.ExtensionMethod;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.client.components.RightClickMenu;
import me.clefal.whats_your_build.client.components.WYBImageButton;
import me.clefal.whats_your_build.client.screen.WYBScreen;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.loadoutscreen.components.BuildEntryFunctionButton;
import me.clefal.whats_your_build.client.screen.loadoutscreen.components.WearButton;
import me.clefal.whats_your_build.client.storage.LoadoutsClientHandler;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.data.modules.armor.VanillaArmorComponent;
//? curios {
import me.clefal.whats_your_build.data.modules.compat.curios.CuriosComponent;
import me.clefal.whats_your_build.data.modules.compat.curios.loadout.CuriosLoadDescriber;
//?}
//? mas {
/*import me.clefal.whats_your_build.data.modules.compat.mas.talent.MASSkillComponent;
import me.clefal.whats_your_build.data.modules.compat.mas.talent.client.MASSkillViewButton;
import me.clefal.whats_your_build.mixinhelper.ITalentDataGetter;
*///?}
import me.clefal.whats_your_build.utils.WidgetHelper;
import me.clefal.whats_your_build.world.BuildMenu;
import me.clefal.whats_your_build.world.IBuildHandler;
import me.clefal.whats_your_build.world.IRewritable;
import me.clefal.whats_your_build.world.loadout.LoadoutMenu;
import me.clefal.whats_your_build.world.loadout.load.LoadDescriber;
import me.clefal.whats_your_build.world.loadout.load.VanillaItemLoadDescriber;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
//? >1.20.1
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

@ExtensionMethod(WidgetHelper.class)
public class LoadoutScreen extends WYBScreen<LoadoutMenu> implements IBuildHandler, IRewritable {
    public static final ResourceLocation INVENTORY_LOCATION = CommonClass.id("textures/gui/container/background.png");
    public static final ResourceLocation ARMORY = CommonClass.id("textures/gui/container/armory.png");
    public LoadoutSelectionList buildList;
    public VertexContainer vertexContainer = new VertexContainer();
    protected LinkedHashMap<String, BuildMenu.ComponentHandler> placePlan = new LinkedHashMap<>();
    //only exists on the client!
    @Getter
    private LinkedHashMap<String, NonNullList<Slot>> slotMap = new LinkedHashMap<>();
    private List<BuildMenuTab<?>> tabs;
    private WYBImageButton addNewEntry;
    private RightClickMenu rightClickMenu;
    private final static NonNullList<Slot> EMPTY = NonNullList.create();
    private Map<String, WearButton> wears;
    @Getter
    protected java.util.Map<String, List<? extends AbstractWidget>> widgets = new HashMap<>();

    @Nullable
    @Setter
    private LoadoutSelectionList.BuildEntry currentEditingEntry;

    public LoadoutScreen(LoadoutMenu menu, Inventory playerInventory) {
        super(menu, playerInventory, Component.literal(""));

    }


    @Override
    protected void init() {
        super.init();

        this.tabs = HandlerManager.getInstance().getImmutableBuildMenuTabFunction(menu.getSelfBuild()).map(x -> x.apply(this, this));
        this.addNewEntry = new WYBImageButton(0, 0, 8, 8, button -> {
            buildList.addSelfBuildEntry(menu.getSelfBuild().copy());
        },
                //? >1.20.1
                new WidgetSprites(CommonClass.gui("sprites/loadout/add_new_entry"), CommonClass.gui("sprites/loadout/add_new_entry")),
                //? 1.20.1
                /*"sprites/loadout/add_new_entry",*/
                vertexContainer){
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                PoseStack pose = guiGraphics.pose();
                pose.pushPose();
                ResourceLocation resourcelocation = getRenderResourceLocation();
                this.container.putBliz(resourcelocation, TextureBufferInfo.of(getX(), getY(), getWidth(), getHeight(), 0, 0,32, 32, 32, 32, guiGraphics.pose().last().pose()));
                pose.popPose();
            }
        };
        this.addNewEntry.setTooltip(Tooltip.create(Component.translatable("wyb.screen.loadout.tab.new")));
        addNewEntry.setPosition(leftPos, topPos - 12);

        this.rightClickMenu = new RightClickMenu(0, 0, 0, 0, Component.literal(""));


        addRenderableWidget(addNewEntry);

        tabs.forEachWithIndex((buildMenuTab, value) -> {
            buildMenuTab.setSize(12, buildMenuTab.getHeight());
            buildMenuTab.setPosition(leftPos + 80 + buildMenuTab.getWidth() * value, topPos - 8);
            this.addRenderableWidget(buildMenuTab);
        });

        this.wears = API.Map(VanillaArmorComponent.ID, Util.make(() -> {
                    var button = new WearButton(menu.getPos(), this) {
                        @Override
                        public LoadDescriber<?> getDescriber() {
                            return new VanillaItemLoadDescriber(this.getScreen().safeGetCurrentSlots().stream().filter(Slot::hasItem).map(x -> x.getItem().copy()).toList());
                        }
                    };
                    button.setPosition(leftPos + 150, topPos + 60);
                    return button;

        })
                //? curios {
                , CuriosComponent.ID, Util.make(() -> {
                    var button = new WearButton(menu.getPos(), this) {
                        @Override
                        public LoadDescriber<?> getDescriber() {
                            return new CuriosLoadDescriber(this.getScreen().safeGetCurrentSlots().stream().filter(Slot::hasItem).map(x -> x.getItem().copy()).toList());
                        }
                    };
                    button.setPosition(leftPos + 150, topPos + 60);
                    return button;
                })
                //?}
        );

        this.buildList = new LoadoutSelectionList(80, 60, topPos, topPos + 80, this);
        try {
            buildList.addBuildsOnInit(LoadoutsClientHandler.readAllFromLocal(minecraft.player.getUUID()));
        } catch (IOException e) {
            Constants.LOG.error("Failed to load builds from local when initialize LoadoutScreen: {}", String.valueOf(e));
        }

        //? >1.20.1 {
        buildList.setX(leftPos);
        buildList.setY(topPos);
        //?} else {
        /*buildList.setLeftPos(leftPos);
        *///?}
        addRenderableWidget(buildList);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        boolean b = buildList.noBuild();
        if (b) {
            this.tabs.forEach(x -> {
                x.active = false;
                x.visible = false;
            });
            this.wears.forEach((string, wearButton) -> {
                wearButton.active = false;
                wearButton.visible = false;
            });
            vertexContainer.putString(DrawStringBufferInfo.of(Component.translatable("wyb.screen.loadout.no_loadout").getString(), leftPos + 100, topPos + buildList.getHeight() / 2, ChatFormatting.GRAY.getColor(), guiGraphics.pose().last().pose()));
        } else {
            this.tabs.forEach(x -> {
                x.active = true;
                x.visible = true;
            });
            if (currentAt.isEmpty()){
                this.wears.forEach((string, wearButton) -> {
                    wearButton.active = false;
                    wearButton.visible = false;
                });
            } else {
                this.wears.get(currentAt).forEach(wearButton -> {
                    wearButton.active = true;
                    wearButton.visible = true;
                });
            }
        }


        super.render(guiGraphics, mouseX, mouseY, partialTick);

        //this is ugly but in 1.20.1 this method has to be placed behind the super.render();
        //otherwise the words on tooltips will be covered
        //weird depth issue i guess
        //also happen on 1.20.1
        if (!b) tryRenderClientSlot(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
        buildList.renderHoveredTooltips(guiGraphics, mouseX, mouseY);
        vertexContainer.draw(guiGraphics.bufferSource(), RenderTypeCreator.guiBlend);
    }

    private void tryRenderClientSlot(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        NonNullList<Slot> slots1 = safeGetCurrentSlots();

        for (int k = 0; k < slots1.size(); k++) {
            Slot slot = slots1.get(k);
            if (slot.isActive()) {
                this.renderSlot(guiGraphics, slot);
            }
            boolean hovering = this.isHovering1(slot.x, slot.y, 16, 16, mouseX, mouseY);

            if (hovering) {
                this.hoveredSlot = slot;
                //? neoforge {
                this.renderSlotHighlight(guiGraphics, slot, mouseX, mouseY, partialTick);
                //?} else {
                /*renderSlotHighlight(guiGraphics, slot.x, slot.y, 0);
                *///?}
            }
        }
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

    public void addButtonForMenu(WYBImageButton button, double mouseX, double mouseY, LoadoutSelectionList.BuildEntry entry){
        if (button instanceof BuildEntryFunctionButton buildEntryFunctionButton && entry != null){
            buildEntryFunctionButton.entry = entry;
            this.rightClickMenu.buttons.add(buildEntryFunctionButton);
        }
    }

    public void removeAllButtonsInMenu(){
        this.rightClickMenu.buttons.clear();
    }

    private void tryRemoveCurrentMenu(){
        if (this.children().contains(rightClickMenu)) {
            //setFocused(null);
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
            List<WYBImageButton> buttons = List.empty();
            if (currentEntry.currentState instanceof BuildEntryState.Editing) {
                buttons = List.of(buildList.save, buildList.reset, buildList.clear, buildList.delete);
            } else if (currentEntry.currentState instanceof BuildEntryState.Waiting){
                buttons = List.of(buildList.clear, buildList.delete);
            }
            for (WYBImageButton wybImageButton : buttons){
                    addButtonForMenu(wybImageButton, mouseX, mouseY, currentEntry);
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
        if (this.children().contains(rightClickMenu) && rightClickMenu.isMouseOver(mouseX, mouseY) && rightClickMenu.mouseClicked(mouseX, mouseY, button)){
            tryRemoveCurrentMenu();
            return true;
        }
        boolean b = super.mouseClicked(mouseX, mouseY, button);
        tryRemoveCurrentMenu();
        //try to add right click menu
        if (button == 1){
            tryAddMenu(mouseX, mouseY);
        }
        //handle slot
        boolean flag = false;
        for (Slot slot : safeGetCurrentSlots()) {
            if (isHovering1(slot.x, slot.y, 16, 16, mouseX, mouseY)) {
                ItemStack carried = menu.getCarried();
                boolean noItemInSlot = !slot.hasItem();
                slot.set(carried.copy());
                if (!(carried.isEmpty() && noItemInSlot)) flag = true;
            }
        }
        if (flag){
            markEdited(this);
        }

        return b;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        tryRemoveCurrentMenu();
        return super.charTyped(codePoint, modifiers);
    }

    private void addSlot(String id, Container container, int index, int x, int y) {
        NonNullList<Slot> slots1 = this.slotMap.get(id);
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
            public ItemStack safeTake(int count, int decrement, Player player) {
                return ItemStack.EMPTY;
            }
        };

        if (slots1 == null){
            NonNullList<Slot> objects = NonNullList.create();
            objects.add(slot);
            this.slotMap.put(id, objects);
        } else {
            slots1.add(slot);
        }
    }

    @Override
    public void handleBuild(Build build) {
        Map<String, ? extends IBuildComponent<?>> map = build.getComponents()
                .map((aByte, iBuildComponent) -> Tuple.of(iBuildComponent.getIdentifier(), iBuildComponent));
        placePlan.clear();
        this.widgets.forEach((string, abstractWidgets) -> abstractWidgets.forEach(this::removeWidget));
        widgets.clear();
        map
                .get(VanillaArmorComponent.ID)
                .forEach(iBuildComponent -> placePlan.put(VanillaArmorComponent.ID,
                        () -> {
                            var simpleContainer = ((VanillaArmorComponent) iBuildComponent).asContainer();
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
        //? curios {
        map
                .get(CuriosComponent.ID)
                .forEach(iBuildComponent -> placePlan.put(CuriosComponent.ID,
                        () -> {
                            var simpleContainer = ((CuriosComponent) iBuildComponent).asContainer();

                            int k = 0;
                            int j = 0;
                            for (int i = 0; i < simpleContainer.getContainerSize(); i++) {
                                if (k >= 4) {
                                    j++;
                                    k = 0;
                                }
                                addSlot(CuriosComponent.ID, simpleContainer, i, leftPos + j * 18 + 90, topPos + k * 18);
                                k++;
                            }
                        }));
        //?}

        //? mas {

        /*map
                .get(MASSkillComponent.ID)
                .forEach(iBuildComponent -> {
                    widgets.put(MASSkillComponent.ID, Util.make(() -> MASSkillViewButton.getViewButtonInLoadoutScreen(this, ((MASSkillComponent) iBuildComponent))));
                    placePlan.put(MASSkillComponent.ID, () -> widgets.get(MASSkillComponent.ID).forEachWithIndex((x, y) -> {
                        x.setPosition(leftPos + buildList.getWidth(), topPos + 30 + y * (x.getHeight() + 2));
                        addRenderableWidget(x);
                    }));

                });
        *///?}


    }

    public static void markEdited(LoadoutScreen screen) {
        if (screen.currentEditingEntry != null && screen.currentEditingEntry.currentState instanceof BuildEntryState.Editing editing){
            editing.isEdited = true;
        }
    }

    protected String currentAt = "";

    public NonNullList<Slot> safeGetCurrentSlots(){
        if (currentAt.isEmpty()){
            if (slotMap.isEmpty()){
                if (placePlan.isEmpty()){
                    return EMPTY;
                } else {
                    rewriteSlotsToFirst();
                }
            }
            var stringNonNullListEntry = takeFirst(slotMap);
            if (stringNonNullListEntry != null){
                return stringNonNullListEntry.getValue();
            } else {
                return EMPTY;
            }
        } else {
            NonNullList<Slot> slots = slotMap.get(currentAt);
            if (slots == null) return EMPTY;
            return slots;
        }
    }
    @Override
    public void rewriteSlots(String identifier) {
        BuildMenu.ComponentHandler componentHandler = placePlan.get(identifier);
        if (componentHandler == null){
            Constants.LOG.info("No {} component in this loadout!", identifier);
        } else {
            this.slotMap.clear();
            this.widgets.forEach((string, abstractWidgets) -> abstractWidgets.forEach(this::removeWidget));
            wears.forEach(stringWearButtonTuple2 -> this.removeWidget(stringWearButtonTuple2._2));
            placePlan.get(identifier).handle();
            currentAt = identifier;
            wears.get(identifier).forEach(this::addRenderableWidget);
        }
    }

    public void rewriteSlotsToFirst() {
        java.util.Map.Entry<String, BuildMenu.ComponentHandler> stringSlotPlacerEntry = takeFirst(placePlan);
        if (stringSlotPlacerEntry == null){
            Constants.LOG.info("fail to rewrite the slots to first!");
        } else {
            rewriteSlots(stringSlotPlacerEntry.getKey());
        }

    }

    private <T, R> java.util.Map.Entry<T, R> takeFirst(java.util.Map<T, R> map){
        var it = map.entrySet().iterator();
        return it.hasNext() ? it.next() : null;
    }
}
