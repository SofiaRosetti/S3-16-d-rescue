package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBNotFoundRecordException;
import it.unibo.drescue.model.LoggableModel;

import java.sql.Connection;

/*Note: LoggableDao extends from Updatable because an object with credentials should change the password*/
public abstract class LoggableDaoAbstract<T> extends UpdatableDaoAbstract implements LoggableDao {

    protected LoggableDaoAbstract(final Connection connection, final String tableName) {
        super(connection, tableName);
    }

    @Override
    public LoggableModel login(final LoggableModel loggableInserted) throws DBNotFoundRecordException {

        final LoggableModel loggableInDb =
                (LoggableModel) this.selectByIdentifier(loggableInserted);
        if (loggableInDb == null) {
            System.out.println("[DB]: LOGIN_FAIL: object not found");
            throw new DBNotFoundRecordException();
        }

        final String passInDb = loggableInDb.getPassword();
        final String passInserted = loggableInserted.getPassword();

        if (!passInserted.equals(passInDb)) {
            System.out.println("[DB]: LOGIN_FAIL: wrong credentials");
            throw new DBNotFoundRecordException();
        } else {
            System.out.println("[DB]: LOGIN_OK");
            /*Note: return the object without password*/
            loggableInDb.setPassword("");
            return loggableInDb;
        }

    }


}
