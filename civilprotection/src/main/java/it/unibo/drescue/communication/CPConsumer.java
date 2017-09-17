package it.unibo.drescue.communication;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import it.unibo.drescue.communication.messages.*;
import it.unibo.drescue.utils.Coordinator;
import it.unibo.drescue.utils.CoordinatorCondition;
import it.unibo.drescue.utils.CoordinatorImpl;
import it.unibo.drescue.utils.RescueTeamCondition;


import java.io.IOException;
import java.sql.Timestamp;

public class CPConsumer extends DefaultConsumer {

    private String cpID;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public CPConsumer(final Channel channel) {
        super(channel);
    }

    public void setCpID(final String cpID){
        this.cpID = cpID;
    }

    @Override
    public void handleDelivery(final String consumerTag,
                               final Envelope envelope,
                               final AMQP.BasicProperties properties,
                               final byte[] body)
            throws IOException {
        final String msg = new String(body, "UTF-8");
        //System.out.println("received message :[ " + msg + " ]");
        final MessageType nameMessage = MessageUtils.getMessageNameByJson(msg);

        Coordinator coordinator = CoordinatorImpl.getInstance();
        CoordinatorCondition myCondition;
        String myCs;
        Timestamp myTimestamp;

        switch (nameMessage){
            case REQ_COORDINATION_MESSAGE:
                final ReqCoordinationMessage reqCoordinationMessage = GsonUtils.fromGson(msg, ReqCoordinationMessage.class);

                Timestamp reqTimestamp = reqCoordinationMessage.getTimestamp();
                String reqFrom = reqCoordinationMessage.getFrom();
                String reqTo = reqCoordinationMessage.getTo();
                String reqCs = reqCoordinationMessage.getRescueTeamID();

                if (!reqFrom.equals(this.cpID)){

                    System.out.println("[REQUEST] From: " + reqFrom + " To: "+ reqTo + " Timestamp: " + reqTimestamp + " Cs: "+ reqCs );

                    myCondition = coordinator.getCondition();
                    myCs = coordinator.getCsName();
                    myTimestamp = coordinator.getReqTimestamp();

                    if ((myCondition == CoordinatorCondition.HELD && reqCs.equals(myCs)) || (myCondition == CoordinatorCondition.WANTED && reqCs.equals(myCs)
                            && reqTimestamp.after(myTimestamp))){
                        coordinator.addBlockedCP(reqFrom);

                    } else {
                        //TODO se la rescue team è occupata, ed è stata occupata da altre CP  allora settare lo stato del RT = AVAILABLE
                        coordinator.sendReplayMessageTo(reqCoordinationMessage.getRescueTeamID(), reqFrom);
                    }
                }
                break;

            case REPLAY_COORDINATION_MESSAGE:

                final ReplayCoordinationMessage replayMessage = GsonUtils.fromGson(msg, ReplayCoordinationMessage.class);

                Timestamp replayTimestamp = replayMessage.getTimestamp();
                String replayFrom = replayMessage.getFrom();
                String replayTo = replayMessage.getTo();
                String replayCs = replayMessage.getRescueTeamID();
                RescueTeamCondition replayRTCondition = replayMessage.getRTCondition();

                myCondition = coordinator.getCondition();
                myCs = coordinator.getCsName();

                if (!replayFrom.equals(this.cpID)){
                    System.out.println("[REPLAY] From: " + replayFrom + " To: "+ replayTo + " Timestamp: " + replayTimestamp + " Cs: " + replayCs + " RT Condition " + replayRTCondition);
                    if (myCondition == CoordinatorCondition.WANTED && myCs.equals(replayCs) && replayTo.equals(this.cpID)){

                        //TODO Check RescueTeam state (if state = Occupied then update coordination state)
                        if (replayRTCondition == RescueTeamCondition.AVAILABLE){
                            coordinator.updatePendingCivilProtectionReplayStructure(replayFrom);
                        }
                        else {
                            coordinator.setCondition(CoordinatorCondition.DETACHED);
                        }

                    }
                }
                break;

            case CONFIGURATION_MESSAGE:
                final CPConfigurationMessage cpConfigurationMessage = GsonUtils.fromGson(msg, CPConfigurationMessage.class);
                System.out.println("[Configuration Message] RescueTeam name: " + cpConfigurationMessage.getRescueTeamCollection().get(0).getName() + " From: " + cpConfigurationMessage.getFrom()
                         + " To: " +  cpConfigurationMessage.getTo());

                break;


        }
    }
}
