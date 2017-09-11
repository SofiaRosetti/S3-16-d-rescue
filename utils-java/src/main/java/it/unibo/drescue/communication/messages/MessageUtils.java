package it.unibo.drescue.communication.messages;

/**
 * Utility class for messages.
 */
public class MessageUtils {

    /**
     * Gets an object MessageType of the given message name.
     *
     * @param messageType message name
     * @return the MessageType, otherwise the MessageType of no message if it does not match .
     */
    public static MessageType getMessageNameByType(final String messageType) {
        for (final MessageType name : MessageType.values()) {
            if (messageType.equals(name.name())) {
                return name;
            }
        }
        return MessageType.UNKNOWN_MESSAGE;
    }

}
