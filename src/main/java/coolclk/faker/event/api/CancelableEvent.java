package coolclk.faker.event.api;

public class CancelableEvent extends Event {
    protected boolean canceled = super.isCanceled();

    public void setCanceled(boolean cancel) {
        this.canceled = cancel;
    }

    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
