package it.unibo.drescue.connection.client;

import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.requests.SignUpRequestBuilder;
import it.unibo.drescue.communication.messages.requests.SignUpRequestBuilderImpl;
import it.unibo.drescue.communication.messages.requests.SignUpRequestImpl;
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

        final SignUpRequestBuilder signUpMessageBuilder = new SignUpRequestBuilderImpl()
                .setName("testName")
                .setSurname("testSurname")
                .setEmail("test.email@test.com")
                .setPassword("testPassword")
                .setPhoneNumber("3214567890");
        final SignUpRequestImpl signUpMessage = signUpMessageBuilder.build();

        final String response = requestRPC.doRequest(GsonUtils.toGson(signUpMessage));

        System.out.println("[ClientMain] Response: " + response);

        final ErrorMessageImpl errorMessage = GsonUtils.fromGson(response, ErrorMessageImpl.class);
        if (errorMessage.getError() != null && !errorMessage.getError().isEmpty()) {
            System.out.println("[ClientMain] Error response: " + errorMessage.getError());
        } else {
            final SuccessfulMessageImpl successfulMessage = GsonUtils.fromGson(response, SuccessfulMessageImpl.class);
            if (successfulMessage != null) {
                System.out.println("[ClientMain] Successful response");
            }
        }
        connection.closeConnection();
    }

}
