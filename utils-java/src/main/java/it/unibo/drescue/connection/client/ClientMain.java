package it.unibo.drescue.connection.client;

import it.unibo.drescue.StringUtils;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.builder.requests.SignUpMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl;
import it.unibo.drescue.communication.messages.response.SuccessfulMessageImpl;
import it.unibo.drescue.connection.RabbitMQConnectionImpl;
import it.unibo.drescue.connection.ServerUtils;

public class ClientMain {

    public static void main(final String[] args) {

        System.out.println("[ClientMain] Client started");

        //TODO AsyncTask Android App

        final RabbitMQConnectionImpl connection = new RabbitMQConnectionImpl("localhost");
        connection.openConnection();

        final RPCSenderImpl requestRPC = new RPCSenderImpl(connection.getConnection(),
                ServerUtils.AUTHENTICATION_CHANNEL_RPC);

        final Message signUpMessageBuilder = new SignUpMessageBuilderImpl()
                .setName("testName")
                .setSurname("testSurname")
                .setEmail("test.email@test.com")
                .setPassword("testPassword")
                .setPhoneNumber("3214567890")
                .build();

        final String response = requestRPC.doRequest(GsonUtils.toGson(signUpMessageBuilder));

        System.out.println("[ClientMain] Response: " + response);

        final String messageType = StringUtils.getMessageType(response);
        switch (messageType) {
            case ErrorMessageImpl.ERROR_MESSAGE:
                final ErrorMessageImpl errorMessage = GsonUtils.fromGson(response, ErrorMessageImpl.class);
                System.out.println("[ClientMain] error message " + errorMessage.getError());
            case SuccessfulMessageImpl.SUCCESSFUL_MESSAGE:
                final SuccessfulMessageImpl successfulMessage = GsonUtils.fromGson(response, SuccessfulMessageImpl.class);
                System.out.println("[ClientMain] successful message");
        }

        connection.closeConnection();

    }

}
