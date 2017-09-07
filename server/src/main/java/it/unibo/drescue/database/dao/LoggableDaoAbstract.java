package it.unibo.drescue.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*Note: LoggableDao extends from Updatable because an object with credentials should change the password*/
public abstract class LoggableDaoAbstract<T> extends UpdatableDaoAbstract implements LoggableDao {

    protected LoggableDaoAbstract(final Connection connection, final String tableName) {
        super(connection, tableName);
    }

    /**
     * @param identifier specify the unique identifier of the object
     * @return the password, if the object with that identifier exists,
     * null otherwise
     */
    private String getCpPassword(final String identifier) {
        String strRet = null;
        final String query = this.getQuery(QueryType.FIND_ONE);
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, identifier);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                strRet = resultSet.getString("password");
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            //TODO Exception
            e.printStackTrace();
        }
        return strRet;
    }

    @Override
    public boolean login(final String identifier, final String password) {
        //Get password from DB
        final String passInDb = this.getCpPassword(identifier);
        //Check password
        if (passInDb == null) {
            //TODO Exception
            System.out.println("[DB]: LOGIN_FAIL: object not found");
            return false;
        } else if (!password.equals(passInDb)) {
            //TODO Exception
            System.out.println("[DB]: LOGIN_FAIL: wrong credentials");
            return false;
        } else {
            System.out.println("[DB]: LOGIN_OK");
            return true;
        }

    }


}
