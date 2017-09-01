package it.unibo.drescue.connection;

import com.rabbitmq.client.Connection;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.requests.SignUpRequestImpl;
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl;
import it.unibo.drescue.communication.messages.response.SuccessfulMessageImpl;

public class AuthenticationRPC extends AbstractRPCReceiver {

    /**
     * Creates and waits for a message on the specific connection and channel name.
     *
     * @param connection         the connection on which open the channel
     * @param receiveChannelName the channel name on which waiting for messages.
     */
    public AuthenticationRPC(final Connection connection, final String receiveChannelName) {
        super(connection, receiveChannelName);
    }

    @Override
    public String accessDB(final String jsonMessage) {
        final String response;

        if (jsonMessage == null) {
            response = GsonUtils.toGson(new ErrorMessageImpl("Received null string."));
        } else {
            final SignUpRequestImpl request = GsonUtils.fromGson(jsonMessage, SignUpRequestImpl.class);
            if (request != null) {
                final String name = request.getName();
                final String surname = request.getSurname();
                final String email = request.getEmail();
                final String password = request.getPassword();
                final String phoneNumber = request.getPhoneNumber();

                //TODO access to DB

                System.out.println("[SignUpRPC] name=" + name + " surname=" + surname +
                        " email=" + email + " password=" + password + " phoneNumber=" + phoneNumber);

                response = GsonUtils.toGson(new SuccessfulMessageImpl());
            } else {
                response = GsonUtils.toGson(new ErrorMessageImpl("Received wrong message."));
            }
        }

        System.out.println("[SignUpRPC] response " + response);

        return response;
    }


}
