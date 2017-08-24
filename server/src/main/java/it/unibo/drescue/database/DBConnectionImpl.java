package it.unibo.drescue.database;

import java.sql.*;

public class DBConnectionImpl implements DBConnection {

    private static final String STR_ESCAPE = "\'";
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static Connection connection;
    private static Statement statement;
    private DBInfo dbInfo;
    private String dbAddress;
    private String dbUsername;
    private String dbPassword;

    /**
     * Constructor without parameters, connect to REMOTE db
     */
    public DBConnectionImpl() {
        this.dbInfo = DBInfo.REMOTE;
        this.setDbInfo();
    }

    //TODO TO FIX: now it always connects to remote

    /**
     * @param info enum (LOCAL/REMOTE), to specify in which environment to connect
     */
    public DBConnectionImpl(DBInfo info) {
        this.dbInfo = info;
        this.setDbInfo();
    }

    private void setDbInfo() {
        switch (this.dbInfo) {
            case LOCAL:
                this.dbAddress = "jdbc:mysql://localhost:3306/testschema";
                this.dbUsername = "admin";
                this.dbPassword = "4dm1n";
            case REMOTE:
                this.dbAddress =
                        "jdbc:mysql://rds-mysql-drescue.cnwnbp8hx7vq.us-east-2.rds.amazonaws.com:3306/drescueDB";
                this.dbUsername = "masterDrescue";
                this.dbPassword = "rdsTeamPass";
        }
    }

    @Override
    public void openConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(DRIVER_NAME);
                System.out.println("[DB]: Connecting with db address: " + this.dbAddress);
                connection = DriverManager.getConnection(this.dbAddress,
                        this.dbUsername, this.dbPassword);
                statement = connection.createStatement();
                System.out.println("[DB]: Connection and statement created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        try {
            statement.close();
            connection.close();
            System.out.println("[DB]: Statement and connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getUserId(String email) {
        int id = -1;
        String query = "SELECT userID FROM USER WHERE email=" + STR_ESCAPE + email + STR_ESCAPE;
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                id = resultSet.getInt("userID");
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean registerUser(String email, String password, String name, String surname, String phoneNumber) {

        //Verify if email already exists
        if (this.getUserId(email) != -1) {
            System.out.println("[DB]: SIGNUP_FAIL: User " + email + " already registered");
            return false;
        }

        String query = "INSERT INTO USER (email,password,name,surname,phoneNumber)"
                + "VALUE ("
                + STR_ESCAPE + email + STR_ESCAPE + ","
                + STR_ESCAPE + password + STR_ESCAPE + ","
                + STR_ESCAPE + name + STR_ESCAPE + ","
                + STR_ESCAPE + surname + STR_ESCAPE + ","
                + STR_ESCAPE + phoneNumber + STR_ESCAPE
                + ")";
        try {
            statement.executeUpdate(query);
            System.out.println("[DB]: SIGNUP_OK: Added user " + email);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean unregisterUser(int userID) {

        String query = "DELETE FROM USER WHERE userID=" + userID;
        try {
            statement.executeUpdate(query);
            System.out.println("[DB]: DELETE_USER_OK: Deleted user " + userID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param email
     * @return the password if the user with the given email exist,
     * null otherwise
     */
    private String getUserPwd(String email) {
        String strRet = null;
        String query = "SELECT password FROM USER WHERE email=" + STR_ESCAPE + email + STR_ESCAPE;
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                strRet = resultSet.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strRet;
    }

    @Override
    public boolean login(String email, String password) {

        //Check password
        String passInDb = this.getUserPwd(email);
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

    public enum DBInfo {
        LOCAL,
        REMOTE
    }

}
