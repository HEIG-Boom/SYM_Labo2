package ch.heigvd.sym.labo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import ch.heigvd.sym.labo2.comm.Request;
import ch.heigvd.sym.labo2.comm.SymComManager;

/**
 * Asynchronous activity class used to make asynchronous communication to the server
 *
 * @author Jael Dubey, Loris Gilliand, Mateo Tutic, Luc Wachter
 * @version 1.0
 * @since 2019-11-08
 */
public class AsynchronousActivity extends AppCompatActivity {
    // Graphics components
    private EditText editText;
    private TextView responseText;

    /**
     * Method called on creation of the activity.
     * Used to bind the communication event listener to the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the layout
        setContentView(R.layout.activity_asynchronous);

        editText = findViewById(R.id.editText);
        responseText = findViewById(R.id.responseText);

        Button sendBtn = findViewById(R.id.sendButton);

        responseText.setMovementMethod(new ScrollingMovementMethod());

        sendBtn.setOnClickListener((v) -> {
            SymComManager mcm = new SymComManager();
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            Request request = new Request("http://sym.iict.ch/rest/txt", editText.getText().toString(), headers);
            mcm.setCommunicationEventListener(
                    response -> {
                        responseText.setText(response);
                        return true;
                    }
            );
            mcm.sendRequest(request);
        });
    }
}
