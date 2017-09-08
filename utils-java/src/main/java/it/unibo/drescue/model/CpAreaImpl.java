package it.unibo.drescue.model;

/**
 * Civil protection area implementation
 */
public class CpAreaImpl implements CpArea {

    private String cpID;
    private String districtID;

    public CpAreaImpl(final String cpID, final String districtID) {
        this.cpID = cpID;
        this.districtID = districtID;
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
    public String getDistrictID() {
        return this.districtID;
    }

    @Override
    public void setDistrictID(final String districtID) {
        this.districtID = districtID;
    }
}
