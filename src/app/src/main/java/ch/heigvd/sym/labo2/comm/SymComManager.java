package ch.heigvd.sym.labo2.comm;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * Communication manager class, uses an event listener to handle the response
 *
 * @author Jael Dubey, Loris Gilliand, Mateo Tutic, Luc Wachter
 * @version 1.0
 * @since 2019-11-08
 */
public class SymComManager extends AsyncTask<Request, Void, String> {
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

            OutputStream writer;
            if (header.containsKey("X-Network")) {
                System.out.println("hello");
                Deflater deflate = new Deflater(Deflater.DEFAULT_COMPRESSION,true);
                writer = new DeflaterOutputStream(con.getOutputStream(), deflate);
            } else {
                writer = new BufferedOutputStream(con.getOutputStream());
            }

            writer.write(requests[0].getData().getBytes());
            writer.flush();
            writer.close();


            if (con.getResponseCode() == 200) {
                InputStream reader;
                if (header.containsKey("X-Network")) {
                    Inflater inflater = new Inflater(true);
                    reader = new InflaterInputStream(con.getInputStream(), inflater);
                } else {
                    reader = new BufferedInputStream(con.getInputStream());
                }

                byte[] contents = new byte[1024];

                int bytesRead = 0;
                while ((bytesRead = reader.read(contents)) != -1) {
                    response += new String(contents, 0, bytesRead);
                }
                reader.close();
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
