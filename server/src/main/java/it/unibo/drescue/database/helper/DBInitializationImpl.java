package it.unibo.drescue.database.helper;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.JsonFileUtils;
import it.unibo.drescue.database.dao.GenericDao;
import it.unibo.drescue.database.exceptions.DBConnectionException;
import it.unibo.drescue.database.exceptions.DBDuplicatedRecordException;
import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Class that allows to initialize records of database.
 */
public class DBInitializationImpl implements DBInitialization {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBInitializationImpl.class);

    private final JsonFileUtils jsonFileUtils = new JsonFileUtils();
    private final DBConnection dbConnection;

    public DBInitializationImpl(final DBConnection connection) {
        this.dbConnection = connection;
    }

    /**
     * Method that returns the class of the implementation class for a
     * specific object model related to a table given
     *
     * @param table
     * @return the class of the implementation class for an object of the specified table
     */
    private Class getTheImplClassOf(final DBConnection.Table table) {
        switch (table) {
            case USER:
                return UserImpl[].class;
            case DISTRICT:
                return DistrictImpl[].class;
            case EVENT_TYPE:
                return EventTypeImpl[].class;
            case CIVIL_PROTECTION:
                return CivilProtectionImpl[].class;
            case CP_AREA:
                return CpAreaImpl[].class;
            case CP_ENROLLMENT:
                return CpEnrollmentImpl[].class;
            case RESCUE_TEAM:
                return RescueTeamImpl[].class;
            default: {
                LOGGER.error("Not existing table");
                return null;
            }
        }
    }

    @Override
    public void insertAllObjectsFromAFile(final DBConnection.Table table, final String pathFile) {
        GenericDao dao = null;
        final Class clazz = this.getTheImplClassOf(table);
        if (clazz == null) {
            LOGGER.error("Not existing table");
            return;
        }
        final List<ObjectModel> objectModels =
                this.jsonFileUtils.getListFromJsonFile(pathFile, clazz);
        if (objectModels == null) {
            LOGGER.error("There are no object in file " + pathFile);
            return;
        }
        try {
            dao = this.dbConnection.getDAO(table);
        } catch (final DBConnectionException e) {
            LOGGER.error("DBConnectionException when get DAO ", e);
        }

        //Inserting objects
        for (final ObjectModel objectModel : objectModels) {
            try {
                dao.insert(objectModel);
            } catch (final DBDuplicatedRecordException e) {
                LOGGER.error("DBDuplicatedRecordException when insert of a object of "
                        + table.name() + " already on the database");
            } catch (final DBQueryException e) {
                LOGGER.error("DBQueryException inserting an object of " + table.name(), e);
            } catch (final Exception e) {
                LOGGER.error("Unknown exception inserting an object of " + table.name(), e);
            }
        }
    }

}
