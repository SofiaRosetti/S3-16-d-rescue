package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.User;

public interface UserDao {

    /**
     * @param email specify the user's email
     * @return the user if a user with the given email exist
     */
    User findByEmail(String email);

    /**
     * This method register a given user into DB
     *
     * @param user to register
     * @return true if the given user is added into DB
     * false if the given user email already exists in the DB
     */
    boolean insert(User user);

    /**
     * Delete a user from DB
     *
     * @param user to delete
     * @return false if something goes wrong
     */
    boolean delete(User user);

    /**
     * @param email
     * @param password
     * @return true if the credentials are valid, false otherwise
     */
    boolean login(String email, String password);


}
