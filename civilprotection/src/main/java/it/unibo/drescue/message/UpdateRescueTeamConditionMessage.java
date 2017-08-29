package it.unibo.drescue.message;

import com.google.gson.Gson;

public class UpdateRescueTeamConditionMessage implements JSONMessage {

    private String from;
    private String to;
    private String messageType;
    private String content;

    @Override
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static JSONMessage fromJSON(String msg){
        Gson gson = new Gson();
        return gson.fromJson(msg, UpdateRescueTeamConditionMessage.class);
    };

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String toString(){
        return String.format("Message From: %s To: %s" + "Content: %s ",  from, to, content);
    }
}
