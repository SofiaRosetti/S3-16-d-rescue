package it.unibo.drescue.model;

public class DistrictImpl implements District {

    private final String districtID;
    private final String districtLongName;
    private final int population;

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
    public String getDistrictLongName() {
        return this.districtLongName;
    }

    @Override
    public int getPopulation() {
        return this.population;
    }
}
