package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Created by erin on 11/03/16.
 */
public class ElasticSearch {
    private static String clientAddress = "http://cmput301.softwareprocess.es:8080";
    private static JestDroidClient client;

    public static class AddUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifyClient();

            for(int i = 0; i < users.length; i++) {
                User user = users[i];

                Index index = new Index.Builder(user).index("cmput301w16t16").type("User").build();
                try {
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()) {
                        // Set the ID to tweet that elasticsearch told me it was
                        user.setID(result.getId());
                    } else {
                        // TODO: Add an error message, because this was puzzling.
                        // TODO: Right here it will trigger if the insert fails
                        Log.i("TODO", "We actually failed here, adding a tweet");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static void verifyClient() {
        // 1. Verify that 'client' exists.
        if(client == null) {
            // 2. If it doesn't, make it.
            // TODO: Put this URL somewhere it makes sense (e.g. class variable?)
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(clientAddress);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}