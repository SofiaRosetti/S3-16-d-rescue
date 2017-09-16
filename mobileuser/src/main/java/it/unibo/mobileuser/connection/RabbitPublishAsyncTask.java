package it.unibo.mobileuser.connection;

import android.os.AsyncTask;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.connection.RabbitMQConnectionImpl;
import it.unibo.drescue.connection.RabbitMQImpl;

import java.io.IOException;

/**
 * Class used to publish a message in async way to server.
 */
public class RabbitPublishAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private final String destinationQueue;
    private final Message message;
    private final PublisherDelegate delegate;

    /**
     * Creates a new async task which perform the publish.
     *
     * @param destinationQueue queue on which to send the message
     * @param message          message to send
     * @param delegate         delegate that handles the response
     */
    public RabbitPublishAsyncTask(final String destinationQueue, final Message message, final PublisherDelegate delegate) {
        this.destinationQueue = destinationQueue;
        this.message = message;
        this.delegate = delegate;
    }

    @Override
    protected Boolean doInBackground(final Void... voids) {
        boolean successfulPublish;

        final RabbitMQConnectionImpl connection = new RabbitMQConnectionImpl("10.0.2.2");
        connection.openConnection();

        final RabbitMQImpl rabbitMQ;
        try {
            rabbitMQ = new RabbitMQImpl(connection);
            rabbitMQ.sendMessage("", this.destinationQueue, null, this.message);
            successfulPublish = true;
        } catch (final IOException e) {
            successfulPublish = false;
        } finally {
            if (connection.getConnection() != null) {
                connection.closeConnection();
            }
        }
        return successfulPublish;
    }

    @Override
    protected void onPostExecute(final Boolean successfulPublish) {
        super.onPostExecute(successfulPublish);
        if (this.delegate != null) {
            this.delegate.onPublishingMessage(successfulPublish);
        }
    }
}
