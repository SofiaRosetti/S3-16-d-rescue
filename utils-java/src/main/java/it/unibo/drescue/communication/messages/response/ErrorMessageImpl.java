package it.unibo.drescue.communication.messages.response;

/**
 * Class that represents an error response.
 */
public class ErrorMessageImpl implements ErrorMessage {

    private final String error;

    /**
     * Creates an error message with the given parameters.
     *
     * @param error
     */
    public ErrorMessageImpl(final String error) {
        this.error = error;
    }

    @Override
    public String getError() {
        return this.error;
    }
}
