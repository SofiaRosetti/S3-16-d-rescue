package it.unibo.drescue.connection;

/**
 * Enum of types of queue which are useful for connection.
 */
public enum QueueType {
    ALERTS_QUEUE("alerts_channel"), //new alert, get alerts
    MOBILEUSER_QUEUE("mobileuser_channel"), // app authentication, user profile
    CIVIL_PROTECTION_QUEUE("civil_protection_channel"); // cp authentication, request rescue team

    private final String queueName;

    /**
     * Creates a queue well formed.
     *
     * @param queueName the queue name
     */
    QueueType(final String queueName) {
        this.queueName = queueName;
    }

    /**
     * @return the queue name
     */
    public String getQueueName() {
        return this.queueName;
    }
}
