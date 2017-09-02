package it.unibo.drescue.communication.messages.requests;

/**
 * Class that represents a sign up request.
 */
public class SignUpRequestImpl implements SignUpRequest {

    private final String name;
    private final String surname;
    private final String email;
    private final String password;
    private final String phoneNumber;

    public SignUpRequestImpl(final String name, final String surname, final String email, final String password, final String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
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
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

}
