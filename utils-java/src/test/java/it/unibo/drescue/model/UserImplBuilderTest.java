package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UserImplBuilderTest {

    private static final int USER_ID = 1931;
    private static final String NAME = "John";
    private static final String SURNAME = "White";
    private static final String EMAIL = "j.white@gmail.com";
    private static final String PASSWORD = "jwhite19";
    private static final String PHONENUMBER = "3459287116";
    private User user;

    @Before
    public void createUser() throws Exception {
        this.user = new UserImplBuilder()
                .setUserID(this.USER_ID)
                .setName(this.NAME)
                .setSurname(this.SURNAME)
                .setEmail(this.EMAIL)
                .setPassword(this.PASSWORD)
                .setPhoneNumber(this.PHONENUMBER)
                .createUserImpl();
    }

    @Test
    public void checkCorrectID() throws Exception {
        final int userID = this.user.getUserID();
        assertTrue(userID == this.USER_ID);
    }

    @Test
    public void checkCorrectNameAndSurname() throws Exception {
        final String name = this.user.getName();
        final String surname = this.user.getSurname();
        assertTrue(name.equals(this.NAME) && surname.equals(this.SURNAME));
    }

    @Test
    public void checkCorrectEmailAndPwd() throws Exception {
        final String email = this.user.getEmail();
        final String password = this.user.getPassword();
        assertTrue(email.equals(this.EMAIL) && password.equals(this.PASSWORD));
    }

    @Test
    public void checkCorrectPhoneNumber() throws Exception {
        final String phoneNumber = this.user.getPhoneNumber();
        assertTrue(phoneNumber.equals(this.PHONENUMBER));
    }

}