package it.unibo.drescue.database;

import java.sql.*;

public class DBConnectionImpl implements DBConnection {

    private static final String STR_ESCAPE = "\'";
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String LOCAL_DB_URL = "jdbc:mysql://localhost:3306/testschema";
    private static final String LOCAL_DB_USERNAME = "admin";
    private static final String LOCAL_DB_PASSWORD = "4dm1n";

    private static Connection connection;
    private static Statement statement;

    public DBConnectionImpl() {
    }

    @Override
    public void openConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(DRIVER_NAME);
                connection = DriverManager.getConnection(LOCAL_DB_URL,
                        LOCAL_DB_USERNAME, LOCAL_DB_PASSWORD);
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

    @Override
    public boolean login(String email, String password) {

        //TODO
        return false;
    }


}
