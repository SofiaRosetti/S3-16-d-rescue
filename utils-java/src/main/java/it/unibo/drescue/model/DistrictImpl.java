package it.unibo.drescue.model;

public class DistrictImpl implements District {

    private String districtID;
    private String districtLongName;
    private int population;

    public DistrictImpl(final String districtID, final String districtLongName, final int population) {
        this.districtID = districtID;
        this.districtLongName = districtLongName;
        this.population = population;
    }

    @Override
    public String getDistrictID() {
        return this.districtID;
    }

    @Override
    public void setDistrictID(final String districtID) {
        this.districtID = districtID;
    }

    @Override
    public String getDistrictLongName() {
        return this.districtLongName;
    }

    @Override
    public void setDistrictLongName(final String districtLongName) {
        this.districtLongName = districtLongName;
    }

    @Override
    public int getPopulation() {
        return this.population;
    }

    @Override
    public void setPopulation(final int population) {
        this.population = population;
    }
}
