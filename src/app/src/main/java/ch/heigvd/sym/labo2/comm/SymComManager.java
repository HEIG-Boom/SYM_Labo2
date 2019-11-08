package ch.heigvd.sym.labo2.comm;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SymComManager extends AsyncTask {
    private static final String TAG = SymComManager.class.getSimpleName();

    private CommunicationEventListener communicationEventListener = null;

    public void sendRequest(String urlString, String request) {
    }

    public void setCommunicationEventListener(CommunicationEventListener communicationEventListener) {
        this.communicationEventListener = communicationEventListener;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}
