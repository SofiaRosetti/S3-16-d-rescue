package it.unibo.drescue.connection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

                    //TODO Insert data into DB

                    //Prepare response
                    JsonObject response = new JsonObject();

                    response.addProperty("code", 200);
                    response.add("data", new JsonObject());

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

}
