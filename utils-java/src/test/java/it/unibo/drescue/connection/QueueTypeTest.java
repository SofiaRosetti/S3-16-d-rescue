package it.unibo.drescue.connection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueueTypeTest {

    private static final String QUEUE = "authentication_channel";

    @Test
    public void checkCorrectQueue() {
        assertEquals(QUEUE, QueueType.AUTHENTICATION_QUEUE.getQueueName());
    }
}
