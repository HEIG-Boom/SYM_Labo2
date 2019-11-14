package ch.heigvd.sym.labo2;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.os.Bundle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ch.heigvd.sym.labo2.comm.Request;
import ch.heigvd.sym.labo2.comm.SymComManager;

/**
 * Activity that provides a mean to send data to a server, delaying the request
 * if the server is unavailable or the connection dead
 * <p>
 * To test it, disconnect from the internet, send a few requests, reconnect and
 * see the responses
 *
 * @author Jael Dubey, Loris Gilliand, Mateo Tutic, Luc Wachter
 * @version 1.0
 * @since 2019-11-13
 */
public class DelayedActivity extends AppCompatActivity {
    // Rate at which to try again when no internet connectivity
    private static final int SCHEDULE_RATE = 2;
    // Server address
    private static final String SERVER = "http://sym.iict.ch/rest/txt";

    // Graphics components
    private EditText editTextArea;
    private TextView requestTextArea;

    // Queue of requests
    List<Request> requests = new LinkedList<>();

    /**
     * Set up buttons and actions
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delayed);

        // Bind graphical elements
        editTextArea = findViewById(R.id.editText);
        requestTextArea = findViewById(R.id.responseText);
        Button sendBtn = findViewById(R.id.sendButton);

        requestTextArea.setMovementMethod(new ScrollingMovementMethod());

        // Schedule a thread to run every SCHEDULE_RATE seconds and try sending the requests
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::tryAndSendAllRequests, 0, SCHEDULE_RATE, TimeUnit.SECONDS);

        // Set up button action
        sendBtn.setOnClickListener((v) -> {
            // Create request and store it in request list
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            Request request = new Request(SERVER, editTextArea.getText().toString(), headers);

            requests.add(request);
        });
    }

    /**
     * Check whether the network is available by connecting to web socket
     * <p>
     * source: https://stackoverflow.com/questions/1560788/
     * how-to-check-internet-access-on-android-inetaddress-never-times-out?page=1&tab=votes#tab-top
     *
     * @return false if no interface is connected, true if one is
     */
    public boolean isNetworkAvailable() {
        int timeoutMs = 200;
        Socket sock = new Socket();

        try {
            SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
            // Try to connect
            sock.connect(socketAddress, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) {
            // Return false if connection went wrong
            return false;
        }
    }

    /**
     * Executed periodically, send all requests in the list if the network
     * is available
     */
    private void tryAndSendAllRequests() {
        // If there are requests in the queue
        if (!requests.isEmpty()) {
            // If the network is available
            if (isNetworkAvailable()) {
                // Send all requests
                for (Request request : requests) {
                    SymComManager scm = new SymComManager();
                    scm.setCommunicationEventListener((response) -> {
                        // Log response
                        System.out.println("Delayed response: " + response);
                        // Display response in terminal
                        requestTextArea.setText(response);
                        return true;
                    });

                    scm.sendRequest(request);
                }
                requests.clear();
            }
        }
    }
}
