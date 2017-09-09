package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.model.*;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class AlertDaoImplTest extends GenericDaoAbstractTest {

    private static final double LATITUDE_TEST = -34.397;
    private static final double LONGITUDE_TEST = 150.644;
    private static final String DATA_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String TIMESTAMP_TEST = "2017-07-04 18:55:21";
    private District districtTest = (District) new DistrictDaoImplTest().getTestObject();
    private EventType eventTypeTest = (EventType) new EventTypeDaoImplTest().getTestObject();
    private User userTest = (User) new UserDaoImplTest().getTestObject();
    private AlertDao alertDao;
    private UserDao userDao;
    private DistrictDao districtDao;
    private EventTypeDao eventTypeDao;
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
        //insert a test user and
        this.userDao.insert(this.userTest);
        this.userTest = (User) this.userDao.selectByIdentifier(this.userTest);
        //insert a test district
        this.districtDao.insert(this.districtTest);
        this.districtTest = (District) this.districtDao.selectByIdentifier(this.districtTest);
        //insert a test event type
        this.eventTypeDao.insert(this.eventTypeTest);
        this.eventTypeTest = (EventType) this.eventTypeDao.selectByIdentifier(this.eventTypeTest);

        //Create alert
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_PATTERN, Locale.US);
        final Timestamp timestamp = new Timestamp(dateFormat.parse(TIMESTAMP_TEST).getTime());
        this.alertTest = new AlertImplBuilder()
                .setTimestamp(timestamp)
                .setLatitude(LATITUDE_TEST)
                .setLongitude(LONGITUDE_TEST)
                .setUserID(this.userTest.getUserID())
                .setEventID(this.eventTypeTest.getEventID())
                .setDistrictID(this.districtTest.getDistrictID())
                .setUpvotes(0)
                .createAlertImpl();
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
        this.alertDao.insert(this.alertTest);

        Alert alertInDb = (Alert) this.alertDao.selectByIdentifier(this.alertTest);
        final int upvotesBefore = alertInDb.getUpvotes();

        alertInDb.setUpvotes(alertInDb.getUpvotes() + 1);

        this.alertDao.update(alertInDb);
        alertInDb = (Alert) this.alertDao.selectByIdentifier(alertInDb);

        assertEquals(upvotesBefore + 1, alertInDb.getUpvotes());

        //Deleting test district
        this.alertDao.delete(this.alertTest);
    }
}