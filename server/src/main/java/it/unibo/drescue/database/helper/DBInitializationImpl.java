package it.unibo.drescue.database.helper;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.JsonFileUtils;
import it.unibo.drescue.database.dao.CivilProtectionDao;
import it.unibo.drescue.database.dao.CpAreaDao;
import it.unibo.drescue.database.dao.DistrictDao;
import it.unibo.drescue.database.dao.EventTypeDao;
import it.unibo.drescue.database.exceptions.DBConnectionException;
import it.unibo.drescue.database.exceptions.DBDuplicatedRecordException;
import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DBInitializationImpl implements DBInitialization {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBInitializationImpl.class);

    private final JsonFileUtils jsonFileUtils = new JsonFileUtils();
    private final DBConnection dbConnection;

    public DBInitializationImpl(final DBConnection connection) {
        this.dbConnection = connection;
    }

    @Override
    public void insertAllDistrictsFrom(final String pathFile) {

        DistrictDao districtDao = null;
        //Getting districts from file
        final List<DistrictImpl> districts =
                this.jsonFileUtils.getListFromJsonFile(pathFile, DistrictImpl[].class);
        if (districts == null) {
            LOGGER.error("Districts are null");
            return;
        }
        //Getting Dao
        try {
            districtDao = (DistrictDao) this.dbConnection.getDAO(DBConnection.Table.DISTRICT);
        } catch (final DBConnectionException e) {
            LOGGER.error("DBConnectionException when get DAO of districts", e);
        }
        //Inserting districts
        for (final District district : districts) {
            try {
                districtDao.insert(district);
            } catch (final DBDuplicatedRecordException e) {
                LOGGER.error("DBDuplicatedRecordException when insert of a district already on the database", e);
            } catch (final DBQueryException e) {
                LOGGER.error("DBQueryException when insert a district", e);
            }
        }
    }


    @Override
    public void insertAllEventTypesFrom(final String pathFile) {
        EventTypeDao eventTypeDao = null;
        //Getting event_types from file
        final List<EventTypeImpl> eventTypeList =
                this.jsonFileUtils.getListFromJsonFile(pathFile, EventTypeImpl[].class);
        if (eventTypeList == null) {
            LOGGER.error("List of events type is null");
            return;
        }
        //Getting Dao
        try {
            eventTypeDao = (EventTypeDao) this.dbConnection.getDAO(DBConnection.Table.EVENT_TYPE);
        } catch (final DBConnectionException e) {
            LOGGER.error("DBConnectionException when get DAO of events type", e);
        }
        //Inserting events_type
        for (final EventType eventType : eventTypeList) {
            try {
                eventTypeDao.insert(eventType);
            } catch (final DBDuplicatedRecordException e) {
                LOGGER.error("DBDuplicatedRecordException when insert of a event type already on the database", e);
            } catch (final DBQueryException e) {
                LOGGER.error("DBQueryException when insert a event type", e);
            }
        }

    }

    @Override
    public void insertAllCivilProtectionsFrom(final String pathFile) {

        CivilProtectionDao civilProtectionDao = null;
        //Getting districts from file
        final List<CivilProtectionImpl> civilProtectionList =
                this.jsonFileUtils.getListFromJsonFile(pathFile, CivilProtectionImpl[].class);
        if (civilProtectionList == null) {
            LOGGER.error("List of civil protections is null");
            return;
        }
        //Getting Dao
        try {
            civilProtectionDao = (CivilProtectionDao)
                    this.dbConnection.getDAO(DBConnection.Table.CIVIL_PROTECTION);
        } catch (final DBConnectionException e) {
            LOGGER.error("DBConnectionException when get DAO of civil protections", e);
        }
        //Inserting districts
        for (final CivilProtection civilProtection : civilProtectionList) {
            try {
                civilProtectionDao.insert(civilProtection);
            } catch (final DBDuplicatedRecordException e) {
                LOGGER.error("DBDuplicatedRecordException when insert of a civil protection already on the database", e);
            } catch (final DBQueryException e) {
                LOGGER.error("DBQueryException when insert a civil protection", e);
            }
        }
    }

    @Override
    public void insertAllCpAreasFrom(final String pathFile) {

        CpAreaDao cpAreaDao = null;
        //Getting districts from file
        final List<CpAreaImpl> cpAreas =
                this.jsonFileUtils.getListFromJsonFile(pathFile, CpAreaImpl[].class);
        if (cpAreas == null) {
            LOGGER.error("List of civil protections area is null");
            return;
        }
        //Getting Dao
        try {
            cpAreaDao = (CpAreaDao) this.dbConnection.getDAO(DBConnection.Table.CP_AREA);
        } catch (final DBConnectionException e) {
            LOGGER.error("DBConnectionException when get DAO of civil protections area", e);
        }
        //Inserting districts
        for (final CpArea cpArea : cpAreas) {
            try {
                cpAreaDao.insert(cpArea);
            } catch (final DBDuplicatedRecordException e) {
                LOGGER.error("DBDuplicatedRecordException when insert of a civil protection area already on the database", e);
            } catch (final DBQueryException e) {
                LOGGER.error("DBQueryException when insert a civil protection area", e);
            }
        }
    }
}
