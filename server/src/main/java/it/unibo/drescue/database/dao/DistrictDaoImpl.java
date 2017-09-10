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

public class DistrictDaoImpl extends UpdatableDaoAbstract<District> implements DistrictDao {

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
                /*
                * Note: The identifier in district is 'districtID'
                */
                return "SELECT districtID,districtLongName,population "
                        + "FROM " + TABLENAME + " WHERE districtID = ?";
            case FIND_ALL:
                return "SELECT  districtID, districtLongName, population FROM " + TABLENAME;
            case UPDATE:
                return "UPDATE " + TABLENAME + " SET population = ? "
                        + "WHERE districtID = ?";
            default:
                //TODO Handle Exception
                return null;
        }
    }

    @Override
    public PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement, final QueryType queryType) {
        final District district = ((DistrictImpl) objectModel);
        try {
            switch (queryType) {
                case INSERT:
                    statement.setString(1, district.getDistrictID());
                    statement.setString(2, district.getDistrictLongName());
                    statement.setInt(3, district.getPopulation());
                    break;
                case DELETE:
                    statement.setString(1, district.getDistrictID());
                    break;
                case FIND_ONE:
                    statement.setString(1, district.getDistrictID());
                    break;
                case UPDATE:
                    //used to update population of that specific districtID
                    statement.setInt(1, district.getPopulation());
                    statement.setString(2, district.getDistrictID());
                    statement.executeUpdate();
                    break;
                default:
                    //TODO Exception 'query not available for this object'
            }

        } catch (final SQLException e) {
            e.printStackTrace();
            //TODO handle exception
            return null;
        }
        return statement;
    }

    @Override
    protected ObjectModel mapRecordToModel(final ResultSet resultSet) {
        District district = null;
        try {
            district = new DistrictImpl(
                    resultSet.getString("districtID"),
                    resultSet.getString("districtLongName"),
                    resultSet.getInt("population"));
        } catch (final SQLException e) {
            e.printStackTrace();
            //TODO handle
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
                final District district = (District) mapRecordToModel(resultSet);
                districtList.add(district);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return districtList;
    }

}
