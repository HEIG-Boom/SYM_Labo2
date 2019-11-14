package ch.heigvd.sym.labo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import ch.heigvd.sym.labo2.comm.Request;
import ch.heigvd.sym.labo2.comm.SymComManager;

/**
 * Compressed activity class used to transfer Java a compressed request to the server
 *
 * @author Jael Dubey, Loris Gilliand, Mateo Tutic, Luc Wachter
 * @version 1.0
 * @since 2019-11-08
 */
public class CompressedActivity extends AppCompatActivity {
    // Graphics components
    private EditText editText;
    private TextView responseText;

    /**
     * Method called on creation of the activity.
     * Set the required headers to simulate low speed transfert and compress data, send the request
     * to the server and display the response after it was uncompressed.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the layout
        setContentView(R.layout.activity_compressed);

        editText = findViewById(R.id.editText);
        responseText = findViewById(R.id.responseText);

        Button sendBtn = findViewById(R.id.sendButton);

        responseText.setMovementMethod(new ScrollingMovementMethod());

        sendBtn.setOnClickListener((v) -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            SymComManager mcm = new SymComManager();
            HashMap<String, String> headers = new HashMap();

            // Set several headers
            headers.put("X-Network", "CSD");
            headers.put("Content-Type", "text/plain");
            headers.put("Content-Encoding", "deflate");

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
