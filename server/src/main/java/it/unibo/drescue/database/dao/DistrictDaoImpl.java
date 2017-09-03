package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.District;
import it.unibo.drescue.model.DistrictImpl;
import it.unibo.drescue.model.ObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DistrictDaoImpl extends GenericDaoAbstract<District> implements DistrictDao {

    private final static String TABLENAME = "DISTRICT";

    public DistrictDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    public String getQuery(final QueryType queryType) {
        switch (queryType) {
            case INSERT:
                return "INSERT INTO " + TABLENAME + "(districtID,districtLongName,population)"
                        + "VALUE (?,?,?)";
            case DELETE:
                return "DELETE FROM " + TABLENAME
                        + " WHERE districtID = ?";
            case FIND_ONE:
                return "SELECT districtID,districtLongName,population "
                        + "FROM " + TABLENAME + " WHERE districtID = ?";
            case FIND_ALL:
                return "SELECT  districtID, districtLongName, population FROM " + TABLENAME;
            default:
                //TODO Manage Exception
                return null;
        }
    }

    @Override
    public District findById(final String districtId) {

        District district = null;
        final String query = this.getQuery(QueryType.FIND_ONE);
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
        final String query = this.getQuery(QueryType.FIND_ALL);
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

    @Override
    public ObjectModel getObject(final ObjectModel objectModel) {
        return findById(((DistrictImpl) objectModel).getDistrictID());
    }

    @Override
    public PreparedStatement compileInsertStatement(final ObjectModel objectModel, final PreparedStatement statement) {
        final District district = ((DistrictImpl) objectModel);
        try {
            statement.setString(1, district.getDistrictID());
            statement.setString(2, district.getDistrictLongName());
            statement.setInt(3, district.getPopulation());
        } catch (final SQLException e) {
            //TODO handle
            e.printStackTrace();
            return null;
        }
        return statement;
    }

    @Override
    protected PreparedStatement compileDeleteStatement(final ObjectModel objectModel, final PreparedStatement statement) {
        final District district = ((DistrictImpl) objectModel);
        try {
            statement.setString(1, district.getDistrictID());
        } catch (final SQLException e) {
            //TODO handle
            e.printStackTrace();
            return null;
        }
        return statement;
    }
}
