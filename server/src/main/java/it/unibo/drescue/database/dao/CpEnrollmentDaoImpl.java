package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.CpEnrollment;
import it.unibo.drescue.model.CpEnrollmentImpl;
import it.unibo.drescue.model.ObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CpEnrollmentDaoImpl extends GenericDaoAbstract<CpEnrollment> implements CpEnrollmentDao {

    private static final String TABLENAME = "CP_ENROLLMENT";

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
}
