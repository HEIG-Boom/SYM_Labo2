package ch.heigvd.sym.labo2.comm;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Communication manager class, uses an event listener to handle the response
 *
 * @author Jael Dubey, Loris Gilliand, Mateo Tutic, Luc Wachter
 * @since 2019-11-08
 * @version 1.0
 */
public class SymComManager extends AsyncTask<Request, Void, String> {
    private static final String TAG = SymComManager.class.getSimpleName();

    private String response = "";

    private CommunicationEventListener communicationEventListener = null;

    /**
     * Allows the caller to send the request
     *
     * @param request Contains the data to send, the destination url and content-type
     */
    public void sendRequest(Request request) {
        execute(request);
    }

    /**
     * Lets the caller set the event listener with the action to take
     *
     * @param communicationEventListener
     */
    public void setCommunicationEventListener(CommunicationEventListener communicationEventListener) {
        this.communicationEventListener = communicationEventListener;
    }

    /**
     * Main request code, sends to server and reads response in background
     *
     * @param requests
     * @return The response from the server
     */
    @Override
    protected String doInBackground(Request... requests) {
        try {
            URL url = new URL(requests[0].getUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.setChunkedStreamingMode(0);

            HashMap<String, String> header = requests[0].getHeaders();
            Iterator it = header.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> pair = (Map.Entry) it.next();
                con.setRequestProperty(pair.getKey(), pair.getValue());
            }

            BufferedOutputStream writer = new BufferedOutputStream(con.getOutputStream());
            writer.write(requests[0].getData().getBytes());
            writer.flush();

            if (con.getResponseCode() == 200) {
                BufferedInputStream reader = new BufferedInputStream(con.getInputStream());

                byte[] contents = new byte[1024];

                int bytesRead = 0;
                while((bytesRead = reader.read(contents)) != -1) {
                    response += new String(contents, 0, bytesRead);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * Executed after the doInBackground method, handles the response
     *
     * @param args
     */
    @Override
    protected void onPostExecute(String args) {
        super.onPostExecute(args);
        communicationEventListener.handleServerResponse(response);
    }
}
