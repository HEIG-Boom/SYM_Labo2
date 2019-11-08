package ch.heigvd.sym.labo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ch.heigvd.sym.labo2.comm.CommunicationEventListener;
import ch.heigvd.sym.labo2.comm.SymComManager;

public class AsynchronousActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynchronous);

        SymComManager mcm = new SymComManager();
        mcm.setCommunicationEventListener(
                response -> {
                    System.out.println("toto");
                    return true;
                }
        );
        mcm.sendRequest("http://sym.iict.ch/rest/txt", "Affaire Robel Teklehaimanot");
    }
}
