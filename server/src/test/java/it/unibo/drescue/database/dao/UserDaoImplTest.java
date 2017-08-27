package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.DBConnectionImpl;
import it.unibo.drescue.model.User;
import it.unibo.drescue.model.UserImplBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserDaoImplTest {

    private static final String EMAIL_TEST = "test@gmail.com";
    private static final String PASSWORD_TEST = "password";
    private static final String NAME_TEST = "name";
    private static final String SURNAME_TEST = "surname";
    private static final String PHONENUMBER_TEST = "333123123";
    private final UserDao userDao = null;
    private DBConnection dbConnection;
    private User userTest;

    @Before
    public void setUp() throws Exception {
        this.dbConnection = DBConnectionImpl.getLocalConnection();
        this.dbConnection.openConnection();
        this.userTest = new UserImplBuilder()
                .setEmail(EMAIL_TEST)
                .setPassword(PASSWORD_TEST)
                .setName(NAME_TEST)
                .setSurname(SURNAME_TEST)
                .setPhoneNumber(PHONENUMBER_TEST)
                .createUserImpl();

        //TODO instantiate userDao
    }

    @Test
    public void isUserNotAlreadyInDB() throws Exception {
        assertTrue(this.userDao.findByEmail(this.userTest.getEmail()) == null);
    }

    @Test
    public void isRegisteringAndDeletingUser() throws Exception {
        //Before registration
        this.isUserNotAlreadyInDB();
        //Registering user
        this.userDao.insert(this.userTest);
        //After registration
        assertTrue(this.userDao.findByEmail(this.userTest.getEmail()) != null);
        //Deleting user
        this.userDao.delete(this.userTest);
        //After delete
        this.isUserNotAlreadyInDB();
    }


    @Test
    public void isRejectingDuplicateEmail() throws Exception {
        assertTrue(this.userDao.insert(this.userTest));
        assertFalse(this.userDao.insert(this.userTest));
        //Deleting test user
        this.userDao.delete(this.userTest);
    }

    @Test
    public void isRejectingUnregisteredUser() throws Exception {
        assertFalse(this.userDao.login(EMAIL_TEST, PASSWORD_TEST));
    }

    @Test
    public void isLoggingInRegisteredUser() throws Exception {
        this.userDao.insert(this.userTest);
        assertTrue(this.userDao.login(EMAIL_TEST, PASSWORD_TEST));
        //Deleting test user
        this.userDao.delete(this.userTest);
    }

    @After
    public void tearDown() throws Exception {
        this.dbConnection.closeConnection();
    }
}