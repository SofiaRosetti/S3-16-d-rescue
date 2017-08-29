package it.unibo.drescue.message;

import com.google.gson.Gson;

public interface JSONMessage {
    /**
     *
     * @return
     */
    String toJSON();

    static JSONMessage fromJSON(String msg) {
        Gson gson = new Gson();
        return gson.fromJson(msg, JSONMessage.class);
    }

    /**
     *
     * @return
     */
    String getFrom();

    /**
     *
     * @param from
     */
    void setFrom(String from);

    /**
     *
     * @return
     */
    String getTo();

    /**
     *
     * @param to
     */
    void setTo(String to);

    /**
     *
     * @return
     */
    String getContent();

    /**
     *
     * @param content
     */
    void setContent(String content);

    /**
     *
     * @return
     */
    String getMessageType();

    /**
     *
     * @param messageType
     */
    void setMessageType(String messageType);
}
