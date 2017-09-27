package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpvotedAlertImplTest {

    private static final int USER_ID = 2811;
    private static final int ALERT_ID = 39766;
    private UpvotedAlert upvotedAlert;

    @Before
    public void createUpvotedAlert() throws Exception {
        this.upvotedAlert = new UpvotedAlertImpl(USER_ID, ALERT_ID);
    }

    @Test
    public void checkCorrectUserID() throws Exception {
        final int userID = this.upvotedAlert.getUserID();
        assertEquals(userID, USER_ID);
    }

    @Test
    public void checkCorrectAlertID() throws Exception {
        final int alertID = this.upvotedAlert.getAlertID();
        assertEquals(alertID, ALERT_ID);
    }

}