package coolclk.faker.event.events;

import coolclk.faker.event.api.Event;

public class ClientConnectToServerEvent extends Event {
    protected final String ip;
    protected final int port;

    public ClientConnectToServerEvent(final String ip, final int port) {
        this.ip = ip;
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public String getIp() {
        return this.ip;
    }
}
