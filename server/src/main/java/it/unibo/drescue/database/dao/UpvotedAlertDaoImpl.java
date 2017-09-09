package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.ObjectModel;
import it.unibo.drescue.model.UpvotedAlert;
import it.unibo.drescue.model.UpvotedAlertImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpvotedAlertDaoImpl extends GenericDaoAbstract<UpvotedAlert> implements UpvotedAlertDao {

    private static final String TABLENAME = "UPVOTED_ALERT";

    public UpvotedAlertDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    public String getQuery(final QueryType queryType) {
        switch (queryType) {
            case INSERT:
                return "INSERT INTO " + TABLENAME + "(userID,alertID)"
                        + "VALUE (?,?)";
            case DELETE:
                return "DELETE FROM " + TABLENAME
                        + " WHERE userID = ? AND alertID = ?";
            case FIND_ONE:
                /*
                * Note: The identifier in Upvoted Alert is 'userID' & 'alertID'
                */
                return "SELECT userID,alertID "
                        + "FROM " + TABLENAME + " WHERE userID = ? AND alertID = ?";
            default:
                //TODO Handle Exception
                return null;
        }
    }

    @Override
    public PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement,
                                           final QueryType queryType) {
        final UpvotedAlert upvotedAlert = ((UpvotedAlert) objectModel);
        try {
            switch (queryType) {
                case INSERT:
                case DELETE:
                case FIND_ONE:
                    statement.setInt(1, upvotedAlert.getUserID());
                    statement.setInt(2, upvotedAlert.getAlertID());
                    break;
                default:
                    //TODO Exception 'query not available for this object'
            }

        } catch (final SQLException e) {
            e.printStackTrace();
            //TODO handle exception
            return null;
        }
        return statement;
    }

    @Override
    protected ObjectModel mapRecordToModel(final ResultSet resultSet) {
        UpvotedAlert upvotedAlert = null;
        try {
            upvotedAlert = new UpvotedAlertImpl(
                    resultSet.getInt("userID"),
                    resultSet.getInt("alertID"));
        } catch (final SQLException e) {
            e.printStackTrace();
            //TODO handle
        }
        return upvotedAlert;
    }
}
