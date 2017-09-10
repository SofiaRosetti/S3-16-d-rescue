package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.model.*;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AlertAndUpvotedAlertDaoTest extends GenericDaoAbstractTest {

    private static final int LAST_X = 50;
    private static final double LATITUDE_TEST = -34.397;
    private static final double LONGITUDE_TEST = 150.644;
    private District districtTest = (District) new DistrictDaoImplTest().getTestObject();
    private EventType eventTypeTest = (EventType) new EventTypeDaoImplTest().getTestObject();
    private User userTest = (User) new UserDaoImplTest().getTestObject();
    private AlertDao alertDao;
    private UserDao userDao;
    private DistrictDao districtDao;
    private EventTypeDao eventTypeDao;
    private UpvotedAlertDao upvotedAlertDao;
    private Alert alertTest;

    @Override
    protected GenericDao getDaoForTest(final DBConnection connectionForTest) throws Exception {
        return this.alertDao = (AlertDao) connectionForTest.getDAO(DBConnection.Table.ALERT);
    }

    @Override
    protected void doOtherSetUp(final DBConnection connectionForTest) throws Exception {
        //initializing dao
        this.userDao = (UserDao) connectionForTest.getDAO(DBConnection.Table.USER);
        this.districtDao = (DistrictDao) connectionForTest.getDAO(DBConnection.Table.DISTRICT);
        this.eventTypeDao = (EventTypeDao) connectionForTest.getDAO(DBConnection.Table.EVENT_TYPE);
        //insert a test user
        this.userTest = (User) this.userDao.insertAndGet(this.userTest);
        //insert a test district
        this.districtTest = (District) this.districtDao.insertAndGet(this.districtTest);
        //insert a test event type
        this.eventTypeTest = (EventType) this.eventTypeDao.insertAndGet(this.eventTypeTest);

        this.alertTest = new AlertImplBuilder()
                .setTimestamp(this.getCurrentTimestampForDb())
                .setLatitude(LATITUDE_TEST)
                .setLongitude(LONGITUDE_TEST)
                .setUserID(this.userTest.getUserID())
                .setEventName(this.eventTypeTest.getEventName())
                .setDistrictID(this.districtTest.getDistrictID())
                .setUpvotes(0)
                .createAlertImpl();

        //Setup upvotedAlertDao
        this.upvotedAlertDao = (UpvotedAlertDao)
                connectionForTest.getDAO(DBConnection.Table.UPVOTED_ALERT);
    }

    @Override
    protected ObjectModel getTestObject() {
        return this.alertTest;
    }

    @Override
    protected void doOtherTearDown() {
        //delete test user
        this.userDao.delete(this.userTest);
        //delete test district
        this.districtDao.delete(this.districtTest);
        //delete test event type
        this.eventTypeDao.delete(this.eventTypeTest);
    }

    /**
     * Test update method functionality.
     * For alert the update concerns upvotes
     */
    @Test
    public void isUpvotingAnAlert() throws Exception {

        //Insert test alert
        Alert alertInDb = (Alert) this.alertDao.insertAndGet(this.alertTest);

        //get upvotes before updating for testing
        final int upvotesBefore = alertInDb.getUpvotes();

        //Insert upvoted alert
        final UpvotedAlert upvotedAlert =
                new UpvotedAlertImpl(this.userTest.getUserID(), alertInDb.getAlertID());
        this.upvotedAlertDao.insert(upvotedAlert);

        //If this ended up without exception the record is added into DB
        //Now we have to update redundant field "upvotes" in alert table
        alertInDb.setUpvotes(alertInDb.getUpvotes() + 1);
        this.alertDao.update(alertInDb);

        //check if the field in db was updated
        alertInDb = (Alert) this.alertDao.selectByIdentifier(alertInDb);
        assertEquals(upvotesBefore + 1, alertInDb.getUpvotes());

        //Deleting test upvote
        this.upvotedAlertDao.delete(upvotedAlert);
        //Deleting test alert
        this.alertDao.delete(this.alertTest);
    }

    @Test
    public void isFindingLastXAlert() throws Exception {
        this.alertDao.insert(this.alertTest);
        final List<Alert> alertList = this.alertDao.findLast(LAST_X);
        assertNotEquals(alertList.size(), 0);
        //assertTrue(alertList.contains(this.alertTest)); //TODO
        this.alertDao.delete(this.alertTest);
    }

    private Timestamp getCurrentTimestampForDb() {
        final Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        currentTimestamp.setNanos(0);
        return currentTimestamp;
    }
}