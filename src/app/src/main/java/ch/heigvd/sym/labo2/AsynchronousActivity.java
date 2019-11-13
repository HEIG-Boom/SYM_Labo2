package ch.heigvd.sym.labo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ch.heigvd.sym.labo2.comm.Request;
import ch.heigvd.sym.labo2.comm.SymComManager;

public class AsynchronousActivity extends AppCompatActivity {
    private Button sendBtn;
    private EditText editTextArea;
    private TextView requestTextArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynchronous);

        editTextArea = findViewById(R.id.editText);
        requestTextArea = findViewById(R.id.responseText);
        sendBtn = findViewById(R.id.sendButton);

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
