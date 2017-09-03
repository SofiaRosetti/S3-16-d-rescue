package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.User;

public interface UserDao extends UpdatableDao {

    /**
     * Find a user given the email
     *
     * @param email specify the user's email
     * @return the user if a user with the given email exists
     */
    User findByEmail(String email);

    /**
     * Check credentials
     *
     * @param email
     * @param password
     * @return true if the credentials are valid, false otherwise
     */
    boolean login(String email, String password);

}
