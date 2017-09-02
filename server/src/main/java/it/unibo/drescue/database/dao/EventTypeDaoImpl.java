package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.EventType;
import it.unibo.drescue.model.EventTypeImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventTypeDaoImpl extends GenericDao implements EventTypeDao {

    private final static String TABLENAME = "EVENT_TYPE";

    public EventTypeDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    public EventType findByName(final String eventName) {

        EventType eventType = null;
        final String query = "SELECT eventID,eventName "
                + "FROM " + TABLENAME + " WHERE eventName = ?";
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, eventName);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                eventType = new EventTypeImpl(
                        resultSet.getInt("eventID"),
                        resultSet.getString("eventName"));
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return eventType;
    }

    @Override
    public List<EventType> findAll() {
        final List<EventType> eventTypeList = new ArrayList<>();
        final String query = "SELECT  eventID, eventName FROM " + TABLENAME;
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final EventTypeImpl eventType = new EventTypeImpl(
                        resultSet.getInt("eventID"),
                        resultSet.getString("eventName"));
                eventTypeList.add(eventType);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return eventTypeList;
    }

    @Override
    public boolean insert(final EventType eventType) {

        //Verify if eventID already exists
        if (this.findByName(eventType.getEventName()) != null) {
            System.out.println("[DB]: INSERT_EVENT_TYPE_FAIL: "
                    + eventType.getEventName() + " already in db");
            return false;
        }

        final String query = "INSERT INTO " + TABLENAME + "(eventName)"
                + "VALUE (?)";
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, eventType.getEventName());
            statement.executeUpdate();
            statement.close();
            System.out.println("[DB]: INSERT_EVENT_TYPE_OK: Added event " + eventType.getEventName());
            return true;
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(final EventType eventType) {

        final EventType eventToDelete = this.findByName(eventType.getEventName());

        final String query = "DELETE FROM " + TABLENAME
                + " WHERE eventID = ?";
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, eventToDelete.getEventID());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
