package it.unibo.drescue.communication.messages;

import it.unibo.drescue.StringUtils;

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

    /**
     * Gets the object MessageType of the give json message.
     *
     * @param jsonMessage json message
     * @return the MessageType
     */
    public static MessageType getMessageNameByJson(final String jsonMessage) {
        final String messageType = StringUtils.getMessageType(jsonMessage);
        return getMessageNameByType(messageType);
    }

}
