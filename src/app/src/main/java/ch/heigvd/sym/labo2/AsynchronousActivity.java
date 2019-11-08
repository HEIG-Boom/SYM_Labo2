package ch.heigvd.sym.labo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import ch.heigvd.sym.labo2.comm.CommunicationEventListener;
import ch.heigvd.sym.labo2.comm.Request;
import ch.heigvd.sym.labo2.comm.SymComManager;

public class AsynchronousActivity extends AppCompatActivity {
    private Button sendBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynchronous);

        sendBtn = findViewById(R.id.sendButton);
        sendBtn.setOnClickListener((v) -> {
            SymComManager mcm = new SymComManager();
            Request request = new Request("http://sym.iict.ch/rest/txt", "Affaire Robel Teklehaimanot", "text/plain");
            mcm.setCommunicationEventListener(
                    response -> {
                        System.out.println(response);
                        return true;
                    }
            );
            mcm.sendRequest(request);
        });
    }
}
