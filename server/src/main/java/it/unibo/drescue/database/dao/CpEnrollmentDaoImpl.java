package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that provides the complete access to the CpEnrollment object in DB
 * implements the abstract class GenericDaoAbstract and its interface
 */
public class CpEnrollmentDaoImpl extends GenericDaoAbstract<CpEnrollment> implements CpEnrollmentDao {

    private static final String TABLENAME = "CP_ENROLLMENT";

    private static final String FIND_RELATED_TO_RESCUE_TEAM_EXCEPTION =
            "Exception while trying to find cp related to a given rescue team";
    private static final String FIND_RELATED_TO_CP_EXCEPTION =
            "Exception while trying to find a rescue teams related to a given cp";

    public CpEnrollmentDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    protected String getQuery(final QueryType queryType) throws SQLException {
        switch (queryType) {
            case FIND_ONE:
                /*
                 * Note: The identifier in CpEnrollment is 'cpID && rescueTeamID'
                 */
                return "SELECT cpID,rescueTeamID "
                        + "FROM " + TABLENAME + " WHERE cpID = ? AND rescueTeamID = ?";
            case INSERT:
                return "INSERT INTO " + TABLENAME + "(cpID,rescueTeamID)"
                        + " VALUE (?,?)";
            case DELETE:
                return "DELETE FROM " + TABLENAME
                        + " WHERE cpID = ? AND rescueTeamID = ?";
            default:
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    protected PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement, final QueryType queryType) throws SQLException {
        final CpEnrollment cpEnrollment = (CpEnrollment) objectModel;
        switch (queryType) {
            case FIND_ONE:
            case INSERT:
            case DELETE:
                statement.setString(1, cpEnrollment.getCpID());
                statement.setString(2, cpEnrollment.getRescueTeamID());
                break;
            default:
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
        return statement;
    }

    @Override
    protected ObjectModel mapRecordToModel(final ResultSet resultSet) throws SQLException {
        final CpEnrollment cpEnrollment = new CpEnrollmentImpl(
                resultSet.getString("cpID"),
                resultSet.getString("rescueTeamID"));
        return cpEnrollment;
    }

    @Override
    public List<CivilProtection> findAllCpRelatedToARescueTeam(final String rescueTeamID) throws DBQueryException {
        final List<CivilProtection> civilProtectionList = new ArrayList<>();
        try {
            final String query = "SELECT cpID"
                    + " FROM " + TABLENAME
                    + " WHERE rescueTeamID = ?";
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, rescueTeamID);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                //NOTE: the password is hidden
                final CivilProtection civilProtection = new CivilProtectionImpl(
                        resultSet.getString("cpID"), null);
                civilProtectionList.add(civilProtection);
            }
            resultSet.close();
            statement.close();

        } catch (final SQLException e) {
            throw new DBQueryException(FIND_RELATED_TO_RESCUE_TEAM_EXCEPTION);
        }
        return civilProtectionList;
    }

    @Override
    public List<RescueTeam> findAllRescueTeamGivenACp(final String cpID, final boolean related) throws DBQueryException {
        //Preparing query
        final String queryRelated = "SELECT " + RescueTeamDaoImpl.TABLENAME + ".rescueTeamID,"
                + RescueTeamDaoImpl.TABLENAME + ".name,"
                + RescueTeamDaoImpl.TABLENAME + ".latitude,"
                + RescueTeamDaoImpl.TABLENAME + ".longitude,"
                + RescueTeamDaoImpl.TABLENAME + ".phoneNumber"
                + " FROM " + RescueTeamDaoImpl.TABLENAME + " JOIN " + TABLENAME
                + " ON " + TABLENAME + ".rescueTeamID = " + RescueTeamDaoImpl.TABLENAME + ".rescueTeamID"
                + " WHERE " + TABLENAME + ".cpID = ?";
        final String nestedQuery = "SELECT " + RescueTeamDaoImpl.TABLENAME + ".rescueTeamID"
                + " FROM " + RescueTeamDaoImpl.TABLENAME + " JOIN " + TABLENAME
                + " ON " + TABLENAME + ".rescueTeamID = " + RescueTeamDaoImpl.TABLENAME + ".rescueTeamID"
                + " WHERE " + TABLENAME + ".cpID = ?";
        final String queryNotRelated = "SELECT " + RescueTeamDaoImpl.TABLENAME + ".rescueTeamID,"
                + RescueTeamDaoImpl.TABLENAME + ".name,"
                + RescueTeamDaoImpl.TABLENAME + ".latitude,"
                + RescueTeamDaoImpl.TABLENAME + ".longitude,"
                + RescueTeamDaoImpl.TABLENAME + ".phoneNumber"
                + " FROM " + RescueTeamDaoImpl.TABLENAME
                + " WHERE " + RescueTeamDaoImpl.TABLENAME + ".rescueTeamID NOT IN (" + nestedQuery + ")";

        final List<RescueTeam> rescueTeams = new ArrayList<>();
        try {
            final String query = related ? queryRelated : queryNotRelated;
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, cpID);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final RescueTeam rescueTeam = new RescueTeamImplBuilder()
                        .setRescueTeamID(resultSet.getString(RescueTeamDaoImpl.TABLENAME + ".rescueTeamID"))
                        .setName(resultSet.getString(RescueTeamDaoImpl.TABLENAME + ".name"))
                        .setLatitude(resultSet.getDouble(RescueTeamDaoImpl.TABLENAME + ".latitude"))
                        .setLongitude(resultSet.getDouble(RescueTeamDaoImpl.TABLENAME + ".longitude"))
                        .setPhoneNumber(resultSet.getString(RescueTeamDaoImpl.TABLENAME + ".phoneNumber"))
                        .createRescueTeamImpl();
                rescueTeams.add(rescueTeam);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            throw new DBQueryException(FIND_RELATED_TO_CP_EXCEPTION);
        }
        return rescueTeams;
    }
}
