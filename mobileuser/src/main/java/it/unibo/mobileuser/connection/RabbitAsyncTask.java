package it.unibo.mobileuser.connection;

import android.os.AsyncTask;
import com.rabbitmq.client.AMQP;
import it.unibo.drescue.StringUtils;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl;
import it.unibo.drescue.connection.RabbitMQConnectionImpl;
import it.unibo.drescue.connection.RabbitMQImpl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Class used to send an async request to server.
 */
public class RabbitAsyncTask extends AsyncTask<Void, Void, String> {

    private final String destinationQueue;
    private final Message message;
    private final RequestDelegate delegate;

    /**
     * Creates a new async task which perform the request.
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

            connection = new RabbitMQConnectionImpl("10.0.2.2");
            connection.openConnection();

            rabbitMQ = new RabbitMQImpl(connection);
            final String replyQueue = rabbitMQ.addReplyQueue();
            final AMQP.BasicProperties props = rabbitMQ.setReplyTo(replyQueue);

            rabbitMQ.sendMessage("", this.destinationQueue, props, this.message);

            final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
            responseMessage = rabbitMQ.addRPCClientConsumer(response, replyQueue);

            //if request reach timeout
            if (!StringUtils.isAValidString(responseMessage)) {
                responseMessage = GsonUtils.toGson(new ErrorMessageImpl("Error during server communication."));
            }

            System.out.println("responseMessage " + responseMessage);

        } catch (final Exception e) {
            responseMessage = GsonUtils.toGson(new ErrorMessageImpl("Error during server communication."));
            System.out.println("responseMessage inside exception" + responseMessage);
        } finally {
            if (connection.getConnection() != null) {
                connection.closeConnection();
            }
        }

        /*final RabbitMQConnectionImpl connection = new RabbitMQConnectionImpl("10.0.2.2");
        connection.openConnection();

        final RPCSenderImpl requestRPC = new RPCSenderImpl(connection.getConnection(), this.destinationQueue);

        String response = requestRPC.doRequest(GsonUtils.toGson(this.message));
        System.out.println("[RabbitAsyncTask] Response " + response);

        if (response == null) {
            response = GsonUtils.toGson(new ErrorMessageImpl("Error contacting server.")); //TODO
        }

        connection.closeConnection();*/

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
