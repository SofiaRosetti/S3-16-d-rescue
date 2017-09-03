package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.AbstractMessage;

/**
 * Class that represents an error message.
 */
public class ErrorMessageImpl extends AbstractMessage implements ErrorMessage {

    public final static String ERROR_MESSAGE = "error_message";

    private final String error;

    /**
     * Creates an error message with the given parameter.
     *
     * @param error message of error
     */
    public ErrorMessageImpl(final String error) {
        super(ERROR_MESSAGE);
        this.error = error;
    }

    @Override
    public String getError() {
        return this.error;
    }

}
