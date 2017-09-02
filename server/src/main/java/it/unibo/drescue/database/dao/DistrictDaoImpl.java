package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.District;
import it.unibo.drescue.model.DistrictImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DistrictDaoImpl extends GenericDao<District> implements DistrictDao {

    private final static String TABLENAME = "DISTRICT";

    public DistrictDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    public District findById(final String districtId) {

        District district = null;
        final String query = "SELECT districtID,districtLongName,population "
                + "FROM " + TABLENAME + " WHERE districtID = ?";
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, districtId);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                district = new DistrictImpl(
                        resultSet.getString("districtID"),
                        resultSet.getString("districtLongName"),
                        resultSet.getInt("population"));
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return district;
    }

    @Override
    public List<District> findAll() {
        final List<District> districtList = new ArrayList<>();
        final String query = "SELECT  districtID, districtLongName, population FROM " + TABLENAME;
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final District district = new DistrictImpl(
                        resultSet.getString("districtID"),
                        resultSet.getString("districtLongName"),
                        resultSet.getInt("population")
                );
                districtList.add(district);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return districtList;
    }

    @Override
    public boolean insert(final District district) {

        //Verify if districtID already exists
        if (this.findById(district.getDistrictID()) != null) {
            System.out.println("[DB]: INSERT_DISTRICT_FAIL: " + district.getDistrictID() + " already in db");
            return false;
        }
        final String query = "INSERT INTO " + TABLENAME + "(districtID,districtLongName,population)"
                + "VALUE (?,?,?)";
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, district.getDistrictID());
            statement.setString(2, district.getDistrictLongName());
            statement.setInt(3, district.getPopulation());
            statement.executeUpdate();
            statement.close();
            System.out.println("[DB]: INSERT_DISTRICT_OK: Added district " + district.getDistrictLongName());
            return true;
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(final District district) {
        final String query = "DELETE FROM " + TABLENAME
                + " WHERE districtID = ?";
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, district.getDistrictID());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(final String districtId, final int population) {

        final String query = "UPDATE " + TABLENAME + " SET population = ? "
                + "WHERE districtID = ?";
        try {
            final PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, population);
            preparedStatement.setString(2, districtId);
            preparedStatement.executeUpdate();
            return true;
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
