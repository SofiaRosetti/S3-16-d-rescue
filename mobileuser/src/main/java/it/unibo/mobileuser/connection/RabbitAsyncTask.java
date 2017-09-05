package it.unibo.mobileuser.connection;

import android.os.AsyncTask;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl;
import it.unibo.drescue.connection.RabbitMQConnectionImpl;
import it.unibo.drescue.connection.client.RPCSenderImpl;

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

        final RabbitMQConnectionImpl connection = new RabbitMQConnectionImpl("10.0.2.2");
        connection.openConnection();

        final RPCSenderImpl requestRPC = new RPCSenderImpl(connection.getConnection(), this.destinationQueue);

        String response = requestRPC.doRequest(GsonUtils.toGson(this.message));
        System.out.println("[RabbitAsyncTask] Response " + response);

        if (response == null) {
            response = GsonUtils.toGson(new ErrorMessageImpl("Error contacting server.")); //TODO
        }

        connection.closeConnection();

        return response;
    }

    @Override
    protected void onPostExecute(final String result) {
        super.onPostExecute(result);
        if (this.delegate != null) {
            this.delegate.onReceivingResponse(result);
        }
    }


}
