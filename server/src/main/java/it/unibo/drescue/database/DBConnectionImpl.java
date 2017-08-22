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
                connection = DriverManager.getConnection(this.dbAddress,
                        this.dbUsername, this.dbPassword);
                System.out.println("Connected to DB");
                statement = connection.createStatement();
                System.out.println("Statement created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        try {
            statement.close();
            System.out.println("Statement closed");
            connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getUserId(String email) {
        int id = -1;
        String query = "SELECT userID FROM User WHERE email=" + STR_ESCAPE + email + STR_ESCAPE;
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
            System.out.println("User " + email + " already registered");
            return false;
        }

        String query = "INSERT INTO User (email,password,phoneNumber,name,surname)"
                + "VALUE ("
                + STR_ESCAPE + email + STR_ESCAPE + ","
                + STR_ESCAPE + password + STR_ESCAPE + ","
                + STR_ESCAPE + phoneNumber + STR_ESCAPE + ","
                + STR_ESCAPE + name + STR_ESCAPE + ","
                + STR_ESCAPE + surname + STR_ESCAPE
                + ")";
        try {
            statement.executeUpdate(query);
            System.out.println("Added user " + email);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean unregisterUser(int userID) {

        String query = "DELETE FROM User WHERE userID=" + userID;
        try {
            statement.executeUpdate(query);
            System.out.println("Deleted user " + userID);
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
        String query = "SELECT password FROM User WHERE email=" + STR_ESCAPE + email + STR_ESCAPE;
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
            return false;
        } else {
            return password.equals(passInDb);
        }

    }

    protected enum DBInfo {
        LOCAL,
        REMOTE
    }

}
