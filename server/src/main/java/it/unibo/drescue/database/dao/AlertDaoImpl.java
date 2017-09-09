package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.Alert;
import it.unibo.drescue.model.AlertImplBuilder;
import it.unibo.drescue.model.ObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlertDaoImpl extends UpdatableDaoAbstract<Alert> implements AlertDao {

    private static final String TABLENAME = "ALERT";

    public AlertDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    public String getQuery(final QueryType queryType) {
        switch (queryType) {
            /*
             * Note: the identifier in event is timestamp & userID
             */
            case FIND_ONE:
                return "SELECT alertID,timestamp,latitude,longitude,userID,eventName,districtID,upvotes"
                        + " FROM " + TABLENAME + " WHERE timestamp = ? AND userID = ?";
            case INSERT:
                return "INSERT INTO " + TABLENAME
                        + " (timestamp,latitude,longitude,userID,eventName,districtID,upvotes)"
                        + " VALUES (?,?,?,?,?,?,?)";
            case DELETE:
                return "DELETE FROM " + TABLENAME
                        + " WHERE timestamp = ? AND userID = ?";
            case UPDATE:
                return "UPDATE " + TABLENAME + " SET upvotes = ?"
                        + " WHERE alertID = ?";
            default:
                //TODO handle exception
                return null;
        }
    }

    @Override
    public PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement, final QueryType queryType) {
        final Alert alert = ((Alert) objectModel);
        try {
            switch (queryType) {
                case INSERT:
                    statement.setTimestamp(1, alert.getTimestamp());
                    statement.setDouble(2, alert.getLatitude());
                    statement.setDouble(3, alert.getLongitude());
                    statement.setInt(4, alert.getUserID());
                    statement.setString(5, alert.getEventName());
                    statement.setString(6, alert.getDistrictID());
                    /*
                     * Note: At creation an alert has zero upvotes
                     */
                    statement.setInt(7, 0);
                    break;
                case FIND_ONE:
                    statement.setTimestamp(1, alert.getTimestamp());
                    statement.setInt(2, alert.getUserID());
                    break;
                case DELETE:
                    statement.setTimestamp(1, alert.getTimestamp());
                    statement.setInt(2, alert.getUserID());
                    break;
                case UPDATE:
                    /* NOTE: In this case it takes only the alert */
                    statement.setInt(1, alert.getUpvotes());
                    statement.setInt(2, alert.getAlertID());
                    break;
                default:
                    //TODO Exception 'query not available for this object'

            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    @Override
    protected ObjectModel mapRecordToModel(final ResultSet resultSet) {
        Alert alert = null;
        try {
            alert = new AlertImplBuilder()
                    .setAlertID(resultSet.getInt("alertID"))
                    .setTimestamp(resultSet.getTimestamp("timestamp"))
                    .setLatitude(resultSet.getDouble("latitude"))
                    .setLongitude(resultSet.getDouble("longitude"))
                    .setUserID(resultSet.getInt("userID"))
                    .setEventName(resultSet.getString("eventName"))
                    .setDistrictID(resultSet.getString("districtID"))
                    .setUpvotes(resultSet.getInt("upvotes"))
                    .createAlertImpl();
        } catch (final SQLException e) {
            e.printStackTrace();
            //TODO handle
        }
        return alert;
    }
}
