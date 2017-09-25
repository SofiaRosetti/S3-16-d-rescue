package it.unibo.drescue.communication.messages;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReqCoordinationMessageTest {

    private static final String FROM = "from_test";
    private static final String TO = "to_test";
    private static final String RESCUE_TEAM_ID = "RT001";
    private static final Timestamp REQ_TIMESTAMP = new Timestamp(System.currentTimeMillis());

    private ReqCoordinationMessage reqCoordinationMessage;

    @Before
    public void build(){
        reqCoordinationMessage = new ReqCoordinationMessage();
        reqCoordinationMessage.setFrom(FROM);
        reqCoordinationMessage.setTo(TO);
        reqCoordinationMessage.setRescueTeamID(RESCUE_TEAM_ID);
        reqCoordinationMessage.setTimestamp(REQ_TIMESTAMP);
    }

    @Test
    public void checkCorrectMessageType() throws Exception {
        assertEquals(MessageType.REQ_COORDINATION_MESSAGE.getMessageType(), this.reqCoordinationMessage.getMessageType());
    }

    @Test
    public void checkRescueTeamId() {
        assertEquals(reqCoordinationMessage.getRescueTeamID().toString(), RESCUE_TEAM_ID);
    }

    @Test
    public void checkCorrectFromTo() {
        assertEquals(this.reqCoordinationMessage.getFrom(), FROM);
        assertEquals(this.reqCoordinationMessage.getTo(), TO);
    }

    @Test
    public void checkReqTimestamp() {
        assertNotNull(this.reqCoordinationMessage.getTimestamp());
    }


}
