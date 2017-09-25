package it.unibo.drescue.connection.client;

import it.unibo.drescue.communication.builder.requests.NewAlertMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.connection.QueueType;
import it.unibo.drescue.connection.RabbitMQConnectionImpl;
import it.unibo.drescue.connection.RabbitMQImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMain.class);

    public static void main(final String[] args) {

        LOGGER.info("Client started");

        RabbitMQConnectionImpl connection = null;
        RabbitMQImpl rabbitMQ = null;

        final String responseMessage;

        try {

            connection = new RabbitMQConnectionImpl("localhost");
            connection.openConnection();

            rabbitMQ = new RabbitMQImpl(connection); //create channel
            //final String replyQueue = rabbitMQ.addReplyQueue(); //queue for response
            //final AMQP.BasicProperties props = rabbitMQ.setReplyTo(replyQueue);
            //set in properties queue for response

            final Message alertMessage = new NewAlertMessageBuilderImpl()
                    .setUserID(1)
                    .setEventType("Earthquakes")
                    .setLatitude(44.420826)
                    .setLongitude(11.912387)
                    .build();

            /*final Message signUpMessageBuilder = new SignUpMessageBuilderImpl()
                    .setName("testName")
                    .setSurname("testSurname")
                    .setEmail("test.email@test.com")
                    .setPassword("testPassword")
                    .setPhoneNumber("3214567890")
                    .build();*/

            //rabbitMQ.sendMessage("", QueueType.MOBILEUSER_QUEUE.getQueueName(), props, signUpMessageBuilder);
            rabbitMQ.sendMessage("", QueueType.ALERTS_QUEUE.getQueueName(), null, alertMessage);

            /*final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
            responseMessage = rabbitMQ.addRPCClientConsumer(response, replyQueue);

            if (!StringUtils.isAValidString(responseMessage)) {
                responseMessage = GsonUtils.toGson(new ErrorMessageImpl("Error during server communication."));
            }

            System.out.println("responseMessage " + responseMessage);*/

        } catch (final Exception e) {
            //responseMessage = GsonUtils.toGson(new ErrorMessageImpl("Error during server communication."));
        } finally {
            if (connection.getConnection() != null) {
                connection.closeConnection();
            }
        }

        //handle reply to AbstractResponse
        /*final MessageType nameMessage = MessageUtils.getMessageNameByJson(responseMessage);

        switch (nameMessage) {
            case ERROR_MESSAGE:
                final ErrorMessageImpl errorMessage = GsonUtils.fromGson(responseMessage, ErrorMessageImpl.class);
                System.out.println("[ClientMain] error message " + errorMessage.getError());
                break;
            case SUCCESSFUL_MESSAGE:
                final SuccessfulMessageImpl successfulMessage = GsonUtils.fromGson(responseMessage, SuccessfulMessageImpl.class);
                System.out.println("[ClientMain] successful message");
                break;
        }*/

    }

}
