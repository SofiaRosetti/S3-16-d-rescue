package it.unibo.drescue.communication.messages;

import it.unibo.drescue.utils.RescueTeamCondition;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ReplyCoordinationMessageTest {

    private static final String FROM = "from_test";
    private static final String TO = "to_test";
    private static final String RESCUE_TEAM_ID = "RT001";
    private static final RescueTeamCondition RESCUE_TEAM_CONDITION = RescueTeamCondition.AVAILABLE;

    private ReplyCoordinationMessage replyCoordinationMessage;

    @Before
    public void build(){
        replyCoordinationMessage = new ReplyCoordinationMessage();
        replyCoordinationMessage.setFrom(FROM);
        replyCoordinationMessage.setTo(TO);
        replyCoordinationMessage.setRTcondition(RESCUE_TEAM_CONDITION);
        replyCoordinationMessage.setRescueTeamID(RESCUE_TEAM_ID);
    }

    @Test
    public void checkCorrectMessageType() throws Exception {
        assertEquals(MessageType.REPLAY_COORDINATION_MESSAGE.getMessageType(), this.replyCoordinationMessage.getMessageType());
    }

    @Test
    public void checkRescueTeamIdAndCondition() {
        assertEquals(replyCoordinationMessage.getRTCondition(), RESCUE_TEAM_CONDITION);
        assertEquals(replyCoordinationMessage.getRescueTeamID().toString(), RESCUE_TEAM_ID);
    }

    @Test
    public void checkCorrectFromTo() {
        assertEquals(this.replyCoordinationMessage.getFrom(), FROM);
        assertEquals(this.replyCoordinationMessage.getTo(), TO);
    }


}
