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

    private Class getTheImplClassOf(final DBConnection.Table table) {
        Class clazz = null;
        switch (table) {
            case USER: {
                //TODO
                clazz = UserImpl[].class;
                break;
            }
            case DISTRICT: {
                clazz = DistrictImpl[].class;
                break;
            }
            case EVENT_TYPE: {
                clazz = EventTypeImpl[].class;
                break;
            }
            case CIVIL_PROTECTION: {
                clazz = CivilProtectionImpl[].class;
                break;
            }
            case CP_AREA: {
                clazz = CpAreaImpl[].class;
                break;
            }
            default: {
                LOGGER.error("Not existing table");
                break;
            }
        }
        return clazz;
    }

    @Override
    public void insertAllObjectsFromAFile(final DBConnection.Table table, final String pathFile) {
        GenericDao dao = null;
        final Class clazz = getTheImplClassOf(table);
        if (clazz == null) {
            LOGGER.error("Not existing table");
            return;
        }
        final List<ObjectModel> objectModels =
                this.jsonFileUtils.getListFromJsonFile(pathFile, clazz);
        if (objectModels == null) {
            LOGGER.error("objects are null");
            return;
        }
        try {
            dao = this.dbConnection.getDAO(table);
        } catch (final DBConnectionException e) {
            LOGGER.error("DBConnectionException when get DAO of districts", e);
        }

        //Inserting objects
        for (final ObjectModel objectModel : objectModels) {
            try {
                dao.insert(objectModel);
            } catch (final DBDuplicatedRecordException e) {
                LOGGER.error("DBDuplicatedRecordException when insert of a object of "
                        + table.name() + " already on the database", e);
            } catch (final DBQueryException e) {
                LOGGER.error("DBQueryException when insert an object of " + table.name(), e);
            }
        }
    }

}
