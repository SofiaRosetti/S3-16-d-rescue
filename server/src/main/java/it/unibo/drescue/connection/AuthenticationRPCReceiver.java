package it.unibo.drescue.connection;

import com.rabbitmq.client.Connection;
import it.unibo.drescue.StringUtils;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.requests.LoginMessageImpl;
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
    public AuthenticationRPCReceiver(final Connection connection, final String receiveChannelName) {
        super(connection, receiveChannelName);
    }

    @Override
    public String accessDB(final String jsonMessage) {
        String response = null;

        final String messageType = StringUtils.getMessageType(jsonMessage);

        switch (messageType) {
            case SignUpMessageImpl.SIGN_UP_MESSAGE:
                System.out.println("Received SIGN UP message");
                final SignUpMessageImpl signUpMessage = GsonUtils.fromGson(jsonMessage, SignUpMessageImpl.class);
                //TODO accessDB maybe using worker and futures
                response = GsonUtils.toGson(new SuccessfulMessageImpl());
                break;

            case LoginMessageImpl.LOGIN_MESSAGE:
                System.out.println("Received LOGIN message");
                //TODO accessDB
                //TODO modify
                response = GsonUtils.toGson(new ErrorMessageImpl("testError"));
                break;

        }

        return response;
    }

}
