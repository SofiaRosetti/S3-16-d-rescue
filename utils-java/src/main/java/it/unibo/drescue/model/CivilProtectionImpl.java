package it.unibo.drescue.model;

/**
 * Civil protection implementation
 */
public class CivilProtectionImpl implements CivilProtection {

    private String cpID;
    private String password;

    public CivilProtectionImpl(final String cpID, final String password) {
        this.cpID = cpID;
        this.password = password;
    }

    @Override
    public String getCpID() {
        return this.cpID;
    }

    @Override
    public void setCpID(final String cpID) {
        this.cpID = cpID;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        final String newLine = System.getProperty("line.separator");

        result.append("Civil protection: {" + newLine);
        result.append(" Cp ID: " + this.getCpID() + newLine);
        result.append(" Password: " + this.getPassword() + newLine);
        result.append("}");

        return result.toString();
    }
}
