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
    protected String getQuery(final QueryType queryType) throws SQLException {
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
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    protected PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement,
                                              final QueryType queryType) throws SQLException {
        final UpvotedAlert upvotedAlert = ((UpvotedAlert) objectModel);
        switch (queryType) {
            case INSERT:
            case DELETE:
            case FIND_ONE:
                statement.setInt(1, upvotedAlert.getUserID());
                statement.setInt(2, upvotedAlert.getAlertID());
                break;
            default:
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
        return statement;
    }

    @Override
    protected ObjectModel mapRecordToModel(final ResultSet resultSet) throws SQLException {
        final UpvotedAlert upvotedAlert = new UpvotedAlertImpl(
                resultSet.getInt("userID"),
                resultSet.getInt("alertID"));
        return upvotedAlert;
    }
}
