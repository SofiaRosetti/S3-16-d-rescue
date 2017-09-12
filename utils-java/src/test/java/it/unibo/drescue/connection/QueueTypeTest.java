package it.unibo.drescue.connection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueueTypeTest {

    private static final String QUEUE = "mobileuser_channel";

    @Test
    public void checkCorrectQueue() {
        assertEquals(QUEUE, QueueType.MOBILEUSER_QUEUE.getQueueName());
    }
}
