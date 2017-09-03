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
     * TODO
     *
     * @param queryType
     * @return
     */
    public abstract String getQuery(QueryType queryType);

    /**
     * TODO
     *
     * @param objectModel
     * @param stmt
     * @return
     */
    public abstract PreparedStatement compileInsertStatement(
            ObjectModel objectModel, PreparedStatement stmt);

    /**
     * TODO
     *
     * @param objectModel
     * @param stmt
     * @return
     */
    protected abstract PreparedStatement compileDeleteStatement(
            ObjectModel objectModel, PreparedStatement stmt);

    /**
     * Insert a given object model into DB
     *
     * @param objectModel to insert
     * @return true if the given object is added into DB
     * false if the given object already exists in the DB
     */
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
            preparedStatement = this.compileInsertStatement(objectModel, preparedStatement);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("[DB]: INSERT_OK: Added object");
        } catch (final SQLException e) {
            //TODO handle
            e.printStackTrace();
        }

    }

    /**
     * Delete a given object model in DB
     *
     * @param objectModel to delete
     * @return true if the given object is deleted from DB
     * false if the given object NOT already exists in the DB
     */
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
            preparedStatement = this.compileDeleteStatement(objectModel, preparedStatement);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("[DB]: DELETE OK: Deleted an object");
        } catch (final SQLException e) {
            //TODO handle
            e.printStackTrace();
        }

    }

    /**
     * TODO
     */
    protected enum QueryType {
        INSERT,
        DELETE,
        FIND_ONE,
        FIND_ALL
    }


}
