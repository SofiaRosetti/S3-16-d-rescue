package it.unibo.drescue.communication.messages;

public abstract class AbstractRoutingMessage implements RoutingMessage {

    private String from;
    private String to;

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public abstract String getMessageType();
}
