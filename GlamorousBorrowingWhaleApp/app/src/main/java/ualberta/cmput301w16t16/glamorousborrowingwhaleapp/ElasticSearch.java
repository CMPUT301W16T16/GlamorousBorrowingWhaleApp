package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by erin on 11/03/16.
 * This class performs elastic search methods such as saving a user, saving an
 * item and searching for things. We used Jestdroid and got most of the code
 * from lonelyTwitter (CMPUT301 lab app)
 * @author adam, andrew, erin, laura, martina
 */

public class ElasticSearch extends Application {
    public Context context;

    // retrieves all items on the ElasticSearch list, adding them to the returned ItemList.
    // Used in SearchResultsActivity.
    public static class elasticGetItems extends AsyncTask<ListView, String, ItemList> {

        TextView tv;
        ListView itemsListView;
        ArrayAdapter<Item> adapter;
        Context context;

        public elasticGetItems(Context context){
            this.context = context;
        }

        @Override
        protected ItemList doInBackground(ListView... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url;
            itemsListView = params[0];

            try {
                url = new URL("http://cmput301.softwareprocess.es:8080/cmput301w16t16/Item/_search?");
                connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String longStringOfJSON = buffer.toString();
                JSONObject allOfTheJSON = new JSONObject(longStringOfJSON);
                JSONObject subsetOfTheJSON = allOfTheJSON.getJSONObject("hits");
                JSONArray hitsList = subsetOfTheJSON.getJSONArray("hits");

                ItemList itemList = new ItemList();
                // each thing in this list should become its own User or Item
                for (int i = 0; i < hitsList.length(); i++) {
                    JSONObject thingInList = hitsList.getJSONObject(i);
                    JSONObject itemFromES = thingInList.getJSONObject("_source");
                    Item item = new Item();
                    item.setTitle(itemFromES.getString("title"));
                    item.setDescription(itemFromES.getString("description"));
                    item.setSize(itemFromES.getString("size"));
                    item.setAvailability(itemFromES.getBoolean("availability"));
                    item.setPhoto(itemFromES.getString("photo").getBytes());
                    // the following are ???
                    /*
                    item.setBids(itemFromES.get("bids"));
                    item.setOwner(itemFromES.get("owner"));
                    */
                    itemList.add(item);
                }
                return itemList;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // ugly "closing down shop" section
            if (connection != null)
                connection.disconnect();
            try {
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
        protected void onPostExecute(ItemList items) {
            super.onPostExecute(items);
            if (items != null) {
                adapter = new CustomAdapter(context, items.getItemList());
                itemsListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                ItemController.setItemList(items);
                Toast.makeText(context, "Search Finished!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Sorry, nothing here.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    // used in SignUpActivity
    public static class elasticAddUser extends AsyncTask<User, String, String> {

        User user = UserController.getUser();
        BufferedWriter writer;

        @Override
        protected String doInBackground(User... params) {

            HttpURLConnection connection = null;
            URL url;

            try {
                url = new URL("http://cmput301.softwareprocess.es:8080/cmput301w16t16/User/");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                OutputStream stream = new BufferedOutputStream(connection.getOutputStream());

                // JSON assist from Mar-13-2016 from http://stackoverflow.com/questions/18983185/how-to-create-correct-jsonarray-in-java-using-jsonobject
                JSONObject jo = new JSONObject();
                jo.put("username", user.getName());
                jo.put("emailAddress", user.getEmailAddress());
                jo.put("phoneNumber", user.getPhoneNumber());
                jo.put("photo", user.getPhoto());

                /////////////////////////////////////////////////////////////////////////////////////////this section only applies if Users have Items
//                // array containing the items in itemsBorrowing
//                // will all be separate when getting them back to they will have to be put
//                // into an array and then set as the user's items borrowing
//                JSONArray ib = new JSONArray();
//                for(Item item: user.getItemsBorrowing()) {
//                    // this should probably be serialized to JSON?
//                    ib.put(item);
//                }
//                jo.put("itemsBorrowing", ib);
//
//                // array containing the items in itemsRenting
//                // same notes as with items borrowing
//                JSONArray ir = new JSONArray();
//                for(Item item: user.getItemsRenting()) {
//                    ir.put(item);
//                }
//                jo.put("itemsRenting", ir);

                writer = new BufferedWriter(new OutputStreamWriter(stream));
                writer.write(jo.toString());
                writer.flush();
                writer.close();
                stream.close();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String output;
                JSONObject ESResponse;
                while ((output = br.readLine()) != null) {
                    Log.e("website returned", output);
                    ESResponse = new JSONObject(output);
                    if (ESResponse.getString("_id") != null) {
                        user.setID(ESResponse.getString("_id"));
                    }
                }
                Log.e("user ID", user.getID());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();

            return null;
        }

    }

    // Used in NewListingActivity
    public static class elasticAddItem extends AsyncTask<Item, String, String> {

        Item item;
        BufferedWriter writer;

        @Override
        protected String doInBackground(Item... params) {

            item = params[0];
            HttpURLConnection connection = null;
            URL url;

            try {
                url = new URL("http://cmput301.softwareprocess.es:8080/cmput301w16t16/Item/");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                OutputStream stream = new BufferedOutputStream(connection.getOutputStream());

                JSONObject jo = new JSONObject();
                jo.put("title", item.getTitle());
                jo.put("description", item.getDescription());
                jo.put("size", item.getSize());
                jo.put("availability", item.getAvailability());
                jo.put("photo", item.getPhoto());
                jo.put("owner", item.getOwner());
                // this last one probably won't work the same
                jo.put("bids", item.getBids());

                writer = new BufferedWriter(new OutputStreamWriter(stream));
                writer.write(jo.toString());
                writer.flush();
                writer.close();
                stream.close();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String output;
                JSONObject ESResponse;
                while ((output = br.readLine()) != null) {
                    Log.e("website returned", output);
                    ESResponse = new JSONObject(output);
                    if (ESResponse.getString("_id") != null) {
                        item.setID(ESResponse.getString("_id"));
                    }
                }
                Log.e("item ID", item.getID());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();

            return null;
        }
    }


    // used in MyItemActivity
    public static class elasticDeleteItem extends AsyncTask<Item, String, Void> {

        @Override
        protected Void doInBackground(Item... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url;
            Item item = params[0];

            try {
                String urlText = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/Item/" + item.getID();
                url = new URL(urlText);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();

            return null;

        }
    }
    //much copied from existing elasticAddItem class here
    public static class elasticUpdateItem extends AsyncTask<Item, String, String> {

        Item item;
        BufferedWriter writer;

        @Override
        protected String doInBackground(Item... params) {

            item = params[0];
            HttpURLConnection connection = null;
            URL url;

            try {
                url = new URL("http://cmput301.softwareprocess.es:8080/cmput301w16t16/Item/" + item.getID());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                OutputStream stream = new BufferedOutputStream(connection.getOutputStream());

                JSONObject jo = new JSONObject();
                jo.put("_id", item.getID());
                jo.put("title", item.getTitle());
                jo.put("description", item.getDescription());
                jo.put("size", item.getSize());
                jo.put("availability", item.getAvailability());
                jo.put("photo", item.getPhoto());
                jo.put("owner", item.getOwner());
                // this last one probably won't work the same
                jo.put("bids", item.getBids());

                writer = new BufferedWriter(new OutputStreamWriter(stream));
                writer.write(jo.toString());
                writer.flush();
                writer.close();
                stream.close();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String output;
                JSONObject ESResponse;
                while ((output = br.readLine()) != null) {
                    Log.e("website returned", output);
                    ESResponse = new JSONObject(output);
                    if (ESResponse.getString("_id") != null) {
                        item.setID(ESResponse.getString("_id"));
                    }
                }
                Log.e("item ID", item.getID());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();

            return null;
        }
    }

    public static class elasticGetUserByID extends AsyncTask<Item, String, User> {

        @Override
        protected User doInBackground(Item... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url;
            Item item = params[0];
            User user = null;

            try {
                String urlText = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/User/" + item.getOwnerID();
                url = new URL(urlText);
                connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String longStringOfJSON = buffer.toString();
                JSONObject allOfTheJSON = new JSONObject(longStringOfJSON);
                JSONObject userFromES = allOfTheJSON.getJSONObject("_source");

                user = new User();
                user.setName(userFromES.getString("username"));
                user.setEmailAddress(userFromES.getString("emailAddress"));
                user.setPhoneNumber(userFromES.getString("phoneNumber"));
                user.setPhoto(userFromES.getString("photo").getBytes());


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();

            return user;
        }
    }


    // PROBLEM:
    // this should NOT be Asynchronous (the result is needed immediately)...
    // used in SignInActivity (and sign up activity to check if username is taken)
    public static class elasticGetUserByName extends AsyncTask<String, String, User> {

        @Override
        protected User doInBackground(String... params) {
            return null;
        }
    }

}