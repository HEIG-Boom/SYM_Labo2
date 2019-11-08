package ch.heigvd.sym.labo2.comm;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SymComManager extends AsyncTask<Request, Void, String> {
    private static final String TAG = SymComManager.class.getSimpleName();

    private CommunicationEventListener communicationEventListener = null;

    public void sendRequest(Request request) {
        execute(request);
    }

    public void setCommunicationEventListener(CommunicationEventListener communicationEventListener) {
        this.communicationEventListener = communicationEventListener;
    }

    @Override
    protected String doInBackground(Request... requests) {
        try {
            URL url = new URL(requests[0].getUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setChunkedStreamingMode(0);
            con.setRequestProperty("Content-Type", requests[0].getContentType());

            BufferedOutputStream writer = new BufferedOutputStream(con.getOutputStream());
            writer.write(requests[0].getData().getBytes());
            writer.flush();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "test";
    }
}
