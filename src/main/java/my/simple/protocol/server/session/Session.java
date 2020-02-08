package my.simple.protocol.server.session;

import java.util.Date;
import java.util.UUID;

public class Session {
    private String sessionId;
    private int timeout;
    private Date date;

    private Session(String sessionId, int timeoutInMs, Date date) {
        this.sessionId = sessionId;
        this.timeout = timeoutInMs;
        this.date = date;
    }

    public static Session createNewSession(ISessionIdGenerator sessionIdGenerator, Date startDate, int timeout) {
        String sessionId = sessionIdGenerator.generate();
        return new Session(sessionId, timeout, startDate);
    }

    public int getTimeout() {
        return timeout;
    }

    public String getSessionId() {
        return sessionId;
    }

    public long duration(Date endDate) {
        return endDate.getTime() - this.date.getTime();
    }
}
