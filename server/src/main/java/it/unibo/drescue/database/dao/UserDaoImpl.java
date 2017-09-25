package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.ObjectModel;
import it.unibo.drescue.model.User;
import it.unibo.drescue.model.UserImpl;
import it.unibo.drescue.model.UserImplBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that provides the complete access to the User object in DB
 * implements the abstract class LoggableDaoAbstract and its interface
 */
public class UserDaoImpl extends LoggableDaoAbstract<User> implements UserDao {

    private final static String TABLENAME = "USER";

    public UserDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    protected String getQuery(final QueryType queryType) throws SQLException {
        switch (queryType) {
            case INSERT:
                return "INSERT INTO " + TABLENAME + "(email,password,name,surname,phoneNumber)"
                        + "VALUE (?,?,?,?,?)";
            case DELETE:
                return "DELETE FROM " + TABLENAME
                        + " WHERE userID = ?";
            case FIND_ONE:
                /*
                * Note: in User the identifier is 'email'
                */
                return "SELECT userID,email,password,name,surname,phoneNumber "
                        + "FROM " + TABLENAME + " WHERE email = ?";
            case UPDATE:
                return "UPDATE " + TABLENAME + " SET password = ? "
                        + "WHERE userID = ?";
            default:
                throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    protected PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement, final QueryType queryType) throws SQLException {
        final User user = (UserImpl) objectModel;
        try {
            switch (queryType) {
                case INSERT:
                    statement.setString(1, user.getEmail());
                    statement.setString(2, user.getPassword());
                    statement.setString(3, user.getName());
                    statement.setString(4, user.getSurname());
                    statement.setString(5, user.getPhoneNumber());
                    break;
                case DELETE:
                    final int userIDToDel = ((User) this.selectByIdentifier(user)).getUserID();
                    statement.setInt(1, userIDToDel);
                    break;
                case FIND_ONE:
                    statement.setString(1, user.getEmail());
                    break;
                case UPDATE:
                    final int userIDToUpdate = ((User) this.selectByIdentifier(user)).getUserID();
                    final String newPassword = user.getPassword();
                    statement.setString(1, newPassword);
                    statement.setInt(2, userIDToUpdate);
                    break;
                default:
                    throw new SQLException(QUERY_NOT_FOUND_EXCEPTION);
            }

        } catch (final Exception e) {
            throw new SQLException();
        }
        return statement;

    }

    @Override
    protected ObjectModel mapRecordToModel(final ResultSet resultSet) throws SQLException {
        User user = null;
        user = new UserImplBuilder()
                .setUserID(resultSet.getInt("userID"))
                .setEmail(resultSet.getString("email"))
                .setPassword(resultSet.getString("password"))
                .setName(resultSet.getString("name"))
                .setSurname(resultSet.getString("surname"))
                .setPhoneNumber(resultSet.getString("phoneNumber"))
                .createUserImpl();
        return user;
    }

}
