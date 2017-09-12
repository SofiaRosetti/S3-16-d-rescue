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

import java.util.List;

public class DBInitializationImpl implements DBInitialization {

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
            //TODO handle exception
            System.out.println("districts: null");
            return;
        }
        //Getting Dao
        try {
            districtDao = (DistrictDao) this.dbConnection.getDAO(DBConnection.Table.DISTRICT);
        } catch (final DBConnectionException e) {
            //TODO handle exception
            e.printStackTrace();
        }
        //Inserting districts
        for (final District district : districts) {
            try {
                districtDao.insert(district);
            } catch (final DBDuplicatedRecordException e) {
                //TODO handle exception
                System.out.println("Duplicate " + district.getDistrictID());
            } catch (final DBQueryException e) {
                //TODO handle exception
                e.printStackTrace();
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
            System.out.println("eventTypeList: null");
            return; //TODO handle exception
        }
        //Getting Dao
        try {
            eventTypeDao = (EventTypeDao) this.dbConnection.getDAO(DBConnection.Table.EVENT_TYPE);
        } catch (final DBConnectionException e) {
            //TODO handle exception
            e.printStackTrace();
        }
        //Inserting events_type
        for (final EventType eventType : eventTypeList) {
            try {
                eventTypeDao.insert(eventType);
            } catch (final DBDuplicatedRecordException e) {
                //TODO handle exception
                System.out.println("Duplicate " + eventType.getEventName());
            } catch (final DBQueryException e) {
                //TODO handle exception
                e.printStackTrace();
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
            //TODO Exception
            System.out.println("cp: null");
            return;
        }
        //Getting Dao
        try {
            civilProtectionDao = (CivilProtectionDao)
                    this.dbConnection.getDAO(DBConnection.Table.CIVIL_PROTECTION);
        } catch (final DBConnectionException e) {
            e.printStackTrace();
            //TODO handle exception
        }
        //Inserting districts
        for (final CivilProtection civilProtection : civilProtectionList) {
            try {
                civilProtectionDao.insert(civilProtection);
            } catch (final DBDuplicatedRecordException e) {
                //TODO handle exception
                System.out.println("Duplicate " + civilProtection.getCpID());
            } catch (final DBQueryException e) {
                //TODO handle exception
                e.printStackTrace();
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
            //TODO Exception
            System.out.println("cpareas: null");
            return;
        }
        //Getting Dao
        try {
            cpAreaDao = (CpAreaDao) this.dbConnection.getDAO(DBConnection.Table.CP_AREA);
        } catch (final DBConnectionException e) {
            e.printStackTrace();
        }
        //Inserting districts
        for (final CpArea cpArea : cpAreas) {
            try {
                cpAreaDao.insert(cpArea);
            } catch (final DBDuplicatedRecordException e) {
                //TODO handle exception
                System.out.println("Duplicate " + cpArea.getCpID() + " + " + cpArea.getDistrictID());
            } catch (final DBQueryException e) {
                //TODO handle exception
                e.printStackTrace();
            }
        }
    }
}
