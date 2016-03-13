package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by erin on 11/03/16.
 * This class performs elastic search methods such as saving a user, saving an
 * item and searching for things. We used Jestdroid and got most of the code
 * from lonelyTwitter (CMPUT301 lab app)
 * @author adam, andrew, erin, laura, martina
 */

public class ElasticSearch {
    //tossed a final in here since we should only have to have one of these
    private final static String clientAddress = "http://cmput301.softwareprocess.es:8080";
    private static JestDroidClient client;

    /*
    //this doesn't work yet, copied from lonelytwitter
    public static class GetUserTask extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... search_strings) {
            verifyConfig();

            // Start our initial array list (empty)
            ArrayList<User> users = new ArrayList<User>();

            // NOTE: I'm a making a huge assumption here, that only the first search term
            // will be used.
            String query = "{\n \"query\": {\n \"filtered\": {\n \"query\"";

            Search search = new Search.Builder(query)
                    // multiple index or types can be added.
                    .addIndex("cmput301w16t16")
                    .addIndex("User")
                    .build();
            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()) {
                    // Return our list of users
                    List<User> returned_users = execute.getSourceAsObjectList(User.class);
                    users.addAll(returned_users);
                } else {
                    // TODO: Add an error message, because that other thing was puzzling.
                    // TODO: Right here it will trigger if the search fails
                    Log.i("TODO", "We actually failed here, searching for tweets");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return users;
        }
    }
    */

    /**
     * This class adds a user when they sign up for the app in the
     * SignUpActivity.
     * @author adam, andrew, erin, laura, martina
     * @see SignUpActivity
     */

    // this doesn't cause an error anymore when the app is run without calling it in
    // signup activity, but there is an error when calling it from sign up activity
    public static class AddUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... params) {
            verifyConfig();

            for(User user : params) {
                Index index = new Index.Builder(user).index("cmput301w16t16").type("User").build();

                try {
                    DocumentResult execute = client.execute(index);
                    if(execute.isSucceeded()) {
                        user.setID(execute.getId());
                    } else {
                        Log.e("TODO", "Our insert of user failed");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * This class checks that anytime the client is being accessed, there is
     * one that has been saved and is available (so we're actually writing to
     * a real server
     * @author adam, andrew, erin, laura, martina
     */
    // If no client, add one
    public static void verifyConfig() {
        if(client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(clientAddress);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}