package it.unibo.drescue.model;

public class UserImplBuilder {

    private int ID;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;

    public UserImplBuilder setID(final int ID) {
        this.ID = ID;
        return this;
    }

    public UserImplBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    public UserImplBuilder setSurname(final String surname) {
        this.surname = surname;
        return this;
    }

    public UserImplBuilder setEmail(final String email) {
        this.email = email;
        return this;
    }

    public UserImplBuilder setPassword(final String password) {
        this.password = password;
        return this;
    }

    public UserImplBuilder setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserImpl createUserImpl() {
        return new UserImpl(this.ID, this.name, this.surname, this.email, this.password, this.phoneNumber);
    }
}
