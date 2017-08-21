package it.unibo.drescue.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DBConnectionImplTest {

    private static final String EMAIL_TEST = "test@gmail.com";
    private static final String PASSWORD_TEST = "password";
    private static final String NAME_TEST = "name";
    private static final String SURNAME_TEST = "surname";
    private static final String PHONENUMBER_TEST = "333123123";
    private DBConnectionImpl dbConnection;

    @Before
    public void setUp() throws Exception {
        this.dbConnection = new DBConnectionImpl();
        this.dbConnection.openConnection();
    }

    @Test
    public void isRegisteringAndDeletingUser() throws Exception {
        this.dbConnection.registerUser(EMAIL_TEST, PASSWORD_TEST,
                NAME_TEST, SURNAME_TEST, PHONENUMBER_TEST);
        assertTrue(this.dbConnection.unregisterUser(this.dbConnection.getUserId(EMAIL_TEST)));
    }

    @Test
    public void isRejectingDuplicateEmail() throws Exception {
        assertTrue(this.dbConnection.registerUser(EMAIL_TEST, PASSWORD_TEST,
                NAME_TEST, SURNAME_TEST, PHONENUMBER_TEST));
        assertFalse(this.dbConnection.registerUser(EMAIL_TEST, PASSWORD_TEST,
                NAME_TEST, SURNAME_TEST, PHONENUMBER_TEST));
        //Deleting test user
        this.dbConnection.unregisterUser(this.dbConnection.getUserId(EMAIL_TEST));
    }

    @After
    public void tearDown() throws Exception {
        this.dbConnection.closeConnection();
    }
}