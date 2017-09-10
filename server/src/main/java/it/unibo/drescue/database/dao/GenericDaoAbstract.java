package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.ObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class GenericDaoAbstract<T> implements GenericDao {

    protected final String tableName;
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
     */
    public abstract String getQuery(QueryType queryType);

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
     */
    public abstract PreparedStatement fillStatement(
            ObjectModel objectModel, PreparedStatement statement, QueryType queryType);

    @Override
    public void insert(final ObjectModel objectModel) {
        if (this.selectByIdentifier(objectModel) != null) {
            //TODO Throws exception 'object already in DB'
            System.out.println("INSERT: object already in DB");
            return;
        }
        final String query = this.getQuery(QueryType.INSERT);
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement = this.fillStatement(objectModel, preparedStatement, QueryType.INSERT);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("[DB]: INSERT_OK: Added object");
        } catch (final SQLException e) {
            //TODO handle
            e.printStackTrace();
        }

    }

    @Override
    public void delete(final ObjectModel objectModel) {
        if (this.selectByIdentifier(objectModel) == null) {
            //TODO Throws exception 'object NOT already in DB'
            System.out.println("DELETE: object NOT already in DB");
            return;
        }
        final String query = this.getQuery(QueryType.DELETE);
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement = this.fillStatement(objectModel, preparedStatement, QueryType.DELETE);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("[DB]: DELETE OK: Deleted an object");
        } catch (final SQLException e) {
            //TODO handle
            e.printStackTrace();
        }

    }

    @Override
    public ObjectModel selectByIdentifier(final ObjectModel objectModel) {
        ObjectModel objectModelToRet = null;
        final String query = getQuery(QueryType.FIND_ONE);
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            this.fillStatement(objectModel, statement, QueryType.FIND_ONE);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                objectModelToRet = this.mapRecordToModel(resultSet);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return objectModelToRet;
    }

    /**
     * Get an object from a resultSet given, obtained from a select query by identifier
     *
     * @param resultSet from which it takes data for the object
     * @return an object created from data obtained from the result set
     */
    protected abstract ObjectModel mapRecordToModel(ResultSet resultSet);

    @Override
    public ObjectModel insertAndGet(final ObjectModel objectModel) {
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
