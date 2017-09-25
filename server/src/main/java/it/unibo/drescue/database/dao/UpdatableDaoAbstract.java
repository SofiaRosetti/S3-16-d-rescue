package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.ObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Abstract class from which all DAO implementation that needs an update method extends,
 * it contains the template method for all objects in DB
 *
 * @param <T> specify the class of the object interested in DAO, in this case an ObjectModel
 */
public abstract class UpdatableDaoAbstract<T extends ObjectModel> extends GenericDaoAbstract implements UpdatableDao {

    protected UpdatableDaoAbstract(final Connection connection, final String tableName) {
        super(connection, tableName);
    }

    @Override
    public void update(final ObjectModel objectModel) throws DBQueryException {

        try {
            final String query = this.getQuery(QueryType.UPDATE);
            final PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            this.fillStatement(objectModel, preparedStatement, QueryType.UPDATE);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DBQueryException(UPDATE_EXCEPTION);
        }
    }

}
