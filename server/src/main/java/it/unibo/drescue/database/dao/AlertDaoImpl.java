package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.Alert;
import it.unibo.drescue.model.AlertImplBuilder;
import it.unibo.drescue.model.ObjectModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertDaoImpl extends UpdatableDaoAbstract<Alert> implements AlertDao {

    private static final String FIND_LAST_EXCEPTION =
            "Exception while trying to find last x alert in district";
    private static final String TABLENAME = "ALERT";

    public AlertDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    protected String getQuery(final QueryType queryType) throws SQLException {
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
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    protected PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement, final QueryType queryType) throws SQLException {
        final Alert alert = ((Alert) objectModel);
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
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
        return statement;
    }

    @Override
    protected ObjectModel mapRecordToModel(final ResultSet resultSet) throws SQLException {
        final Alert alert = new AlertImplBuilder()
                .setAlertID(resultSet.getInt("alertID"))
                .setTimestamp(resultSet.getTimestamp("timestamp"))
                .setLatitude(resultSet.getDouble("latitude"))
                .setLongitude(resultSet.getDouble("longitude"))
                .setUserID(resultSet.getInt("userID"))
                .setEventName(resultSet.getString("eventName"))
                .setDistrictID(resultSet.getString("districtID"))
                .setUpvotes(resultSet.getInt("upvotes"))
                .createAlertImpl();
        return alert;
    }

    @Override
    public List<Alert> findLast(final int x, final String districtId) throws DBQueryException {

        final List<Alert> alertList = new ArrayList<>();
        final String query = "SELECT alertID,timestamp,latitude,longitude,userID,eventName,districtID,upvotes"
                + " FROM " + TABLENAME
                + " WHERE districtID = ?"
                + " ORDER BY timestamp DESC"
                + " LIMIT ?";
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, districtId);
            statement.setInt(2, x);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final Alert alert = (Alert) mapRecordToModel(resultSet);
                alertList.add(alert);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            throw new DBQueryException(FIND_LAST_EXCEPTION);
        }
        return alertList;
    }

    @Override
    public Timestamp getCurrentTimestampForDb() {
        final Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        currentTimestamp.setNanos(0);
        return currentTimestamp;
    }
}
