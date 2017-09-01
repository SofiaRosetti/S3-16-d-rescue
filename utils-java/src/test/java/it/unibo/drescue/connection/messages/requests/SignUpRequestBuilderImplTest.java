package it.unibo.drescue.connection.messages.requests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SignUpRequestBuilderImplTest {

    private static final String NAME = "testName";
    private static final String SURNAME = "testSurname";

    private SignUpRequest signUpRequest;

    @Before
    public void createRequest() {
        this.signUpRequest = new SignUpRequestBuilderImpl()
                .setName(NAME)
                .setSurname(SURNAME)
                .build();
    }

    @Test
    public void checkIfSetNameAndSurname() {
        assertEquals(NAME, this.signUpRequest.getName());
        assertEquals(SURNAME, this.signUpRequest.getSurname());
    }

    @Test
    public void checkNotSetFields() {
        assertEquals("", this.signUpRequest.getPhoneNumber());
        assertEquals("", this.signUpRequest.getEmail());
        assertEquals("", this.signUpRequest.getPassword());
    }

}
