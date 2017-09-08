package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.model.EventType;
import it.unibo.drescue.model.EventTypeImpl;
import it.unibo.drescue.model.ObjectModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventTypeDaoImplTest extends GenericDaoAbstractTest {

    private static final EventType EVENT_TYPE_TEST =
            new EventTypeImpl(Integer.MIN_VALUE, "Test");

    private EventTypeDao eventTypeDao = null;

    @Override
    protected GenericDao getDaoForTest(final DBConnection connectionForTest) throws Exception {
        return this.eventTypeDao = (EventTypeDao)
                connectionForTest.getDAO(DBConnection.Table.EVENT_TYPE);
    }

    @Override
    protected ObjectModel getTestObject() {
        return EVENT_TYPE_TEST;
    }

    @Override
    protected void doOtherSetUp(final DBConnection connectionForTest) throws Exception {
        //DO NOTHING
    }

    @Override
    protected void doOtherTearDown() {
        //DO NOTHING
    }

    /**
     * Test findAll functionality.
     */
    @Test
    public void isFindingAllEventTypes() throws Exception {
        final int initialSize = this.eventTypeDao.findAll().size();
        this.eventTypeDao.insert(EVENT_TYPE_TEST);
        assertEquals(this.eventTypeDao.findAll().size(), initialSize + 1);
        //Deleting test event_type
        this.eventTypeDao.delete(EVENT_TYPE_TEST);
        assertEquals(this.eventTypeDao.findAll().size(), initialSize);
    }
}