package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.ObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class UpdatableDaoAbstract<T> extends GenericDaoAbstract implements UpdatableDao {

    protected UpdatableDaoAbstract(final Connection connection, final String tableName) {
        super(connection, tableName);
    }

    @Override
    public void update(final ObjectModel objectModel) {

        final String query = this.getQuery(QueryType.UPDATE);
        try {
            final PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            this.fillUpdateStatement(objectModel, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            e.printStackTrace();
            //TODO Exception
        }
    }

    /**
     * TODO
     *
     * @param objectModel
     * @param statement
     * @return
     */
    protected abstract PreparedStatement fillUpdateStatement(ObjectModel objectModel,
                                                             PreparedStatement statement);
}
