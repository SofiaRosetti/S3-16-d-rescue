package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.CpEnrollment;
import it.unibo.drescue.model.CpEnrollmentImpl;
import it.unibo.drescue.model.ObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CpEnrollmentDaoImpl extends GenericDaoAbstract<CpEnrollment> implements CpEnrollmentDao {

    private static final String TABLENAME = "CP_ENROLLMENT";

    private static final String FIND_RELATED_TO_RESCUE_TEAM_EXCEPTION =
            "Exception while trying to find cp_enrollments related to a given rescue team";
    private static final String FIND_RELATED_TO_CP_EXCEPTION =
            "Exception while trying to find a cp_enrollments related to a given cp";

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
            /*case FIND_RELATED_TO:
                return "SELECT cpID,rescueTeamID"
                        + " FROM " + TABLENAME
                        + " WHERE rescueTeamID = ?";*/
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
    public List<CpEnrollment> findAllCpEnrollmentRelatedToARescueTeam(final String rescueTeamID) throws DBQueryException {
        final List<CpEnrollment> cpEnrollments = new ArrayList<>();
        try {
            final String query = "SELECT cpID,rescueTeamID"
                    + " FROM " + TABLENAME
                    + " WHERE rescueTeamID = ?";
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, rescueTeamID);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final CpEnrollment cpEnrollment = new CpEnrollmentImpl(
                        resultSet.getString("cpID"),
                        resultSet.getString("rescueTeamID"));
                cpEnrollments.add(cpEnrollment);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            throw new DBQueryException(FIND_RELATED_TO_RESCUE_TEAM_EXCEPTION);
        }
        return cpEnrollments;
    }

    @Override
    public List<CpEnrollment> findAllCpEnrollmentRelatedToACp(final String cpID) throws DBQueryException {
        final List<CpEnrollment> cpEnrollments = new ArrayList<>();
        try {
            final String query = "SELECT cpID,rescueTeamID"
                    + " FROM " + TABLENAME
                    + " WHERE cpID = ?";
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, cpID);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final CpEnrollment cpEnrollment = new CpEnrollmentImpl(
                        resultSet.getString("cpID"),
                        resultSet.getString("rescueTeamID"));
                cpEnrollments.add(cpEnrollment);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            throw new DBQueryException(FIND_RELATED_TO_CP_EXCEPTION);
        }
        return cpEnrollments;
    }
}
