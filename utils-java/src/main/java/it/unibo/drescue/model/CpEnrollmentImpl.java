package it.unibo.drescue.model;

/**
 * Implementation of an enrollment of a Rescue Team
 * in a Civil Protection.
 */
public class CpEnrollmentImpl implements CpEnrollment {

    private final String cpID;
    private final String rescueTeamID;

    public CpEnrollmentImpl(final String cpID, final String rescueTeamID) {
        this.cpID = cpID;
        this.rescueTeamID = rescueTeamID;
    }

    @Override
    public String getCpID() {
        return this.cpID;
    }

    @Override
    public String getRescueTeamID() {
        return this.rescueTeamID;
    }
}
