package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.ObjectModel;
import it.unibo.drescue.model.User;
import it.unibo.drescue.model.UserImpl;
import it.unibo.drescue.model.UserImplBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl extends UpdatableDaoAbstract<User> implements UserDao {

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
                    final int userIDToDel = this.findByEmail(user.getEmail()).getUserID();
                    statement.setInt(1, userIDToDel);
                    break;
                case UPDATE:
                    final int userIDToUpdate = this.findByEmail(user.getEmail()).getUserID();
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
    public User findByEmail(final String email) {

        User user = null;
        final String query = getQuery(QueryType.FIND_ONE);
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, email);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new UserImplBuilder()
                        .setUserID(resultSet.getInt("userID"))
                        .setEmail(resultSet.getString("email"))
                        .setPassword(resultSet.getString("password"))
                        .setName(resultSet.getString("name"))
                        .setSurname(resultSet.getString("surname"))
                        .setPhoneNumber(resultSet.getString("phoneNumber"))
                        .createUserImpl();
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * @param email specify the unique user email
     * @return the password if the user with the given email exist,
     * null otherwise
     */
    private String getUserPwd(final String email) {
        String strRet = null;
        final String query = "SELECT password FROM " + TABLENAME
                + " WHERE email = ?";
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, email);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                strRet = resultSet.getString("password");
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return strRet;
    }

    @Override
    public boolean login(final String email, final String password) {
        //TODO CHECK
        //Get password from DB
        final String passInDb = this.getUserPwd(email);
        //Check password
        if (passInDb == null) {
            System.out.println("[DB]: LOGIN_FAIL: User " + email + " not registered yet");
            return false;
        } else if (!password.equals(passInDb)) {
            System.out.println("[DB]: LOGIN_FAIL: User " + email + ", wrong credentials");
            return false;
        } else {
            System.out.println("[DB]: LOGIN_OK: User " + email + " log in correctly");
            return true;
        }

    }

    @Override
    public ObjectModel getObject(final ObjectModel objectModel) {
        final User user = (UserImpl) objectModel;
        return this.findByEmail(user.getEmail());
    }

}
