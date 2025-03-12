package me.clefal.whats_your_build.client.screen;

import javax.swing.*;
import java.util.function.Supplier;

public abstract class BuildMenuTab<T extends BuildMenu> extends AbstractButton {
    protected Supplier<T> menu;

}
