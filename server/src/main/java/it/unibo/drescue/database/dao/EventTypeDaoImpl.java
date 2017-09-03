package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.EventType;
import it.unibo.drescue.model.EventTypeImpl;
import it.unibo.drescue.model.ObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventTypeDaoImpl extends GenericDaoAbstract implements EventTypeDao {

    private final static String TABLENAME = "EVENT_TYPE";

    public EventTypeDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    public String getQuery(final QueryType queryType) {
        switch (queryType) {
            case INSERT:
                return "INSERT INTO " + TABLENAME + "(eventName)"
                        + "VALUE (?)";
            case DELETE:
                return "DELETE FROM " + TABLENAME
                        + " WHERE eventID = ?";
            case FIND_ONE:
                return "SELECT eventID,eventName "
                        + "FROM " + TABLENAME + " WHERE eventName = ?";
            case FIND_ALL:
                return "SELECT  eventID, eventName FROM " + TABLENAME;
            default:
                //TODO manage exception
                return null;
        }
    }

    @Override
    public EventType findByName(final String eventName) {

        EventType eventType = null;
        final String query = this.getQuery(QueryType.FIND_ONE);
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
        final String query = this.getQuery(QueryType.FIND_ALL);
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
    public ObjectModel getObject(final ObjectModel objectModel) {
        final EventType eventType = (EventType) objectModel;
        return this.findByName(eventType.getEventName());
    }

    @Override
    public PreparedStatement compileInsertStatement(final ObjectModel objectModel, final PreparedStatement statement) {
        final EventType eventType = (EventType) objectModel;
        try {
            statement.setString(1, eventType.getEventName());
        } catch (final SQLException e) {
            //TODO handle
            e.printStackTrace();
            return null;
        }
        return statement;
    }

    @Override
    protected PreparedStatement compileDeleteStatement(final ObjectModel objectModel, final PreparedStatement statement) {
        final EventType eventType = (EventType) objectModel;
        final EventType eventToDel = this.findByName(eventType.getEventName());
        try {
            statement.setInt(1, eventToDel.getEventID());
        } catch (final SQLException e) {
            //TODO handle
            e.printStackTrace();
            return null;
        }
        return statement;
    }

}
