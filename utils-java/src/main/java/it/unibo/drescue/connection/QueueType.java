package it.unibo.drescue.connection;

/**
 * Enum of types of queue which are useful for connection.
 */
public enum QueueType {
    ALERTS_QUEUE_RPC("alerts_channel_RPC"),
    AUTHENTICATION_QUEUE_RPC("authentication_channel_RPC"),
    NEW_ALERT_QUEUE_PS("new_alert_channel_PS"),
    PROFILE_QUEUE_RPC("profile_channel_RPC");

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
