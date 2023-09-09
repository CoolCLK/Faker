package coolclk.faker.feature.modules;

import coolclk.faker.feature.api.Module;
import coolclk.faker.launch.forge.FakerForgeMod;
import coolclk.faker.util.I18nUtil;

import java.util.ArrayList;
import java.util.List;

public enum ModuleCategory {
    None,
    Combat(10, 10),
    Movement(70, 10),
    Player(150, 10),
    Render(220, 10);

    private final String translateKey;
    private final List<Module> modules;

    private int positionX, positionY;
    private boolean displayFold = false;

    ModuleCategory() {
        this.translateKey = FakerForgeMod.MODID + ".category." + name() + ".name";
        this.modules = new ArrayList<Module>();
    }

    ModuleCategory(int defaultPositionX, int defaultPositionY) {
        this();
        this.positionX = defaultPositionX;
        this.positionY = defaultPositionY;
    }

    public void addModule(Module module) {
        this.modules.add(module);
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public String getDisplayName() {
        return I18nUtil.format(this.translateKey);
    }

    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    public void addPosition(int x, int y) {
        this.setPosition(this.getPositionX() + x, this.getPositionY() + y);
    }

    public boolean getDisplayFold() {
        return this.displayFold;
    }

    public void setDisplayFold(boolean fold) {
        this.displayFold = fold;
    }

    public void toggleDisplayFold() {
        this.setDisplayFold(!this.getDisplayFold());
    }

    public static List<Module> getAllModules() {
        final List<Module> modules = new ArrayList<Module>();
        for (ModuleCategory category : ModuleCategory.values()) {
            modules.addAll(category.getModules());
        }
        return modules;
    }
}
