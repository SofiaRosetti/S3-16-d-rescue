package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.DBConnectionImpl;
import it.unibo.drescue.model.ObjectModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public abstract class GenericDaoAbstractTest {

    private GenericDao genericDao;
    private DBConnection dbConnection;

    private void startConnection() {
        this.dbConnection = DBConnectionImpl.getLocalConnection();
    }

    private void closeConnection() {
        this.dbConnection.closeConnection();
    }

    /**
     * TODO
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.startConnection();
        //Initialize Dao
        this.genericDao = this.getDaoForTest(this.dbConnection);
        
        this.doOtherSetUp(this.dbConnection);
    }

    /**
     * TODO
     *
     * @param connectionForTest
     * @return
     */
    protected abstract GenericDao getDaoForTest(DBConnection connectionForTest) throws Exception;

    /**
     * TODO
     *
     * @param connectionForTest
     * @throws Exception
     */
    protected abstract void doOtherSetUp(DBConnection connectionForTest) throws Exception;

    /**
     * TODO
     *
     * @throws Exception
     */
    @Test
    public void isInsertingAndDeletingCivilProtection() throws Exception {
        this.genericDao.insert(this.getTestObject());
        assertNotNull(this.genericDao.selectByIdentifier(this.getTestObject()));
        this.genericDao.delete(this.getTestObject());
        assertNull(this.genericDao.selectByIdentifier(this.getTestObject()));
    }

    /**
     * TODO
     *
     * @return
     */
    protected abstract ObjectModel getTestObject();

    /**
     * TODO
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        this.doOtherTearDown();
        this.closeConnection();
    }

    /**
     * TODO
     */
    protected abstract void doOtherTearDown();
}
