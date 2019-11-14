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

public class CompressedActivity extends AppCompatActivity {
    // Graphics components
    private EditText editText;
    private TextView responseText;

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
