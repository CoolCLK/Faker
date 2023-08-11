package coolclk.faker.feature.api;

public interface IModule {
    void setEnable(boolean enable);
    boolean getEnable();
    void toggleModule();
    void onEnable();
    void onEnabling();
    void onDisable();
}
