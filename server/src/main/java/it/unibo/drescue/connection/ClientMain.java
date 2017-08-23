package it.unibo.drescue.connection;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;

public class ClientMain {

    private static final String SERVER_ADDRESS = "ec2-13-58-168-22.us-east-2.compute.amazonaws.com";
    private static final int PORT = 4321;

    public static void main(final String[] args) {

        Socket socket = null;

        try {

            socket = new Socket(SERVER_ADDRESS, PORT);

            System.out.println("[Client]: connetcted to " + socket.getInetAddress() +
                    ":" + socket.getPort());
            System.out.println("[Client]: clientIP: " + socket.getLocalAddress());

            final InputStream sin = socket.getInputStream();
            final OutputStream sout = socket.getOutputStream();

            //Stream from server
            final BufferedReader fromServer = new BufferedReader(new InputStreamReader(sin));
            //Stream to server
            final PrintWriter toServer = new PrintWriter(new OutputStreamWriter(sout));

            //Example of request
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", "testEmail@test.it");
            jsonObject.addProperty("password", "testPassword");
            jsonObject.addProperty("name", "Test User");
            jsonObject.addProperty("surname", "Test");
            jsonObject.addProperty("phoneNumber", "3496247111");

            //Traduction from JsonObject to String of the request
            String jsonString = jsonObject.toString();

            //jsonString = requestItem.getJsonObject().toString();
            if (jsonString.equals(null)) throw new IOException();

            //Send request to server
            toServer.println(jsonString);
            toServer.flush();

            //Waiting for server response
            jsonString = fromServer.readLine();
            if (jsonString == null) throw new IOException();

            System.out.println("[Client]: response " + jsonString);


        } catch (final IOException e) {
            e.printStackTrace();
        }

        //Close the socket
        finally {
            if (socket != null)
                try {
                    socket.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
        }

    }

}
