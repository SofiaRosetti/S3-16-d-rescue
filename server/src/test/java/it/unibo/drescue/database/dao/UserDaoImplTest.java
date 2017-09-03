package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.DBConnectionImpl;
import it.unibo.drescue.model.User;
import it.unibo.drescue.model.UserImplBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoImplTest {

    private static final String EMAIL_TEST = "test@gmail.com";
    private static final String PASSWORD_TEST = "password";
    private static final String NAME_TEST = "name";
    private static final String SURNAME_TEST = "surname";
    private static final String PHONENUMBER_TEST = "333123123";
    private UserDao userDao = null;
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

        //Initialize userDao
        this.userDao = (UserDao) this.dbConnection.getDAO(DBConnection.Table.USER);
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


    //TODO handle exception
    @Test
    public void isRejectingDuplicateEmail() throws Exception {
        this.userDao.insert(this.userTest);
        assertNotNull(this.userDao.findByEmail(this.userTest.getEmail()));

        //assertFalse(this.userDao.insert(this.userTest));
        //TODO handle exception

        //Deleting test user
        this.userDao.delete(this.userTest);
    }

    @Test
    public void isUpdatingPassword() throws Exception {
        final String newPassword = "pass2";
        this.userDao.insert(this.userTest);
        User userInDb = this.userDao.findByEmail(this.userTest.getEmail());
        assertTrue(userInDb.getPassword().equals(this.userTest.getPassword()));
        final User userToUpdate = new UserImplBuilder()
                .setUserID(userInDb.getUserID())
                .setEmail(userInDb.getEmail())
                .setPassword(newPassword)
                .createUserImpl();
        this.userDao.update(userToUpdate);
        userInDb = this.userDao.findByEmail(this.userTest.getEmail());
        assertTrue(userInDb.getPassword().equals(newPassword));
        //Deleting user test
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