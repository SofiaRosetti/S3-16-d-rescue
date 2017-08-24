package it.unibo.mobileuser.connection;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class RequestImplTest {

    private static final String EMPTY_JSON = "{}";
    private static final String TEST_KEY = "test_key";
    private static final String TEST_VALUE = "test_value";
    private static final String FULL_JSON = "{\"test_key\":\"test_value\"}";

    private RequestImpl request;

    @Before
    public void createRequest() {
        this.request = new RequestImpl();
    }

    @Test
    public void putKeyValuePair() {

        assertTrue(this.request.getRequestData().getAsJsonObject().toString().equals(EMPTY_JSON));
        this.request.putKeyValuePair(TEST_KEY, TEST_VALUE);
        assertFalse(this.request.getRequestData().getAsJsonObject().toString().equals(EMPTY_JSON));
        assertTrue(this.request.getRequestData().getAsJsonObject().toString().equals(FULL_JSON));

    }


}
