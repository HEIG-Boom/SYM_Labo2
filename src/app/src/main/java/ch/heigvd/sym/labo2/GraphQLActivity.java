package ch.heigvd.sym.labo2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.heigvd.sym.labo2.comm.CommunicationEventListener;
import ch.heigvd.sym.labo2.comm.Request;
import ch.heigvd.sym.labo2.comm.SymComManager;
import ch.heigvd.sym.labo2.model.Author;

/**
 * GraphQL activity class used to send some GraphQL request to the server
 *
 * @author Jael Dubey, Loris Gilliand, Mateo Tutic, Luc Wachter
 * @version 1.0
 * @since 2019-11-08
 */
public class GraphQLActivity extends AppCompatActivity {
    // Graphics components
    private Spinner authorsSpinner;
    private LinearLayout postsVerticalLayout;
    private List<Author> allAuthors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the layout
        setContentView(R.layout.activity_graph_ql);

        authorsSpinner = findViewById(R.id.authors_spinner);
        postsVerticalLayout = findViewById(R.id.posts_vertical_layout);

        // Get all authors and populate the spinner
        getAllAuthors();

        // Register a callback to be invoked when an item in the spinner has been selected
        authorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int authorIndex = parent.getSelectedItemPosition();

                // Get all author's posts when an author is selected
                getPosts(allAuthors.get(authorIndex).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Gets all author's posts
     *
     * @param aid The author's id
     */
    private void getPosts(int aid) {
        String graphQLRequest = String.format("{\"query\": \"{author(id: %d){posts{title content}}}\"}", aid);

        sendGraphQLRequest(graphQLRequest, response -> {
            JsonObject jsonResponse = new Gson().fromJson(response, JsonObject.class).getAsJsonObject("data");

            // Check if we deal with author's posts
            if (jsonResponse.has("author")) {
                JsonArray postsArray = jsonResponse.getAsJsonObject("author").getAsJsonArray("posts");

                // Remove all posts already on the layout
                postsVerticalLayout.removeAllViews();

                // Iterate over all author's posts
                for (JsonElement postElement : postsArray) {
                    JsonObject post = postElement.getAsJsonObject();

                    // Create graphics element to display all author's posts
                    TextView title = new TextView(this);
                    title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    title.setText(post.get("title").getAsString());

                    TextView content = new TextView(this);
                    LinearLayout.LayoutParams layoutParams =
                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 30, 0, 30);
                    content.setText(post.get("content").getAsString());

                    // Add to the author's posts scroll view
                    postsVerticalLayout.addView(title);
                    postsVerticalLayout.addView(content, layoutParams);
                }
            }

            return true;
        });
    }

    /**
     * Gets all authors and populates the spinner
     */
    private void getAllAuthors() {
        String graphQLRequest = "{\"query\": \"{allAuthors{id first_name last_name}}\"}";

        sendGraphQLRequest(graphQLRequest, response -> {
            JsonObject jsonResponse = new Gson().fromJson(response, JsonObject.class).getAsJsonObject("data");

            // Check if we deal with all authors
            if (jsonResponse.has("allAuthors")) {
                JsonArray jsonAuthors = jsonResponse.getAsJsonArray("allAuthors");

                // Iterate over all json authors
                for (JsonElement authorElement : jsonAuthors) {
                    JsonObject author = authorElement.getAsJsonObject();
                    allAuthors.add(new Author(author.get("id").getAsInt(),
                            author.get("first_name").getAsString(), author.get("last_name").getAsString()));
                }

                // Populate the spinner with allAuthors list
                // Source : https://www.mkyong.com/android/android-spinner-drop-down-list-example/
                ArrayAdapter<Author> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                        allAuthors);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                authorsSpinner.setAdapter(dataAdapter);
            }

            return true;
        });
    }

    /**
     * Sends a GraphQL request to the server and handles the GraphQL response with the communication event listener
     *
     * @param graphQLRequest The GraphQL request to send to the server
     * @param cel            The communication event listener
     */
    private void sendGraphQLRequest(String graphQLRequest, CommunicationEventListener cel) {
        SymComManager mcm = new SymComManager();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Request request = new Request("http://sym.iict.ch/api/graphql", graphQLRequest, headers);

        mcm.setCommunicationEventListener(cel);
        mcm.sendRequest(request);
    }
}
