package it.unibo.mobileuser.connection;

import android.os.AsyncTask;
import android.util.Log;
import com.rabbitmq.client.AMQP;
import it.unibo.drescue.StringUtils;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl;
import it.unibo.drescue.connection.RabbitConnectionConstants;
import it.unibo.drescue.connection.RabbitMQConnectionImpl;
import it.unibo.drescue.connection.RabbitMQImpl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Class used to send an async RPC request to server.
 */
public class RabbitAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String ERROR_SERVER = "Error during server communication.";

    private final String destinationQueue;
    private final Message message;
    private final RequestDelegate delegate;

    /**
     * Creates a new async task which perform the RPC request.
     *
     * @param destinationQueue queue on which to send the request
     * @param message          message to send
     * @param delegate         delegate that handles the response
     */
    public RabbitAsyncTask(final String destinationQueue, final Message message, final RequestDelegate delegate) {
        this.destinationQueue = destinationQueue;
        this.message = message;
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(final Void... voids) {

        RabbitMQConnectionImpl connection = null;
        RabbitMQImpl rabbitMQ = null;

        String responseMessage;

        try {

            connection = new RabbitMQConnectionImpl(RabbitConnectionConstants.REMOTE_HOST);
            connection.openConnection();

            rabbitMQ = new RabbitMQImpl(connection);
            final String replyQueue = rabbitMQ.addReplyQueue();
            final AMQP.BasicProperties props = rabbitMQ.setReplyTo(replyQueue);

            rabbitMQ.sendMessage("", this.destinationQueue, props, this.message);

            final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
            responseMessage = rabbitMQ.addRPCClientConsumer(response, replyQueue);

            //if request reach timeout
            if (!StringUtils.isAValidString(responseMessage)) {
                responseMessage = GsonUtils.toGson(new ErrorMessageImpl(ERROR_SERVER));
            }

            Log.i("RabbitAsyncTask", "Response message=" + responseMessage);

        } catch (final Exception e) {
            responseMessage = GsonUtils.toGson(new ErrorMessageImpl(ERROR_SERVER));
        } finally {
            if (connection.getConnection() != null) {
                connection.closeConnection();
            }
        }

        return responseMessage;
    }

    @Override
    protected void onPostExecute(final String result) {
        super.onPostExecute(result);
        if (this.delegate != null) {
            this.delegate.onReceivingResponse(result);
        }
    }


}
