package it.unibo.drescue.database.helper;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.JsonFileUtils;
import it.unibo.drescue.database.dao.DistrictDao;
import it.unibo.drescue.database.dao.EventTypeDao;
import it.unibo.drescue.model.District;
import it.unibo.drescue.model.DistrictImpl;
import it.unibo.drescue.model.EventType;
import it.unibo.drescue.model.EventTypeImpl;

import java.sql.SQLException;
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
            //TODO Exception
            System.out.println("districts: null");
            return;
        }
        //Getting Dao
        try {
            districtDao = (DistrictDao) this.dbConnection.getDAO(DBConnection.Table.DISTRICT);
        } catch (final SQLException e) {
            //TODO Exception
            e.printStackTrace();
            return;
        }
        //Inserting districts
        for (final District district : districts) {
            districtDao.insert(district);
            //TODO handle exception
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
            return; //TODO Exception
        }
        //Getting Dao
        try {
            eventTypeDao = (EventTypeDao) this.dbConnection.getDAO(DBConnection.Table.EVENT_TYPE);
        } catch (final SQLException e) {
            //TODO Exception
            e.printStackTrace();
            return;
        }
        //Inserting events_type
        for (final EventType eventType : eventTypeList) {
            eventTypeDao.insert(eventType);
            //TODO Handle Exception
        }

    }
}
