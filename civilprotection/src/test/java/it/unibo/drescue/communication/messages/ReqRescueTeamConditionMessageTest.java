package it.unibo.drescue.communication.messages;

import it.unibo.drescue.communication.builder.ReqRescueTeamConditionMessageBuilderImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReqRescueTeamConditionMessageTest {

    private static final String FROM = "from_test";
    private static final String TO = "to_test";
    private static final String RESCUE_TEAM_ID = "RT001";

    private ReqRescueTeamConditionMessage reqRescueTeamConditionMessage;


    @Before
    public void build(){
        final Message reqMessage = new ReqRescueTeamConditionMessageBuilderImpl()
                .setRescueTeamID(RESCUE_TEAM_ID)
                .setFrom(FROM)
                .setTo(TO)
                .build();

        if (reqMessage.getMessageType().equals(MessageType.REQ_RESCUE_TEAM_CONDITION.getMessageType())){
            reqRescueTeamConditionMessage = (ReqRescueTeamConditionMessage) reqMessage;
        }
    }

    @Test
    public void checkRescueTeamId() {
        assertEquals(reqRescueTeamConditionMessage.getRescueTeamID(), RESCUE_TEAM_ID);
    }

    @Test
    public void checkCorrectFromTo() {
        assertEquals(this.reqRescueTeamConditionMessage.getFrom(), FROM);
        assertEquals(this.reqRescueTeamConditionMessage.getTo(), TO);
    }
}
