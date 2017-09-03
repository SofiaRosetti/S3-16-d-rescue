package it.unibo.drescue.database.dao;

public interface UserDao extends UpdatableDao {

    /**
     * Check credentials
     *
     * @param email
     * @param password
     * @return true if the credentials are valid, false otherwise
     */
    boolean login(String email, String password);

}
