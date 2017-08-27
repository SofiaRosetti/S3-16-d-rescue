package it.unibo.drescue.model;

/**
 * User implementation
 */
public class UserImpl implements User {

    private final int userID;
    private final String name;
    private final String surname;
    private final String email;
    private final String password;
    private final String phoneNumber;

    public UserImpl(final int userID, final String name, final String surname, final String email, final String password, final String phoneNumber) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int getUserID() {
        return this.userID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
