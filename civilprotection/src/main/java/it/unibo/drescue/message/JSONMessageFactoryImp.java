package it.unibo.drescue.message;

public class JSONMessageFactoryImp implements  JSONMessageFactory{

    @Override
    public JSONMessage createUpdateRescueTeamConditionMessage() {
        return new UpdateRescueTeamConditionMessage();
    }


}
