package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.model.ObjectModel;
import it.unibo.drescue.model.User;
import it.unibo.drescue.model.UserImplBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoImplTest extends GenericDaoAbstractTest {

    private static final String EMAIL_TEST = "test@gmail.com";
    private static final String PASSWORD_TEST = "password";
    private static final String NAME_TEST = "name";
    private static final String SURNAME_TEST = "surname";
    private static final String PHONENUMBER_TEST = "333123123";
    private final User userTest = new UserImplBuilder()
            .setEmail(EMAIL_TEST)
            .setPassword(PASSWORD_TEST)
            .setName(NAME_TEST)
            .setSurname(SURNAME_TEST)
            .setPhoneNumber(PHONENUMBER_TEST)
            .createUserImpl();

    private UserDao userDao = null;


    @Override
    protected GenericDao getDaoForTest(final DBConnection connectionForTest) throws Exception {
        return this.userDao = (UserDao) connectionForTest.getDAO(DBConnection.Table.USER);
    }

    @Override
    protected ObjectModel getTestObject() {
        return this.userTest;
    }

    @Override
    protected void doOtherSetUp(final DBConnection connectionForTest) {
        //DO NOTHING
    }

    @Override
    protected void doOtherTearDown() {
        //DO NOTHING
    }

    /**
     * TODO
     *
     * @throws Exception
     */
    //TODO handle exception
    @Test
    public void isRejectingDuplicateEmail() throws Exception {
        this.userDao.insert(this.userTest);
        assertNotNull(this.userDao.selectByIdentifier(this.userTest));

        //assertFalse(this.userDao.insert(this.userTest));
        //TODO handle exception

        //Deleting test user
        this.userDao.delete(this.userTest);
    }

    /**
     * TODO
     *
     * @throws Exception
     */
    @Test
    public void isUpdatingPassword() throws Exception {
        final String newPassword = "password2";
        this.userDao.insert(this.userTest);
        User userInDb = (User) this.userDao.selectByIdentifier(this.userTest);
        assertEquals(userInDb.getPassword(), this.userTest.getPassword());
        final User userToUpdate = new UserImplBuilder()
                .setUserID(userInDb.getUserID())
                .setEmail(userInDb.getEmail())
                .setPassword(newPassword)
                .createUserImpl();
        this.userDao.update(userToUpdate);
        userInDb = (User) this.userDao.selectByIdentifier(this.userTest);
        assertEquals(userInDb.getPassword(), newPassword);
        //Deleting user test
        this.userDao.delete(this.userTest);
    }

    /**
     * TODO
     *
     * @throws Exception
     */
    @Test
    public void isRejectingUnregisteredUser() throws Exception {
        assertFalse(this.userDao.login(EMAIL_TEST, PASSWORD_TEST));
    }

    /**
     * TODO
     *
     * @throws Exception
     */
    @Test
    public void isLoggingInRegisteredUser() throws Exception {
        this.userDao.insert(this.userTest);
        assertTrue(this.userDao.login(EMAIL_TEST, PASSWORD_TEST));
        //Deleting test user
        this.userDao.delete(this.userTest);
    }

}