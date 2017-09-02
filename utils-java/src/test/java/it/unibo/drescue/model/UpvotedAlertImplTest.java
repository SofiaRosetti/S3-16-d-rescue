package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UpvotedAlertImplTest {

    private static final int USER_ID = 2811;
    private static final int ALERT_ID = 39766;
    private UpvotedAlert upvotedAlert;

    @Before
    public void createUpvotedAlert() throws Exception {
        this.upvotedAlert = new UpvotedAlertImpl(this.USER_ID, this.ALERT_ID);
    }

    @Test
    public void checkCorrectUserID() throws Exception {
        final int userID = this.upvotedAlert.getUserID();
        assertTrue(userID == this.USER_ID);
    }

    @Test
    public void checkCorrectAlertID() throws Exception {
        final int alertID = this.upvotedAlert.getAlertID();
        assertTrue(alertID == this.ALERT_ID);
    }

}