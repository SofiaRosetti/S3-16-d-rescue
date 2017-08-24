package it.unibo.drescue.connection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.DBConnectionImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static void main(String[] args) {

        String line;
        Socket socketConnection = null;
        ServerSocket listenSocket = null;

        try {
            listenSocket = new ServerSocket(4321);

            while (true) {

                //Wait a connection request
                System.out.println("[Server]: Waiting...");
                socketConnection = listenSocket.accept();

                //Manage input and output stream
                InputStream sin = socketConnection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(sin));
                PrintStream out = new PrintStream(socketConnection.getOutputStream());

                //Read data from client
                line = in.readLine();

                if (line != null) {
                    //Get data from request
                    JsonObject requestJson = new JsonParser().parse(line).getAsJsonObject();

                    String email = requestJson.get("email").getAsString();
                    String password = requestJson.get("password").getAsString();
                    String name = requestJson.get("name").getAsString();
                    String surname = requestJson.get("surname").getAsString();
                    String phoneNumber = requestJson.get("phoneNumber").getAsString();

                    System.out.println("[Server]: Received email: " + email);
                    System.out.println("[Server]: Received password: " + password);
                    System.out.println("[Server]: Received name: " + name);
                    System.out.println("[Server]: Received surname: " + surname);
                    System.out.println("[Server]: Received phoneNumber: " + phoneNumber);

                    //Insert data into DB
                    DBConnection dbConnection = DBConnectionImpl.getRemoteConnection();
                    dbConnection.openConnection();

                    //Prepare response
                    JsonObject response = createResponse(false, new JsonObject());

                    try {
                        if (dbConnection.registerUser(email, password, name, surname, phoneNumber)) {
                            System.out.println("[Server]: Data inserted into DB");
                            //Prepare response
                            response = createResponse(true, new JsonObject());
                        } else {
                            System.out.println("[Server]: Error inserting data into DB");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        dbConnection.closeConnection();
                    }

                    //Send response to client
                    out.println(response);
                }

                //Close connection
                socketConnection.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        //Close socket
        finally {
            if (socketConnection != null)
                try {
                    socketConnection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private static JsonObject createResponse(boolean completed, JsonObject data) {

        JsonObject response = new JsonObject();

        if (completed) {
            response.addProperty("code", 200);
        } else {
            response.addProperty("code", 400);
        }
        response.add("data", data);
        return response;
    }

}
