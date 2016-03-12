package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.os.AsyncTask;
import android.util.Log;

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
 */
public class ElasticSearch {
<<<<<<< HEAD
    //tossed a final in here since we should only have to have one of these
    private final static String clientAddress = "http://cmput301.softwareprocess.es:8080";
=======
>>>>>>> f05388df0ffde64fe3db166e0d169d15015968b6
    private static JestDroidClient client;

    public static class AddUserTask extends AsyncTask<User, Void, Void> {
        protected Void doInBackground(User... users) {
            verifyClient();
<<<<<<< HEAD

            // Since AsyncTasks work on arrays, we need to work with arrays as well (>= 1 tweet)
            for(int i = 0; i < users.length; i++) {
=======
            for (int i = 0; i < users.length; i++) {
>>>>>>> f05388df0ffde64fe3db166e0d169d15015968b6
                User user = users[i];

                Index index = new Index.Builder(user).index("cmput301w16t16").type("user").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        user.setID(result.getId());
                    } else {
                        // TODO: Add an error message, because this was puzzling.
                        // TODO: Right here it will trigger if the insert fails
                        Log.i("TODO", "We actually failed here, adding a user");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static void verifyClient() {
        if(client == null) {
            // TODO: Put this URL somewhere it makes sense (e.g. class variable?)
<<<<<<< HEAD
            //Adam - we could toss in the strings xml ^^^^^^^^^^^
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(clientAddress);
=======
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
>>>>>>> f05388df0ffde64fe3db166e0d169d15015968b6
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}