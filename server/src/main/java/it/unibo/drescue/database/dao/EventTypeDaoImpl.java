package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.EventType;
import it.unibo.drescue.model.EventTypeImpl;
import it.unibo.drescue.model.ObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that provides the complete access to the EventType object in DB
 * implements the abstract class GenericDaoAbstract and its interface
 */
public class EventTypeDaoImpl extends GenericDaoAbstract<EventType> implements EventTypeDao {

    private final static String TABLENAME = "EVENT_TYPE";

    public EventTypeDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    protected String getQuery(final QueryType queryType) throws SQLException {
        switch (queryType) {
            case INSERT:
                return "INSERT INTO " + TABLENAME + "(eventName)"
                        + "VALUE (?)";
            case DELETE:
                return "DELETE FROM " + TABLENAME
                        + " WHERE eventName = ?";
            case FIND_ONE:
                /*
                 * Note: The identifier in EventType is 'eventName'
                 */
                return "SELECT eventName "
                        + "FROM " + TABLENAME + " WHERE eventName = ?";
            case FIND_ALL:
                return "SELECT eventName FROM " + TABLENAME;
            default:
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    protected PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement, final QueryType queryType) throws SQLException {
        final EventType eventType = (EventType) objectModel;
        switch (queryType) {
            case INSERT:
            case DELETE:
            case FIND_ONE:
                statement.setString(1, eventType.getEventName());
                break;
            default:
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
        return statement;
    }

    @Override
    protected ObjectModel mapRecordToModel(final ResultSet resultSet) throws SQLException {
        final EventType eventType = new EventTypeImpl(
                resultSet.getString("eventName"));
        return eventType;
    }

    @Override
    public List<EventType> findAll() throws DBQueryException {
        final List<EventType> eventTypeList = new ArrayList<>();
        try {
            final String query = this.getQuery(QueryType.FIND_ALL);
            final PreparedStatement statement = this.connection.prepareStatement(query);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final EventType eventType = (EventType) mapRecordToModel(resultSet);
                eventTypeList.add(eventType);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            throw new DBQueryException(FIND_ALL_EXCEPTION);
        }
        return eventTypeList;
    }

}
