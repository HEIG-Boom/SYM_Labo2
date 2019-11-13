package ch.heigvd.sym.labo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Activity that provides a mean to send data to a server, delaying the request
 * if the server is unavailable or the connection dead
 *
 * @author Jael Dubey, Loris Gilliand, Mateo Tutic, Luc Wachter
 * @since 2019-11-13
 * @version 1.0
 */
public class DelayedActivity extends AppCompatActivity {
    /**
     * Set up buttons and actions
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delayed);
    }
}
