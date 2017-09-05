package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.User;
import it.unibo.drescue.model.UserImplBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl extends GenericDao<User> implements UserDao {

    private final static String TABLENAME = "USER";

    public UserDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    public User findByEmail(final String email) {

        User user = null;
        final String query = "SELECT userID,email,password,name,surname,phoneNumber "
                + "FROM " + TABLENAME + " WHERE email = ?";
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

    @Override
    public boolean insert(final User user) {

        //Verify if email already exists
        if (this.findByEmail(user.getEmail()) != null) {
            System.out.println("[DB]: SIGNUP_FAIL: " + user.getEmail() + " already registered");
            return false;
        }
        final String query = "INSERT INTO " + TABLENAME + "(email,password,name,surname,phoneNumber)"
                + "VALUE (?,?,?,?,?)";
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getPhoneNumber());
            statement.executeUpdate();
            statement.close();
            System.out.println("[DB]: SIGNUP_OK: Added user " + user.getEmail());
            return true;
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(final User user) {
        final int userID = this.findByEmail(user.getEmail()).getUserID();
        final String query = "DELETE FROM " + TABLENAME
                + " WHERE userID = ?";
        try {
            final PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, userID);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return false;
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

}
