package coolclk.faker.modules;

import net.minecraft.client.resources.I18n;

import java.util.Arrays;
import java.util.List;

public class ModuleType {
    private final String name;
    private final List<Module> modules;
    private boolean fold = false;

    private int clickGuiPositionX = 0, clickGuiPositionY = 0;

    public ModuleType(String name, Module... modules) {
        this.name = name;
        this.modules = Arrays.asList(modules);
    }

    public ModuleType(String name, int posX, int posY, Module... modules) {
        this(name, modules);
        this.clickGuiPositionX = posX;
        this.clickGuiPositionY = posY;
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public void setFold(boolean fold) {
        this.fold = fold;
    }

    public boolean getFold() {
        return this.fold;
    }

    public String getDisplayName() {
        return I18n.format("modules.type." + name + ".name");
    }

    public String getName() {
        return name;
    }

    public int getClickGuiPositionX() {
        return this.clickGuiPositionX;
    }

    public int getClickGuiPositionY() {
        return this.clickGuiPositionY;
    }

    public void setClickGuiPositionX(int x) {
        this.clickGuiPositionX = x;
    }

    public void setClickGuiPositionY(int y) {
        this.clickGuiPositionY = y;
    }

    public void setClickGuiPosition(int x, int y) {
        this.setClickGuiPositionX(x);
        this.setClickGuiPositionY(y);
    }
}
