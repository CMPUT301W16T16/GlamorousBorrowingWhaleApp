package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;

/**
 * Created by erin on 11/03/16.
 * This class performs elastic search methods such as saving a user, saving an
 * item and searching for things. We used HttpURLConnection.
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

            } catch (IOException | JSONException e) {
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
                adapter = new CustomSearchResultsAdapter(context, items.getItemList());
                itemsListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Toast.makeText(context, "Search Finished!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Sorry, nothing here.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    // Used in IncomingBids Activity
    public static class elasticGetIncomingBids extends AsyncTask<ListView, String, BidList> {

        ListView incomingBidsListView;
        ArrayAdapter<Bid> adapter;
        Context context;
        User user = UserController.getUser();

        //////////////////////////////////////////////////////////////////////////////////////////////// fake bid data
// TODO get rid of this when we don't need fake bid data any more
//        User owner = new User("billy", "billy@abc.com", "123");
//        Item testItem = new Item();
//        testItem.setTitle("Snowshoes");
//        testItem.setAvailability(true);
//        testItem.setOwner(owner);
//        testItem.setDescription("Nice pair of snowshoes. Worth about $20.00 to borrow");
//        BidList testBidList = new BidList();
//        Bid testBid = new Bid(testItem, 15.00);
//        testBidList.addBid(testBid);
//        testItem.setBids(testBidList);
//        bidsToShow.addBid(testBid);
//        Item testItem2 = new Item();
//        testItem2.setTitle("Golf Clubs - don't click this one (not enough data to display)");
//        testItem2.setAvailability(true);
//        Bid testBid2 = new Bid(testItem2, 30.00);
//        bidsToShow.addBid(testBid2);
        ////////////////////////////////////////////////////////////////////////////////////////////// end fake bid data

        public elasticGetIncomingBids(Context context){
            this.context = context;
        }

        @Override
        protected BidList doInBackground(ListView... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url;
            incomingBidsListView = params[0];
            String urlString = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/Item/";

            BidList bidsToShow = new BidList();
            ArrayList<String> itemIDList = user.getMyItems();
            if (itemIDList != null) {
                for (String ID : itemIDList) {
                    urlString = urlString + ID;
                    try {
                        url = new URL(urlString);
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
                        JSONObject itemFromES = allOfTheJSON.getJSONObject("_source");

                        JSONArray bidList = itemFromES.getJSONArray("bids");

                        for (int i = 0; i < bidList.length(); i++) {
                            JSONObject currentBid = bidList.getJSONObject(i);
                            Bid tempBid = new Bid();
                            tempBid.setOwnerID(currentBid.getString("ownerID"));
                            tempBid.setRenterID(currentBid.getString("renterID"));
                            tempBid.setItemID(currentBid.getString("itemID"));
                            tempBid.setBidAmount(currentBid.getDouble("bidAmount"));
                            tempBid.setIsAccepted(currentBid.getBoolean("isAccepted"));
                            bidsToShow.addBid(tempBid);
                        }

                        return bidsToShow;

                    } catch (IOException | JSONException e) {
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
                }
            }
            // the returned item is passed on to onPostExecute as "result"
            return null;
        }

        @Override
        protected void onPostExecute(BidList bids) {
            super.onPostExecute(bids);
            if (bids != null) {
                adapter = new CustomIncomingBidsAdapter(context, bids.getBids());
                incomingBidsListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Toast.makeText(context, "Search finished!", Toast.LENGTH_SHORT).show();
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

                // the following two in AddUser will never trigger... move them somewhere logical
                if (user.getMyItems() != null) {
                    JSONArray myItemsIDArray = new JSONArray();
                    for (String itemID : user.getMyItems()) {
                        myItemsIDArray.put(itemID);
                    }
                    jo.put("myItems", myItemsIDArray);
                }

                if (user.getItemsBorrowed() != null) {
                    JSONArray itemsBorrowedIDArray = new JSONArray();
                    for (String itemID : user.getItemsBorrowed()) {
                        itemsBorrowedIDArray.put(itemID);
                    }
                    jo.put("itemsBorrowed", itemsBorrowedIDArray);
                }

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

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();

            return null;
        }

    }


    // Used in NewListingActivity
    public static class elasticAddItem extends AsyncTask<Item, String, String> {

        User user;
        Item item;
        BufferedWriter writer;

        @Override
        protected String doInBackground(Item... params) {

            user = UserController.getUser();
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
                jo.put("owner", item.getOwnerID());
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
                        user.addMyItem(ESResponse.getString("_id"));
                    }
                }
                Log.e("item ID", item.getID());

            } catch (IOException | JSONException e) {
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
                jo.put("owner", item.getOwnerID());
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

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();

            return null;
        }
    }


//    public static class elasticGetUserByID extends AsyncTask<Item, String, User> {
//
//        @Override
//        protected User doInBackground(Item... params) {
//            HttpURLConnection connection = null;
//            BufferedReader reader = null;
//            URL url;
//            Item item = params[0];
//            User user = null;
//
//            try {
//                String urlText = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/User/" + item.getOwnerID();
//                url = new URL(urlText);
//                connection = (HttpURLConnection) url.openConnection();
//                InputStream stream = connection.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(stream));
//                StringBuilder buffer = new StringBuilder();
//                String line = "";
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line);
//                }
//                String longStringOfJSON = buffer.toString();
//                JSONObject allOfTheJSON = new JSONObject(longStringOfJSON);
//                JSONObject userFromES = allOfTheJSON.getJSONObject("_source");
//
//                user = new User(null, null, null);
//                user.setName(userFromES.getString("username"));
//                user.setEmailAddress(userFromES.getString("emailAddress"));
//                user.setPhoneNumber(userFromES.getString("phoneNumber"));
//                user.setPhoto(userFromES.getString("photo").getBytes());
//
//
//            } catch (IOException | JSONException e) {
//                e.printStackTrace();
//            }
//
//            if (connection != null)
//                connection.disconnect();
//
//            return user;
//        }
//    }


    public static class elasticGetUserByName extends AsyncTask<Activity, String, User> {

        Activity activity;
        Context context;

        public elasticGetUserByName(Context context){
            this.context = context;
        }

        @Override
        protected User doInBackground(Activity... params) {
            activity = params[0];

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url;
            User user = null;

            try {
                String urlText = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/User/" + UserController.getUser().getName();
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

                user = UserController.getUser();
                user.setName(userFromES.getString("username"));
                user.setEmailAddress(userFromES.getString("emailAddress"));
                user.setPhoneNumber(userFromES.getString("phoneNumber"));
                user.setPhoto(userFromES.getString("photo").getBytes());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();

            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if (user != null) {
                Intent intent = new Intent(activity, MyProfileViewActivity.class);
                intent.putExtra("USERNAME", user);
                activity.startActivity(intent);
            } else {
                Toast.makeText(context, "User search returned null", Toast.LENGTH_SHORT).show();
            }
        }
    }
}