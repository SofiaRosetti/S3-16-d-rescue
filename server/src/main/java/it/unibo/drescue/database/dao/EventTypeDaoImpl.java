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
                /**
                 * Note: The identifier in EventType is 'eventName'
                 */
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
    public PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement, final QueryType queryType) {
        final EventType eventType = ((EventType) objectModel);
        try {
            switch (queryType) {
                case INSERT:
                    statement.setString(1, eventType.getEventName());
                    break;
                case DELETE:
                    final EventType eventToDel = (EventType) this.selectByIdentifier(eventType);
                    statement.setInt(1, eventToDel.getEventID());
                    break;
                case FIND_ONE:
                    statement.setString(1, eventType.getEventName());
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
    protected ObjectModel getOneObjFromSelect(final ResultSet resultSet) {
        EventType eventType = null;
        try {
            eventType = new EventTypeImpl(
                    resultSet.getInt("eventID"),
                    resultSet.getString("eventName"));
        } catch (final SQLException e) {
            e.printStackTrace();
            //TODO handle
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

}
