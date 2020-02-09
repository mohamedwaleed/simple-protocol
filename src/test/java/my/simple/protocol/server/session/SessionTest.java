package my.simple.protocol.server.session;

import my.simple.protocol.server.session.Session;
import my.simple.protocol.server.session.UUIDSessionIdGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Date;

public class SessionTest {

    @Mock
    private UUIDSessionIdGenerator sessionIdGenerator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateSession() {
        Date currentData = new Date();
        int timeout = 30000;
        String sessionId = "uuid";

        Mockito.when(sessionIdGenerator.generate()).thenReturn(sessionId);

        Session session = Session.createNewSession(sessionIdGenerator, currentData, timeout);

        Assert.assertNotNull(session);
        Assert.assertEquals(sessionId, session.getSessionId());
        Assert.assertEquals(timeout, session.getTimeout());
    }

    @Test
    public void testSessionDuration() {
        Date currentData = new Date();
        int timeout = 30000;

        Session session = Session.createNewSession(sessionIdGenerator, currentData, timeout);

        Date endData = new Date();
        long actualDuration = session.duration(endData);
        long expectedDuration = endData.getTime() - currentData.getTime();
        Assert.assertEquals(expectedDuration, actualDuration);
    }
}
