package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBDuplicatedRecordException;
import it.unibo.drescue.database.exceptions.DBNotFoundRecordException;
import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.ObjectModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class GenericDaoAbstract<T> implements GenericDao {

    protected static final String UPDATE_EXCEPTION = "Exception while trying to update an object";
    protected static final String QUERY_NOT_FOUND_EXCEPTION = "Invalid query for its DAO";
    protected static final String FIND_ALL_EXCEPTION =
            "Exception while trying to find all objects";
    private static final String INSERT_EXCEPTION = "Exception while trying to insert an object";
    private static final String DELETE_EXCEPTION = "Exception while trying to delete an object";

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericDaoAbstract.class);

    private final String tableName;
    protected Connection connection;

    protected GenericDaoAbstract(final Connection connection, final String tableName) {
        this.tableName = tableName;
        this.connection = connection;
    }

    /**
     * Given a query type it returns the prepared query for this object
     *
     * @param queryType gives instruction on which query to be prepared
     * @return the prepared query for the object
     * @throws SQLException if the query is not available for its Dao or something gone wrong
     */
    protected abstract String getQuery(QueryType queryType) throws SQLException;

    /**
     * Useful method for all template methods
     * Given an object and a prepared statement it returns
     * that prepared statement filled with the useful information
     * to execute the specified query
     *
     * @param objectModel that contains the information of the object
     * @param statement   to fill
     * @param queryType   specify the query
     * @return the statement filled with the information of the object for the specific query
     * @throws SQLException if something gone wrong
     */
    protected abstract PreparedStatement fillStatement(
            ObjectModel objectModel, PreparedStatement statement, QueryType queryType) throws SQLException;

    @Override
    public void insert(final ObjectModel objectModel) throws DBDuplicatedRecordException, DBQueryException {
        if (this.selectByIdentifier(objectModel) != null) {
            throw new DBDuplicatedRecordException();
        }
        try {
            final String query = this.getQuery(QueryType.INSERT);
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement =
                    this.fillStatement(objectModel, preparedStatement, QueryType.INSERT);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            LOGGER.info("INSERT OK: Added object");
        } catch (final SQLException e1) {
            throw new DBQueryException(INSERT_EXCEPTION);
        }

    }

    @Override
    public void delete(final ObjectModel objectModel) throws DBNotFoundRecordException, DBQueryException {
        if (this.selectByIdentifier(objectModel) == null) {
            throw new DBNotFoundRecordException();
        }
        try {
            final String query = this.getQuery(QueryType.DELETE);
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement =
                    this.fillStatement(objectModel, preparedStatement, QueryType.DELETE);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            LOGGER.info("DELETE OK: Deleted an object");
        } catch (final SQLException e) {
            throw new DBQueryException(DELETE_EXCEPTION);
        }

    }

    @Override
    public ObjectModel selectByIdentifier(final ObjectModel objectModel) {
        ObjectModel objectModelToRet = null;
        try {
            final String query = getQuery(QueryType.FIND_ONE);
            final PreparedStatement statement = this.connection.prepareStatement(query);
            this.fillStatement(objectModel, statement, QueryType.FIND_ONE);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                objectModelToRet = this.mapRecordToModel(resultSet);
            }
            resultSet.close();
            statement.close();
            return objectModelToRet;
        } catch (final SQLException e) {
            return null;
        }
    }

    /**
     * Get an object from a resultSet given, obtained from a select query by identifier
     *
     * @param resultSet from which it takes data for the object
     * @return an object created from data obtained from the result set
     * @throws SQLException if something gone wrong
     */
    protected abstract ObjectModel mapRecordToModel(ResultSet resultSet) throws SQLException;

    @Override
    public ObjectModel insertAndGet(final ObjectModel objectModel) throws DBQueryException, DBDuplicatedRecordException, DBNotFoundRecordException {
        //Inserting the object in db
        this.insert(objectModel);
        //Return the object in DB
        return this.selectByIdentifier(objectModel);
    }

    protected enum QueryType {
        INSERT,
        DELETE,
        FIND_ONE,
        FIND_ALL,
        UPDATE
    }


}
