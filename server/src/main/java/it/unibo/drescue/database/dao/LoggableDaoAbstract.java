package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBNotFoundRecordException;
import it.unibo.drescue.model.LoggableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * Abstract class from which all DAO implementation that needs a login method extends,
 * it contains the template method for all objects in DB.
 * Note that LoggableDao extends from Updatable because an object with credentials
 * should have a password that could be changed
 *
 * @param <T> specify the class of the object interested in DAO, in this case an ObjectModel
 */
public abstract class LoggableDaoAbstract<T> extends UpdatableDaoAbstract implements LoggableDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggableDaoAbstract.class);

    protected LoggableDaoAbstract(final Connection connection, final String tableName) {
        super(connection, tableName);
    }

    @Override
    public LoggableModel login(final LoggableModel loggableInserted) throws DBNotFoundRecordException {

        final LoggableModel loggableInDb =
                (LoggableModel) this.selectByIdentifier(loggableInserted);
        if (loggableInDb == null) {
            LOGGER.error("LOGIN FAIL: object not found");
            throw new DBNotFoundRecordException();
        }

        final String passInDb = loggableInDb.getPassword();
        final String passInserted = loggableInserted.getPassword();

        if (!passInserted.equals(passInDb)) {
            LOGGER.error("LOGIN FAIL: wrong credentials");
            throw new DBNotFoundRecordException();
        } else {
            LOGGER.info("LOGIN OK");
            /*Note: return the object without password*/
            loggableInDb.setPassword("");
            return loggableInDb;
        }

    }


}
