package it.unibo.drescue.model;

public interface LoggableModel extends ObjectModel {

    /**
     * @return the password
     */
    String getPassword();

    /**
     * Sets the password
     *
     * @param password the password
     */
    void setPassword(String password);

}
