package it.unibo.drescue.communication.messages;

import it.unibo.drescue.communication.builder.CPConfigurationMessageBuilderImpl;
import it.unibo.drescue.model.RescueTeamImpl;
import it.unibo.drescue.model.RescueTeamImplBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CPConfigurationMessageTest {

    private static final String RESCUE_TEAM_ID_1 = "test_id_1";
    private static final String RESCUE_TEAM_NAME_1 = "test_name_1";
    private static final String RESCUE_TEAM_PASSWORD_1 = "test_password_1";
    private static final String RESCUE_TEAM_PHONE_NUMBER_1 = "test_phone_number_1";
    private static final String RESCUE_TEAM_ID_2 = "test_id_2";
    private static final String RESCUE_TEAM_NAME_2 = "test_name_2";
    private static final String RESCUE_TEAM_PASSWORD_2 = "test_password_2";
    private static final String RESCUE_TEAM_PHONE_NUMBER_2 = "test_phone_number_2";

    private List<RescueTeamImpl> rescueTeamList;
    private CPConfigurationMessage cpConfigurationMessage;

    @Before
    public void build() {

        final RescueTeamImpl rescueTeam1 = new RescueTeamImplBuilder()
                .setRescueTeamID(RESCUE_TEAM_ID_1)
                .setName(RESCUE_TEAM_NAME_1)
                .setPassword(RESCUE_TEAM_PASSWORD_1)
                .setPhoneNumber(RESCUE_TEAM_PHONE_NUMBER_1)
                .createRescueTeamImpl();

        final RescueTeamImpl rescueTeam2 = new RescueTeamImplBuilder()
                .setRescueTeamID(RESCUE_TEAM_ID_2)
                .setName(RESCUE_TEAM_NAME_2)
                .setPassword(RESCUE_TEAM_PASSWORD_2)
                .setPhoneNumber(RESCUE_TEAM_PHONE_NUMBER_2)
                .createRescueTeamImpl();

        this.rescueTeamList = new ArrayList<>();
        this.rescueTeamList.add(rescueTeam1);
        this.rescueTeamList.add(rescueTeam2);

        this.cpConfigurationMessage = (CPConfigurationMessage) new CPConfigurationMessageBuilderImpl()
                .setRescueTeamCollection(this.rescueTeamList)
                .build();

    }

    @Test
    public void checkCorrectMessageType() throws Exception {
        assertEquals(MessageType.CONFIGURATION_MESSAGE.getMessageType(), this.cpConfigurationMessage.getMessageType());
    }

    @Test
    public void checkCorrectName() {
        assertEquals(RESCUE_TEAM_NAME_1, this.cpConfigurationMessage.getRescueTeamCollection().get(0).getName());
        assertEquals(RESCUE_TEAM_NAME_2, this.cpConfigurationMessage.getRescueTeamCollection().get(1).getName());
    }

    @Test
    public void checkCorrectRescueTeamCollection() {
        assertNotNull(this.cpConfigurationMessage.getRescueTeamCollection());
        assertEquals(2, this.cpConfigurationMessage.getRescueTeamCollection().size());
    }


}
