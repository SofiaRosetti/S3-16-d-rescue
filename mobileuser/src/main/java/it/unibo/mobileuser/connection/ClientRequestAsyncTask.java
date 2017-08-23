package it.unibo.mobileuser.connection;

import android.os.AsyncTask;
import it.unibo.drescue.StringUtils;

import java.io.*;
import java.net.Socket;

public class ClientRequestAsyncTask extends AsyncTask<Void, Void, String> {

    private final RequestImpl request;
    private final RequestDelegate delegate;

    public ClientRequestAsyncTask(final RequestImpl request, final RequestDelegate delegate) {
        this.request = request;
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(final Void... voids) {

        Socket socket = null;

        try {

            socket = new Socket(this.request.getAddress(), this.request.getPort());

            System.out.println("[Client]: Connetcted to " + socket.getInetAddress() +
                    ":" + socket.getPort());
            System.out.println("[Client]: ClientIP: " + socket.getLocalAddress());

            final InputStream sin = socket.getInputStream();
            final OutputStream sout = socket.getOutputStream();

            final BufferedReader fromServer = new BufferedReader(new InputStreamReader(sin));
            final PrintWriter toServer = new PrintWriter(new OutputStreamWriter(sout));

            //Traduction from JsonObject to String of the request
            String jsonString;
            jsonString = this.request.getRequestData().toString();
            if (!StringUtils.isAValidString(jsonString)) throw new IOException();

            //Send request to server
            toServer.println(jsonString);
            toServer.flush();

            //Waiting for server response
            jsonString = fromServer.readLine();
            if (StringUtils.isAValidString(jsonString)) throw new IOException();

            System.out.println("[Client]: Response " + jsonString);
            return jsonString;

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

        return null;
    }

}
