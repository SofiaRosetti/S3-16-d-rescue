package it.unibo.drescue.database.dao;

import java.sql.Connection;

public abstract class GenericDao<T> {

    protected final String tableName;
    protected Connection connection;

    protected GenericDao(final Connection connection, final String tableName) {
        this.tableName = tableName;
        this.connection = connection;
    }

}
