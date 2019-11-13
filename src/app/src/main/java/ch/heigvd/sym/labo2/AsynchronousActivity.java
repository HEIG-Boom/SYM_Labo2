package ch.heigvd.sym.labo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ch.heigvd.sym.labo2.comm.Request;
import ch.heigvd.sym.labo2.comm.SymComManager;

/**
 * Asynchronous activity class used to make asynchronous communication to the server
 */
public class AsynchronousActivity extends AppCompatActivity {
    // Graphics components
    private EditText editTextArea;
    private TextView requestTextArea;

    /**
     * Method called on creation of the activity.
     * Used to bind the communication event listener to the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the layout
        setContentView(R.layout.activity_asynchronous);

        editTextArea = findViewById(R.id.editText);
        requestTextArea = findViewById(R.id.responseText);
        Button sendBtn = findViewById(R.id.sendButton);

        requestTextArea.setMovementMethod(new ScrollingMovementMethod());

        sendBtn.setOnClickListener((v) -> {
            SymComManager mcm = new SymComManager();
            Request request = new Request("http://sym.iict.ch/rest/txt", editTextArea.getText().toString(), "text/plain");
            mcm.setCommunicationEventListener(
                    response -> {
                        requestTextArea.setText(response);
                        return true;
                    }
            );
            mcm.sendRequest(request);
        });
    }
}
