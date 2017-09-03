package it.unibo.drescue.connection;

import com.rabbitmq.client.Connection;
import it.unibo.drescue.StringUtils;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.requests.LoginMessageImpl;
import it.unibo.drescue.communication.messages.requests.SignUpMessageImpl;
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
                response = GsonUtils.toGson(new SuccessfulMessageImpl());
                break;

            case LoginMessageImpl.LOGIN_MESSAGE:
                break;

        }
        /*if (jsonMessage == null) {
            response = GsonUtils.toGson(new ErrorMessageImpl("Received null string."));
        } else {

            //TODO login

            final SignUpRequestImpl request = GsonUtils.fromGson(jsonMessage, SignUpRequestImpl.class);
            if (request != null) {
                final String name = request.getName();
                final String surname = request.getSurname();
                final String email = request.getEmail();
                final String password = request.getPassword();
                final String phoneNumber = request.getPhoneNumber();

                //TODO access to DB

                System.out.println("[AuthenticationRPC] name=" + name + " surname=" + surname +
                        " email=" + email + " password=" + password + " phoneNumber=" + phoneNumber);

                response = GsonUtils.toGson(new SuccessfulMessageImpl());
            } else {
                response = GsonUtils.toGson(new ErrorMessageImpl("Received wrong message."));
            }
        }

        System.out.println("[AuthenticationRPC] response " + response);*/

        return response;
    }

}
