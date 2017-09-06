package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.DBConnectionImpl;
import it.unibo.drescue.model.EventType;
import it.unibo.drescue.model.EventTypeImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EventTypeDaoImplTest {

    private static final EventType EVENT_TYPE_TEST =
            new EventTypeImpl(Integer.MIN_VALUE, "Test");

    private EventTypeDao eventTypeDao = null;
    private DBConnection dbConnection;

    @Before
    public void setUp() throws Exception {
        this.dbConnection = DBConnectionImpl.getLocalConnection();
        //Initialize EventDao
        this.eventTypeDao = (EventTypeDao) this.dbConnection.getDAO(DBConnection.Table.EVENT_TYPE);
    }

    @Test
    public void isInsertingAndDeletingEventType() throws Exception {
        this.eventTypeDao.insert(this.EVENT_TYPE_TEST);
        assertTrue(this.eventTypeDao.selectByIdentifier(this.EVENT_TYPE_TEST) != null);
        this.eventTypeDao.delete(this.EVENT_TYPE_TEST);
        assertTrue(this.eventTypeDao.selectByIdentifier(this.EVENT_TYPE_TEST) == null);
    }

    @Test
    public void isFindingAllEventTypes() throws Exception {
        final int initialSize = this.eventTypeDao.findAll().size();
        this.eventTypeDao.insert(this.EVENT_TYPE_TEST);
        assertTrue(this.eventTypeDao.findAll().size() == initialSize + 1);
        //Deleting test event_type
        this.eventTypeDao.delete(this.EVENT_TYPE_TEST);
        assertTrue(this.eventTypeDao.findAll().size() == initialSize);
    }

    @After
    public void tearDown() throws Exception {
        this.dbConnection.closeConnection();
    }

}