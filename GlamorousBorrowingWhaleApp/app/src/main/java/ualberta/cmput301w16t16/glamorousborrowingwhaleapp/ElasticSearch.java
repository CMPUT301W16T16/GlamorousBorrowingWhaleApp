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

    /**
     * This class adds a user when they sign up for the app in the
     * SignUpActivity.
     * @author adam, andrew, erin, laura, martina
     * @see SignUpActivity
     */


//    // i strongly recommend deleting this if you understand ES at all - andrew
//    // because i do not, and it did not work
//    public static class GetUserTask extends AsyncTask<String, Void, User> {
//
//        @Override
//        protected User doInBackground(String... searchStrings) {
//            verifyClient();
//
//            // start our initial array list
//            ArrayList<User> user = new ArrayList<>();
//
//            // assuming only the first search term will be used
//            Search search = new Search.Builder(searchStrings[0]).addIndex("testing").addType("user").build();
//
//            try {
//                SearchResult results = client.execute(search);
//                if (results.getTotal() > 0) {
//                    Log.e("user found", "user found");
//                    // TODO: return user somehow
//                } else {
//                    // TODO: add an error
//                    // todo: this will trigger if our search fails
//                    Log.e("user not found", "user not found");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }


    public static class AddUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifyClient();
            // Since AsyncTasks work on arrays, we need to work with arrays as well (>= 1 tweet)
            for(int i = 0; i < users.length; i++) {
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

    /**
     * This class checks that anytime the client is being accessed, there is
     * one that has been saved and is available (so we're actually writing to
     * a real server
     * @author adam, andrew, erin, laura, martina
     */

    public static void verifyClient() {
        if(client == null) {
            // TODO: Put this URL somewhere it makes sense (e.g. class variable?)
            //Adam - we could toss in the strings xml ^^^^^^^^^^^
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}