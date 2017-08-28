package it.unibo.drescue.model;

/**
 * Civil protection area implementation
 */
public class CpAreaImpl implements CpArea {

    private final String cpID;
    private final String districtID;

    public CpAreaImpl(final String cpID, final String districtID) {
        this.cpID = cpID;
        this.districtID = districtID;
    }

    @Override
    public String getCpID() {
        return this.cpID;
    }

    @Override
    public String getDistrictID() {
        return this.districtID;
    }
}
