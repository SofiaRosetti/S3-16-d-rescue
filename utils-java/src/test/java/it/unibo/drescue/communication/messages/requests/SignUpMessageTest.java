package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.builder.requests.SignUpMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SignUpMessageTest {

    private static final String NAME = "testName";
    private static final String SURNAME = "testSurname";

    private SignUpMessageImpl signUpMessageImpl;

    @Before
    public void build() {

        final Message signUpMessage = new SignUpMessageBuilderImpl()
                .setName(NAME)
                .setSurname(SURNAME)
                .build();

        if (signUpMessage.getMessageType().equals(SignUpMessageImpl.SIGN_UP_MESSAGE)) {
            this.signUpMessageImpl = (SignUpMessageImpl) signUpMessage;
        }

    }

    @Test
    public void checkIfSetNameAndSurname() {
        assertEquals(NAME, this.signUpMessageImpl.getName());
        assertEquals(SURNAME, this.signUpMessageImpl.getSurname());
    }

    @Test
    public void checkNotSetFields() {
        assertEquals(null, this.signUpMessageImpl.getPhoneNumber());
        assertEquals(null, this.signUpMessageImpl.getEmail());
        assertEquals(null, this.signUpMessageImpl.getPassword());
    }

}
