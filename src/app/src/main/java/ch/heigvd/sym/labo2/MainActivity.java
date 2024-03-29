package ch.heigvd.sym.labo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * Main activity class used to display all buttons for other activities
 *
 * @author Jael Dubey, Loris Gilliand, Mateo Tutic, Luc Wachter
 * @version 1.0
 * @since 2019-11-08
 */
public class MainActivity extends AppCompatActivity {

    private Button asynchronousBtn = null;
    private Button compressedBtn = null;
    private Button delayedBtn = null;
    private Button graphQLBtn = null;
    private Button objectBtn = null;

    /**
     * Set up buttons used to create other activities
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asynchronousBtn = findViewById(R.id.btnAsynchronous);
        compressedBtn = findViewById(R.id.btnCompressed);
        delayedBtn = findViewById(R.id.btnDelayed);
        graphQLBtn = findViewById(R.id.btnGraphQL);
        objectBtn = findViewById(R.id.btnObject);

        asynchronousBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(this, ch.heigvd.sym.labo2.AsynchronousActivity.class);
            this.startActivity(intent);
        });

        compressedBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(this, ch.heigvd.sym.labo2.CompressedActivity.class);
            this.startActivity(intent);
        });

        delayedBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(this, ch.heigvd.sym.labo2.DelayedActivity.class);
            this.startActivity(intent);
        });

        graphQLBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(this, ch.heigvd.sym.labo2.GraphQLActivity.class);
            this.startActivity(intent);
        });

        objectBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(this, ch.heigvd.sym.labo2.ObjectActivity.class);
            this.startActivity(intent);
        });
    }
}

