package it.unibo.drescue.model;

/**
 * Implementation of an enrollment of a Rescue Team
 * in a Civil Protection.
 */
public class CpEnrollmentImpl implements CpEnrollment {

    private String cpID;
    private String rescueTeamID;

    public CpEnrollmentImpl(final String cpID, final String rescueTeamID) {
        this.cpID = cpID;
        this.rescueTeamID = rescueTeamID;
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
    public String getRescueTeamID() {
        return this.rescueTeamID;
    }

    @Override
    public void setRescueTeamID(final String rescueTeamID) {
        this.rescueTeamID = rescueTeamID;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        final String newLine = System.getProperty("line.separator");

        result.append("Civil protection enrollment: {" + newLine);
        result.append(" Cp ID: " + this.getCpID() + newLine);
        result.append(" Rescue team ID: " + this.getRescueTeamID() + newLine);
        result.append("}");

        return result.toString();
    }
}
