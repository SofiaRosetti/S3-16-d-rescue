package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.ObjectModel;
import it.unibo.drescue.model.User;
import it.unibo.drescue.model.UserImpl;
import it.unibo.drescue.model.UserImplBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl extends LoggableDaoAbstract<User> implements UserDao {

    private final static String TABLENAME = "USER";

    public UserDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    public String getQuery(final QueryType queryType) {
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
                //TODO Manage exception
                return null;
        }
    }

    @Override
    public PreparedStatement fillStatement(final ObjectModel objectModel, final PreparedStatement statement, final QueryType queryType) {
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
                    //TODO Excepion
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
        User user = null;
        try {
            user = new UserImplBuilder()
                    .setUserID(resultSet.getInt("userID"))
                    .setEmail(resultSet.getString("email"))
                    .setPassword(resultSet.getString("password"))
                    .setName(resultSet.getString("name"))
                    .setSurname(resultSet.getString("surname"))
                    .setPhoneNumber(resultSet.getString("phoneNumber"))
                    .createUserImpl();
        } catch (final SQLException e) {
            e.printStackTrace();
            //TODO handle exception
        }
        return user;
    }

}
