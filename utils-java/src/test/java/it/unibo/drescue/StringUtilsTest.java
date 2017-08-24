package it.unibo.drescue;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsTest {

    private static final String TEST_STRING = "test";

    private static final String RIGHT_EMAIL1 = "test.email@test.com";
    private static final String RIGHT_EMAIL2 = "test-email@test.com";
    private static final String RIGHT_EMAIL3 = "test.email.test@test.com";
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

}