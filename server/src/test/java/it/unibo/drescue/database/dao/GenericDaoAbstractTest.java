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
        this.dbConnection = DBConnectionImpl.getRemoteConnection();
    }

    private void closeConnection() {
        this.dbConnection.closeConnection();
    }

    /**
     * Template Method for setting up connection
     * and all utils data access objects
     *
     * @throws Exception if DB address is not valid,
     *                   or if something gone wrong in getting DAOs
     */
    @Before
    public void setUp() throws Exception {
        this.startConnection();
        //Initialize Dao
        this.genericDao = this.getDaoForTest(this.dbConnection);

        this.doOtherSetUp(this.dbConnection);
    }

    /**
     * Given a connection it returns the DAO of the class considered
     *
     * @param connectionForTest required to get the DAO with factory method
     * @return the dao required for the specific class
     */
    protected abstract GenericDao getDaoForTest(DBConnection connectionForTest) throws Exception;

    /**
     * Set other possible setting required in setUp method for the class
     * (like getting other dao and other objects required for test)
     *
     * @param connectionForTest required to get the DAOs with factory method
     */
    protected abstract void doOtherSetUp(DBConnection connectionForTest) throws Exception;

    /**
     * Test inserting and deleting functionality together.
     */
    @Test
    public void isInsertingAndDeletingCivilProtection() throws Exception {
        this.genericDao.insert(this.getTestObject());
        assertNotNull(this.genericDao.selectByIdentifier(this.getTestObject()));
        this.genericDao.delete(this.getTestObject());
        assertNull(this.genericDao.selectByIdentifier(this.getTestObject()));
    }

    /**
     * Return the specific mock object prepared for test in this class
     *
     * @return the mock object created for test
     */
    protected abstract ObjectModel getTestObject();

    /**
     * Close all connection active and eventually do other actions
     */
    @After
    public void tearDown() throws Exception {
        this.doOtherTearDown();
        this.closeConnection();
    }

    /*
     * Do other possible actions required in tearDown method for the class
     * (like deleting mock object used for test)
     */
    protected abstract void doOtherTearDown();
}
