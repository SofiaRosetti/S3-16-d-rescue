package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.ObjectModel;
import it.unibo.drescue.model.RescueTeam;
import it.unibo.drescue.model.RescueTeamImplBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that provides the complete access to the RescueTeam object in DB
 * implements the abstract class LoggableDaoAbstract and its interface
 */
public class RescueTeamDaoImpl extends LoggableDaoAbstract<RescueTeam> implements RescueTeamDao {
    protected static final String TABLENAME = "RESCUE_TEAM";

    public RescueTeamDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    protected String getQuery(final QueryType queryType) throws SQLException {
        switch (queryType) {
            /*
             * Note: the identifier in rescue team is rescueTeamID
             */
            case FIND_ONE:
                return "SELECT rescueTeamID,password,name,latitude,longitude,phoneNumber"
                        + " FROM " + TABLENAME + " WHERE rescueTeamID = ?";
            case INSERT:
                return "INSERT INTO " + TABLENAME
                        + " (rescueTeamID,password,name,latitude,longitude,phoneNumber)"
                        + " VALUES (?,?,?,?,?,?)";
            case DELETE:
                return "DELETE FROM " + TABLENAME
                        + " WHERE rescueTeamID = ?";
            case UPDATE:
                return "UPDATE " + TABLENAME + " SET password = ?"
                        + " WHERE rescueTeamID = ?";
            default:
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    protected PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement, final QueryType queryType) throws SQLException {
        final RescueTeam rescueTeam = ((RescueTeam) objectModel);
        switch (queryType) {
            case INSERT:
                statement.setString(1, rescueTeam.getRescueTeamID());
                statement.setString(2, rescueTeam.getPassword());
                statement.setString(3, rescueTeam.getName());
                statement.setDouble(4, rescueTeam.getLatitude());
                statement.setDouble(5, rescueTeam.getLongitude());
                statement.setString(6, rescueTeam.getPhoneNumber());
                break;
            case FIND_ONE:
            case DELETE:
                statement.setString(1, rescueTeam.getRescueTeamID());
                break;
            case UPDATE:
                statement.setString(1, rescueTeam.getPassword());
                statement.setString(2, rescueTeam.getRescueTeamID());
                break;
            default:
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
        return statement;
    }

    @Override
    protected ObjectModel mapRecordToModel(final ResultSet resultSet) throws SQLException {
        final RescueTeam rescueTeam = new RescueTeamImplBuilder()
                .setRescueTeamID(resultSet.getString("rescueTeamID"))
                .setPassword(resultSet.getString("password"))
                .setName(resultSet.getString("name"))
                .setLatitude(resultSet.getDouble("latitude"))
                .setLongitude(resultSet.getDouble("longitude"))
                .setPhoneNumber(resultSet.getString("phoneNumber"))
                .createRescueTeamImpl();
        return rescueTeam;
    }

}
