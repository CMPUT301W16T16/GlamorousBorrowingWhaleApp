package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
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

    // private final static String clientAddress = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/User/_search?&pretty";

    // elasticGetItems.execute(URL) takes in a URL (with /Item)... but it probably doesn't need to...
    // and retrieves all items on the ElasticSearch list, adding them to the returned ItemList.
    // Used in SearchResultsActivity, which also should sort somehow?
    public static class elasticGetItems extends AsyncTask<String, String, ItemList> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ItemList doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url;

            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                // this hanging while loop ironically speeds the process up dramatically
                // and suppresses warnings, i think w/o it it retries the line after indefinitely
                while (connection == null) {}
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String longStringOfJSON = buffer.toString();
                JSONObject allOfTheJSON = new JSONObject(longStringOfJSON);
                JSONObject subsetOfTheJSON = allOfTheJSON.getJSONObject("hits");
                JSONArray hitsList = subsetOfTheJSON.getJSONArray("hits");
                // each thing in this list should become its own User or Item
                for (int i = 0; i < hitsList.length(); i++) {
                    JSONObject thingInList = hitsList.getJSONObject(i);
                    // convert thing to its Type
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            // ugly "closing down shop" section
            if (connection != null)
                connection.disconnect();
            try{
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // the returned item is passed on to onPostExecute as "result"
            return null;
        }

        @Override
        protected void onPostExecute(ItemList result) {
            super.onPostExecute(result);
        }
    }

    // Used in NewListingActivity
    public static class elasticAddItem extends AsyncTask<Item, String, String> {

        // the following worked:
        // http://cmput301.softwareprocess.es:8080/cmput301w16t16/Item/ in the URL and  {"name":"mouse", "size":"tiny"} in the body

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Item... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url;

            try {
                url = new URL("http://cmput301.softwareprocess.es:8080/cmput301w16t16/Item/");
                connection = (HttpURLConnection) url.openConnection();
                // this hanging while loop ironically speeds the process up dramatically
                // and suppresses warnings, i think w/o it it retries the line after indefinitely
                while (connection == null) {}
                connection.connect();
                OutputStream stream = connection.getOutputStream();

                // actually write to that output stream here

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

    }

    // used in SignUpActivity
    public static class elasticAddUser extends AsyncTask<User, String, String> {

        // the following worked:
        // http://cmput301.softwareprocess.es:8080/cmput301w16t16/Item/ in the URL and  {"name":"mouse", "size":"tiny"} in the body

        User user = UserController.getUser();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(User... params) {

            HttpURLConnection connection = null;
            BufferedReader writer = null;
            URL url;

            try {
                url = new URL("http://cmput301.softwareprocess.es:8080/cmput301w16t16/User/");
                connection = (HttpURLConnection) url.openConnection();
                // this hanging while loop ironically speeds the process up dramatically
                // and suppresses warnings, i think w/o it it retries the line after indefinitely
                while (connection == null) {}
                connection.connect();
                OutputStream stream = connection.getOutputStream();

                // this isn't currently working, although it isn't causing a crash either
                // the json created below looks like {"username":<the user's name>, "emailAddress":<the user's email address>, etc... }
                // and I'm not sure if that's exactly the thing we're supposed to be writing to the output stream
                // taken Mar-13-2016 from http://stackoverflow.com/questions/18983185/how-to-create-correct-jsonarray-in-java-using-jsonobject
                JSONObject jo = new JSONObject();
                jo.put("username", user.getName());
                jo.put("emailAddress", user.getEmailAddress());
                jo.put("phoneNumber", user.getPhoneNumber());
                // when getting photo back there is an extra step to convert back to byte
                jo.put("photo", user.getPhoto());

                // array containing the items in itemsBorrowing
                // will all be separate when getting them back to they will have to be put
                // into an array and then set as the user's items borrowing
                JSONArray ib = new JSONArray();
                for(Item item: user.getItemsBorrowing()) {
                    // this should probably be serialized to JSON?
                    ib.put(item);
                }
                jo.put("itemsBorrowing", ib);

                // array containing the items in itemsRenting
                // same notes as with items borrowing
                JSONArray ir = new JSONArray();
                for(Item item: user.getItemsRenting()) {
                    ir.put(item);
                }
                jo.put("itemsRenting", ir);

                // ja is the array containing the entire message
                JSONArray ja = new JSONArray();
                ja.put(jo);

                JSONObject mainObj = new JSONObject();
                mainObj.put("", ja);

                // actually write to that output stream here
                stream.write(mainObj.toString().getBytes());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // ugly "closing down shop" section
            if (connection != null)
                connection.disconnect();

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

    }

    // used in MyItemActivity
    public static class elasticDelete extends AsyncTask<Item, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Item... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

    }

    // PROBLEM:
    // this should NOT be Asynchronous (result is needed immediately)...
    // used in SignInActivity
    public static class elasticFind extends AsyncTask<String, String, User> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected User doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(User result) {
            super.onPostExecute(result);
        }

    }

}