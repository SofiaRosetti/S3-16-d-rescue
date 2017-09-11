package it.unibo.drescue.connection;

import com.rabbitmq.client.Connection;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.communication.messages.MessageUtils;
import it.unibo.drescue.communication.messages.requests.SignUpMessageImpl;
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl;
import it.unibo.drescue.communication.messages.response.SuccessfulMessageImpl;

/**
 * Class that handles login and sign up RPCs.
 */
public class AuthenticationRPCReceiver extends AbstractRPCReceiver {

    /**
     * Creates and waits for a request on the specific connection and channel name.
     *
     * @param connection         the connection on which open the channel
     * @param receiveChannelName the channel name on which waiting for requests
     */
    public AuthenticationRPCReceiver(final Connection connection, final QueueType receiveChannelName) {
        super(connection, receiveChannelName);
    }

    @Override
    public String accessDB(final String jsonMessage) {
        String response = null;
        final MessageType nameMessage = MessageUtils.getMessageNameByJson(jsonMessage);
        switch (nameMessage) {
            case SIGN_UP_MESSAGE:
                System.out.println("Received SIGN UP message");
                final SignUpMessageImpl signUpMessage = GsonUtils.fromGson(jsonMessage, SignUpMessageImpl.class);
                //TODO accessDB maybe using worker and futures
                response = GsonUtils.toGson(new SuccessfulMessageImpl());
                break;

            case LOGIN_MESSAGE:
                System.out.println("Received LOGIN message");
                //TODO accessDB
                //TODO modify
                response = GsonUtils.toGson(new ErrorMessageImpl("testError"));
                break;

        }

        return response;
    }

}
