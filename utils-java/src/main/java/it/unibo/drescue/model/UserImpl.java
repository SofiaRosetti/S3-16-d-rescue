package it.unibo.drescue.model;

/**
 * User implementation
 */
public class UserImpl implements User {

    private int userID;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;

    public UserImpl() {
    }

    @Override
    public int getUserID() {
        return this.userID;
    }

    @Override
    public void setUserID(final int userID) {
        this.userID = userID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public void setSurname(final String surname) {
        this.surname = surname;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
