package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.ObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class GenericDaoAbstract<T> implements GenericDao {

    protected final String tableName;
    protected Connection connection;

    protected GenericDaoAbstract(final Connection connection, final String tableName) {
        this.tableName = tableName;
        this.connection = connection;
    }

    /**
     * Search if an object like the given object is already in DB
     *
     * @param objectModel that contains some parameter for the object to search in DB
     * @return null if object is not found, otherwise the object searched
     */
    public abstract ObjectModel getObject(ObjectModel objectModel);

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
        if (this.getObject(objectModel) != null) {
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
        if (this.getObject(objectModel) == null) {
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

    protected enum QueryType {
        INSERT,
        DELETE,
        FIND_ONE,
        FIND_ALL,
        UPDATE
    }


}
