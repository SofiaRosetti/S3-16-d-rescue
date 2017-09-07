package it.unibo.drescue.model;

public class UserImplBuilder {

    /*private int userID;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;*/
    private final UserImpl user;

    public UserImplBuilder() {
        this.user = new UserImpl();
    }

    public UserImplBuilder setUserID(final int userID) {
        this.user.setUserID(userID);
        return this;
    }

    public UserImplBuilder setName(final String name) {
        this.user.setName(name);
        return this;
    }

    public UserImplBuilder setSurname(final String surname) {
        this.user.setSurname(surname);
        return this;
    }

    public UserImplBuilder setEmail(final String email) {
        this.user.setEmail(email);
        return this;
    }

    public UserImplBuilder setPassword(final String password) {
        this.user.setPassword(password);
        return this;
    }

    public UserImplBuilder setPhoneNumber(final String phoneNumber) {
        this.user.setPhoneNumber(phoneNumber);
        return this;
    }

    public UserImpl createUserImpl() {
        return this.user;
    }
}
