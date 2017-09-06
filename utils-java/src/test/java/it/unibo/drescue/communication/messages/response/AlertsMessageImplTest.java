package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.model.AlertImpl;
import it.unibo.drescue.model.AlertImplBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AlertsMessageImplTest {

    private static final int ALERT_ID1 = 1558;
    private static final double LATITUDE1 = 44.139773;
    private static final double LONGITUDE1 = 12.243283;
    private static final int ALERT_ID2 = 1558;
    private static final double LATITUDE2 = 44.139773;
    private static final double LONGITUDE2 = 12.243283;
    private List<AlertImpl> alertList;
    private AlertsMessageImpl alertsMessage;

    @Before
    public void createAlertList() {
        final AlertImpl alert1 = new AlertImplBuilder()
                .setAlertID(ALERT_ID1)
                .setLatitude(LATITUDE1)
                .setLongitude(LONGITUDE1)
                .createAlertImpl();

        final AlertImpl alert2 = new AlertImplBuilder()
                .setAlertID(ALERT_ID2)
                .setLatitude(LATITUDE2)
                .setLongitude(LONGITUDE2)
                .createAlertImpl();

        this.alertList = new ArrayList<>();
        this.alertList.add(alert1);
        this.alertList.add(alert2);
    }

    @Before
    public void build() {
        this.alertsMessage = new AlertsMessageImpl(this.alertList);
    }

    @Test
    public void checkCorrectType() throws Exception {
        final String messageType = this.alertsMessage.getMessageType();
        assertEquals(messageType, AlertsMessageImpl.ALERTS_MESSAGE);
    }

    @Test
    public void checkCorrectAlertList() throws Exception {
        assertEquals(this.alertList, this.alertsMessage.getAlerts());
    }

}