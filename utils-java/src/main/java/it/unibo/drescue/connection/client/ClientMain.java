package it.unibo.drescue.connection.client;

import com.rabbitmq.client.AMQP;
import it.unibo.drescue.StringUtils;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.builder.requests.SignUpMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.communication.messages.MessageUtils;
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl;
import it.unibo.drescue.communication.messages.response.SuccessfulMessageImpl;
import it.unibo.drescue.connection.QueueType;
import it.unibo.drescue.connection.RabbitMQConnectionImpl;
import it.unibo.drescue.connection.RabbitMQImpl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ClientMain {

    public static void main(final String[] args) {

        System.out.println("[ClientMain] Client started");

        //TODO AsyncTask Android App

        RabbitMQConnectionImpl connection = null;
        RabbitMQImpl rabbitMQ = null;

        String responseMessage;

        try {

            connection = new RabbitMQConnectionImpl("localhost");
            connection.openConnection();

            rabbitMQ = new RabbitMQImpl(connection); //create channel
            final String replyQueue = rabbitMQ.addReplyQueue(); //queue for response
            final AMQP.BasicProperties props = rabbitMQ.setReplyTo(replyQueue);
            //set in properties queue for response

            final Message signUpMessageBuilder = new SignUpMessageBuilderImpl()
                    .setName("testName")
                    .setSurname("testSurname")
                    .setEmail("test.email@test.com")
                    .setPassword("testPassword")
                    .setPhoneNumber("3214567890")
                    .build();

            rabbitMQ.sendMessage("", QueueType.AUTHENTICATION_QUEUE.getQueueName(), props, signUpMessageBuilder);

            final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
            responseMessage = rabbitMQ.addRPCClientConsumer(response, replyQueue);

            if (!StringUtils.isAValidString(responseMessage)) { //TODO is not valid if request reach timeout
                responseMessage = GsonUtils.toGson(new ErrorMessageImpl("Error during server communication."));
            }

            System.out.println("responseMessage " + responseMessage);

        } catch (final Exception e) {
            responseMessage = GsonUtils.toGson(new ErrorMessageImpl("Error during server communication."));
        } finally {
            if (connection.getConnection() != null) { //se c'è stata un eccezione e la connessione è ancora aperta la chiudo
                connection.closeConnection();
            }
        }

        //handle reply to AbstractResponse
        final String messageType = StringUtils.getMessageType(responseMessage);
        final MessageType nameMessage = MessageUtils.getMessageNameByType(messageType);
        switch (nameMessage) {
            case ERROR_MESSAGE:
                final ErrorMessageImpl errorMessage = GsonUtils.fromGson(responseMessage, ErrorMessageImpl.class);
                System.out.println("[ClientMain] error message " + errorMessage.getError());
                break;
            case SUCCESSFUL_MESSAGE:
                final SuccessfulMessageImpl successfulMessage = GsonUtils.fromGson(responseMessage, SuccessfulMessageImpl.class);
                System.out.println("[ClientMain] successful message");
                break;
        }

    }

}
