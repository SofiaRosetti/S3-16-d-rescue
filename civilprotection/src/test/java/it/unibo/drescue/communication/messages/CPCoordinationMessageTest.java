package it.unibo.drescue.communication.messages;

import it.unibo.drescue.communication.builder.CPConfigurationMessageBuilderImpl;
import it.unibo.drescue.communication.builder.CPCoordinationMessageBuilderImpl;
import it.unibo.drescue.model.RescueTeamImpl;
import it.unibo.drescue.model.RescueTeamImplBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CPCoordinationMessageTest {

    private static final String FROM = "test_from";
    private static final String TO = "test_to";
    private static final String RESCUE_TEAM_ID = "test_id";
    private static final String RESCUE_TEAM_NAME = "test_name";
    private static final String RESCUE_TEAM_PASSWORD = "test_password";
    private static final String RESCUE_TEAM_PHONE_NUMBER = "test_phone_number";

    private CPCoordinationMessage cpCoordinationMessage;

    @Before
    public void build(){

        final RescueTeamImpl rescueTeam = new RescueTeamImplBuilder()
                .setRescueTeamID(RESCUE_TEAM_ID)
                .setName(RESCUE_TEAM_NAME)
                .setPassword(RESCUE_TEAM_PASSWORD)
                .setPhoneNumber(RESCUE_TEAM_PHONE_NUMBER)
                .createRescueTeamImpl();

        this.cpCoordinationMessage = (CPCoordinationMessage) new CPCoordinationMessageBuilderImpl()
                .setRescueTeam(rescueTeam)
                .setFrom(FROM)
                .setTo(TO)
                .build();
    }


    @Test
    public void checkCorrectMessageType() throws Exception {
        assertEquals(this.cpCoordinationMessage.getMessageType(), CPCoordinationMessage.COORDINATION_MESSAGE);
    }

    @Test
    public void checkCorrectFromTo() {
        assertEquals(this.cpCoordinationMessage.getFrom(), FROM);
        assertEquals(this.cpCoordinationMessage.getTo(), TO);
    }

    @Test
    public void checkRescueTeam() {
        assertNotNull(this.cpCoordinationMessage.getRescueTeam());
        assertEquals(this.cpCoordinationMessage.getRescueTeam().getName(), RESCUE_TEAM_NAME);
        assertEquals(this.cpCoordinationMessage.getRescueTeam().getRescueTeamID(), RESCUE_TEAM_ID);
    }

}
