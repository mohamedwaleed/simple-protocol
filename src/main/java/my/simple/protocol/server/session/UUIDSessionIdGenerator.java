package my.simple.protocol.server.session;

import java.util.UUID;

public class UUIDSessionIdGenerator implements ISessionIdGenerator {

    @Override
    public String generate() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
