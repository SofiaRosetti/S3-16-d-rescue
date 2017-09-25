package it.unibo.drescue.communication.messages;

import it.unibo.drescue.communication.builder.ReplyRescueTeamConditionMessageBuilderImpl;
import it.unibo.drescue.utils.RescueTeamCondition;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ReplyRescueTeamConditionMessageTest {

    private static final String FROM = "from_test";
    private static final String TO = "to_test";
    private static final String RESCUE_TEAM_ID = "RT001";
    private static final RescueTeamCondition RESCUE_TEAM_CONDITION = RescueTeamCondition.AVAILABLE;

    private ReplyRescueTeamConditionMessage replyRescueTeamConditionMessage;

    @Before
    public void build(){
        final Message replyMessage = new ReplyRescueTeamConditionMessageBuilderImpl()
                .setRescueTeamID(RESCUE_TEAM_ID)
                .setRescueTeamCondition(RESCUE_TEAM_CONDITION)
                .setFrom(FROM)
                .build();

        if (replyMessage.getMessageType().equals(MessageType.REPLY_RESCUE_TEAM_CONDITION.getMessageType())){
            replyRescueTeamConditionMessage = (ReplyRescueTeamConditionMessage) replyMessage;
        }
    }

    @Test
    public void checkRescueTeamIdAndCondition() {
        assertEquals(replyRescueTeamConditionMessage.getRescueTeamCondition(), RESCUE_TEAM_CONDITION);
        assertEquals(replyRescueTeamConditionMessage.getRescueTeamID().toString(), RESCUE_TEAM_ID);
    }

    @Test
    public void checkCorrectFromTo() {
        assertEquals(this.replyRescueTeamConditionMessage.getFrom(), FROM);
        assertNotEquals(this.replyRescueTeamConditionMessage.getTo(), TO);
    }
}
