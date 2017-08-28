package it.unibo.mobileuser.connection;

import android.os.AsyncTask;
import it.unibo.drescue.StringUtils;

import java.io.*;
import java.net.Socket;

/**
 * Class used to send an async request to server.
 */
public class RequestAsyncTask extends AsyncTask<Void, Void, String> {

    private final RequestImpl request;
    private final RequestDelegate delegate;

    /**
     * Creates a new AsyncTask which performs the request.
     *
     * @param request  request to be performed
     * @param delegate delegate that handles the response
     */
    public RequestAsyncTask(final RequestImpl request, final RequestDelegate delegate) {
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

            String jsonString;
            jsonString = this.request.getRequestData().toString();
            if (!StringUtils.isAValidString(jsonString)) throw new IOException();

            toServer.println(jsonString);
            toServer.flush();

            jsonString = fromServer.readLine();
            if (!StringUtils.isAValidString(jsonString)) throw new IOException(); //TODO check

            System.out.println("[Client]: Response " + jsonString);
            return jsonString;

        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null)
                try {
                    socket.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
        }

        return null;
    }

    @Override
    protected void onPostExecute(final String result) {
        super.onPostExecute(result);
        if (this.delegate != null) {
            this.delegate.onReceivingResponse(result);
        }
    }


}
