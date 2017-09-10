package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.CpArea;
import it.unibo.drescue.model.CpAreaImpl;
import it.unibo.drescue.model.ObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CpAreaDaoImpl extends GenericDaoAbstract<CpArea> implements CpAreaDao {

    private static final String TABLENAME = "CP_AREA";

    public CpAreaDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    public String getQuery(final QueryType queryType) {
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
                //TODO Exception
                return null;
        }
    }

    @Override
    public PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement, final QueryType queryType) {
        final CpArea cpArea = (CpArea) objectModel;
        try {
            switch (queryType) {
                case FIND_ONE:
                case INSERT:
                case DELETE:
                    statement.setString(1, cpArea.getCpID());
                    statement.setString(2, cpArea.getDistrictID());
                    break;
                default:
                    //TODO Exception
                    return null;
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    @Override
    protected ObjectModel mapRecordToModel(final ResultSet resultSet) {
        CpArea cpArea = null;
        try {
            cpArea = new CpAreaImpl(
                    resultSet.getString("cpID"),
                    resultSet.getString("districtID"));
        } catch (final SQLException e) {
            e.printStackTrace();
            //TODO Exception
        }
        return cpArea;
    }

    @Override
    public List<CpArea> findAll() {
        final List<CpArea> cpAreaList = new ArrayList<>();
        final String query = this.getQuery(QueryType.FIND_ALL);
        try {
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
            e.printStackTrace();
        }
        return cpAreaList;
    }
}
