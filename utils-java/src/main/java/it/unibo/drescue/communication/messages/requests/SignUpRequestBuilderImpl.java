package it.unibo.drescue.communication.messages.requests;

/**
 * Builder for SignUpRequestImpl
 */
public class SignUpRequestBuilderImpl implements SignUpRequestBuilder {

    private String name = "";
    private String surname = "";
    private String email = "";
    private String password = "";
    private String phoneNumber = "";

    @Override
    public SignUpRequestBuilderImpl setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public SignUpRequestBuilderImpl setSurname(final String surname) {
        this.surname = surname;
        return this;
    }

    @Override
    public SignUpRequestBuilderImpl setEmail(final String email) {
        this.email = email;
        return this;
    }

    @Override
    public SignUpRequestBuilderImpl setPassword(final String password) {
        this.password = password;
        return this;
    }

    @Override
    public SignUpRequestBuilderImpl setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public SignUpRequestImpl build() {
        return new SignUpRequestImpl(this.name, this.surname, this.email, this.password, this.phoneNumber);
    }
}
