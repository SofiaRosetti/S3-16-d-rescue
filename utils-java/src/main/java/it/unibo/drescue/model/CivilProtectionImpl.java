package it.unibo.drescue.model;

/**
 * Civil protection implementation
 */
public class CivilProtectionImpl implements CivilProtection {

    private final String cpID;
    private final String password;

    public CivilProtectionImpl(final String cpID, final String password) {
        this.cpID = cpID;
        this.password = password;
    }

    @Override
    public String getCpID() {
        return this.cpID;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
