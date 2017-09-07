package it.unibo.drescue.communication.messages;

/**
 * Utility class for messages.
 */
public class MessageUtils {

    /**
     * Gets an object MessageType of the given message value.
     *
     * @param messageType message value
     * @return the message name, otherwise the message name of no message if it does not match .
     */
    public static MessageType getMessageNameByType(final String messageType) {
        for (final MessageType name : MessageType.values()) {
            if (messageType.equals(name.getMessageType())) {
                return name;
            }
        }
        return MessageType.UNKNOWN_MESSAGE;
    }

}
