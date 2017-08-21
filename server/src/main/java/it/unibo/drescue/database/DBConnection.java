package it.unibo.drescue.database;

/**
 * A class that allows communication with database
 */
public interface DBConnection {


    /**
     * Establish the connection with DB
     */
    void openConnection();

    /**
     * Close the current open connection
     */
    void closeConnection();

    /**
     * @param email
     * @return the userID if the user with the given email exist,
     * -1 otherwise
     */
    int getUserId(String email);

    /**
     * This method register a given user into DB
     *
     * @param email
     * @param password
     * @param name
     * @param surname
     * @param phoneNumber
     * @return true if the given user is added into DB
     * false if the given user email already exist in the DB
     */
    boolean registerUser(String email, String password,
                         String name, String surname, String phoneNumber);

    /**
     * Delete a user from DB with the given userID (taken with getUser(email) method)
     *
     * @param userID
     * @return false if something goes wrong
     */
    boolean unregisterUser(int userID);

    /**
     * @param email
     * @param password
     * @return true if the credentials are valid, false otherwise
     */
    boolean login(String email, String password);


}
