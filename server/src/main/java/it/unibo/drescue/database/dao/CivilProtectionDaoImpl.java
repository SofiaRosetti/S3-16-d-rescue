package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.CivilProtection;
import it.unibo.drescue.model.CivilProtectionImpl;
import it.unibo.drescue.model.ObjectModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that provides the complete access to the Alert object in DB
 * implements the abstract class LoggableDaoAbstract and its interface
 */
public class CivilProtectionDaoImpl extends LoggableDaoAbstract<CivilProtection> implements CivilProtectionDao {

    protected static final String TABLENAME = "CIVIL_PROTECTION";

    public CivilProtectionDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    protected String getQuery(final QueryType queryType) throws SQLException {
        switch (queryType) {
            /*
             * Note: the identifier in cp is cpID
             */
            case FIND_ONE:
                return "SELECT cpID,password"
                        + " FROM " + TABLENAME + " WHERE cpID = ?";
            case INSERT:
                return "INSERT INTO " + TABLENAME + "(cpID,password)"
                        + "VALUE (?,?)";
            case DELETE:
                return "DELETE FROM " + TABLENAME
                        + " WHERE cpID = ?";
            case UPDATE:
                return "UPDATE " + TABLENAME + " SET password = ? "
                        + "WHERE cpID = ?";
            default:
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    protected PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement, final QueryType queryType) throws SQLException {
        final CivilProtection cp = (CivilProtection) objectModel;
        switch (queryType) {
            case FIND_ONE:
                statement.setString(1, cp.getCpID());
                break;
            case INSERT:
                statement.setString(1, cp.getCpID());
                statement.setString(2, cp.getPassword());
                break;
            case DELETE:
                statement.setString(1, cp.getCpID());
                break;
            case UPDATE:
                final String newPassword = cp.getPassword();
                statement.setString(1, newPassword);
                statement.setString(2, cp.getCpID());
                break;
            default:
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }

        return statement;
    }

    @Override
    protected ObjectModel mapRecordToModel(final ResultSet resultSet) throws SQLException {
        final CivilProtection cp = new CivilProtectionImpl(
                resultSet.getString("cpID"),
                resultSet.getString("password"));
        return cp;
    }

}
