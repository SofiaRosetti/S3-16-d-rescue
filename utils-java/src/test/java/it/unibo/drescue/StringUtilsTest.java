package it.unibo.drescue;

import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.communication.messages.response.SuccessfulMessageImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilsTest {

    private static final String PASSWORD_CORRECT = "testT3st";
    private static final String PASSWORD_INCORRECT_LENGHT = "test";
    private static final String PASSWORD_INCORRECT_CHARACTER = "t&st";
    private static final String RIGHT_EMAIL1 = "test.email@test.com";
    private static final String RIGHT_EMAIL2 = "test-email@test.com";
    private static final String RIGHT_EMAIL3 = "test.email.test@test.com";
    private static final String TEST_STRING = "test";
    private static final String WRONG_EMAIL = "test.email@test.1";

    @Test
    public void isAValidString() {

        assertTrue(StringUtils.isAValidString(TEST_STRING));
        assertFalse(StringUtils.isAValidString(null));
        assertFalse(StringUtils.isAValidString(""));

    }

    @Test
    public void isAValidEmail() {

        assertTrue(StringUtils.isAValidEmail(RIGHT_EMAIL1));
        assertTrue(StringUtils.isAValidEmail(RIGHT_EMAIL2));
        assertTrue(StringUtils.isAValidEmail(RIGHT_EMAIL3));
        assertFalse(StringUtils.isAValidEmail(WRONG_EMAIL));

    }

    @Test
    public void isAValidPassword() {
        assertTrue(StringUtils.isAValidPassword(PASSWORD_CORRECT));
    }

    @Test
    public void isTooShortPassword() {
        assertFalse(StringUtils.isAValidPassword(PASSWORD_INCORRECT_LENGHT));
    }

    @Test
    public void isIncorrectCharacterPassword() {
        assertFalse(StringUtils.isAValidPassword(PASSWORD_INCORRECT_CHARACTER));
    }

    @Test
    public void isMessageTypeCorrect() {
        final SuccessfulMessageImpl message = new SuccessfulMessageImpl();
        assertEquals(message.getMessageType(), MessageType.SUCCESSFUL_MESSAGE.getMessageType());
    }

}
