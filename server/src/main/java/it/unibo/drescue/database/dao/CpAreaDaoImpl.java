package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.CpArea;
import it.unibo.drescue.model.CpAreaImpl;
import it.unibo.drescue.model.ObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that provides the complete access to the CpArea object in DB
 * implements the abstract class GenericDaoAbstract and its interface
 */
public class CpAreaDaoImpl extends GenericDaoAbstract<CpArea> implements CpAreaDao {

    private static final String TABLENAME = "CP_AREA";
    private static final String FIND_RELATED_TO_DISTRICT_EXCEPTION =
            "Exception while trying to find a cp area related to a given district";
    private static final String FIND_RELATED_TO_CP_EXCEPTION =
            "Exception while trying to find a cp area related to a given cp";

    public CpAreaDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    protected String getQuery(final QueryType queryType) throws SQLException {
        switch (queryType) {
            case FIND_ONE:
                /*
                 * Note: The identifier in CpArea is 'cpID && districtID'
                 */
                return "SELECT cpID,districtID "
                        + "FROM " + TABLENAME + " WHERE cpID = ? AND districtID = ?";
            case INSERT:
                return "INSERT INTO " + TABLENAME + "(cpID,districtID)"
                        + " VALUE (?,?)";
            case DELETE:
                return "DELETE FROM " + TABLENAME
                        + " WHERE cpID = ? AND districtID = ?";
            case FIND_ALL:
                return "SELECT  cpID,districtID FROM " + TABLENAME;
            default:
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    protected PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement, final QueryType queryType) throws SQLException {
        final CpArea cpArea = (CpArea) objectModel;
        switch (queryType) {
            case FIND_ONE:
            case INSERT:
            case DELETE:
                statement.setString(1, cpArea.getCpID());
                statement.setString(2, cpArea.getDistrictID());
                break;
            default:
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
        return statement;
    }

    @Override
    protected ObjectModel mapRecordToModel(final ResultSet resultSet) throws SQLException {
        final CpArea cpArea = new CpAreaImpl(
                resultSet.getString("cpID"),
                resultSet.getString("districtID"));
        return cpArea;
    }

    @Override
    public List<CpArea> findAll() throws DBQueryException {
        final List<CpArea> cpAreaList = new ArrayList<>();
        try {
            final String query = this.getQuery(QueryType.FIND_ALL);
            final PreparedStatement statement = this.connection.prepareStatement(query);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final CpAreaImpl cpArea = new CpAreaImpl(
                        resultSet.getString("cpID"),
                        resultSet.getString("districtID"));
                cpAreaList.add(cpArea);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            throw new DBQueryException(FIND_ALL_EXCEPTION);
        }
        return cpAreaList;
    }

    @Override
    public List<CpArea> findCpAreasByDistrict(final String districtID) throws DBQueryException {
        final List<CpArea> cpAreaList = new ArrayList<>();
        try {
            final String query = "SELECT cpID,districtID"
                    + " FROM " + TABLENAME
                    + " WHERE districtID = ?";
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, districtID);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final CpAreaImpl cpArea = new CpAreaImpl(
                        resultSet.getString("cpID"),
                        resultSet.getString("districtID"));
                cpAreaList.add(cpArea);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            throw new DBQueryException(FIND_RELATED_TO_DISTRICT_EXCEPTION);
        }
        return cpAreaList;
    }

    @Override
    public List<CpArea> findCpAreasByCp(final String cpID) throws DBQueryException {
        final List<CpArea> cpAreaList = new ArrayList<>();
        try {
            final String query = "SELECT cpID,districtID"
                    + " FROM " + TABLENAME
                    + " WHERE cpID = ?";
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, cpID);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final CpAreaImpl cpArea = new CpAreaImpl(
                        resultSet.getString("cpID"),
                        resultSet.getString("districtID"));
                cpAreaList.add(cpArea);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            throw new DBQueryException(FIND_RELATED_TO_CP_EXCEPTION);
        }
        return cpAreaList;
    }


}
