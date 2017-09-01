package it.unibo.drescue.database.helper;

public interface DBInitialization {

    /**
     * Insert into DB all districts listed in a json file
     *
     * @param pathFile indicates the path where the file with districts is located
     */
    void insertAllDistrictsFrom(String pathFile);

    /**
     * Insert into DB all event_types listed in a json file
     *
     * @param pathFile indicates the path where the file with event_types is located
     */
    void insertAllEventTypesFrom(String pathFile);

}
