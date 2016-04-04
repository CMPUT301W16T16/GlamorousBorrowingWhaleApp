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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by erin on 11/03/16.
 * This class performs elastic search methods such as saving a user, saving an
 * item and searching for things. We used HttpURLConnection.
 * @author adam, andrew, erin, laura, martina
 */

public class ElasticSearch extends Application {

    // retrieves all items on the ElasticSearch list, adding them to the returned ItemList.
    // Used in SearchResultsActivity.
    public static class elasticGetItems extends AsyncTask<ListView, String, ItemList> {
        String query;
        TextView tv;
        ListView itemsListView;
        CustomSearchResultsAdapter adapter;
        Context context;

        public elasticGetItems(String query, CustomSearchResultsAdapter adapter, Context context) {
            this.query = query;
            this.adapter = adapter;
            this.context = context;
        }

        @Override
        protected ItemList doInBackground(ListView... params) {

            ItemList emptyItemList = new ItemList();
            ItemController.setItemList(emptyItemList);

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url;
            itemsListView = params[0];
            if (query != null) {
                query = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/Item/_search?q=title:"+
                        "*" + query + "*";
            } else {
                query = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/Item/_search?";
            }

            try {
                url = new URL(query);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            try {
                //url = new URL("http://cmput301.softwareprocess.es:8080/cmput301w16t16/Item/_search?&size=10");
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

                    // filling in the information for the item
                    Item item = new Item();
                    item.setID(thingInList.getString("_id"));
                    item.setTitle(itemFromES.getString("title"));
                    item.setDescription(itemFromES.getString("description"));
                    item.setSize(itemFromES.getString("size"));
                    item.setAvailability(itemFromES.getBoolean("availability"));
                    item.setPhoto(itemFromES.getString("photo").getBytes());
                    item.setOwnerID(itemFromES.getString("owner"));
                    item.setSport(itemFromES.getString("sport"));

                    // in a try catch for now as not all of our data has these
                    try {
                        item.setRenterID(itemFromES.getString("renter"));
                    } catch (JSONException e) {
                        e.printStackTrace() ;
                        Log.v("JSON error", "renter missing");
                    }
                    try {
                        item.setLatitude(itemFromES.getDouble("latitude"));
                        item.setLongitude(itemFromES.getDouble("longitude"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.v("JSON error", "map data missing");
                    }


                    BidList bids = new BidList();
                    JSONArray bidList = itemFromES.getJSONArray("bids");
                    for (int j = 0; j < bidList.length(); j++) {
                        JSONObject currentBid = bidList.getJSONObject(j);
                        Bid tempBid = new Bid();
                        tempBid.setOwnerID(currentBid.getString("ownerID"));
                        tempBid.setRenterID(currentBid.getString("renterID"));
                        tempBid.setItemID(currentBid.getString("itemID"));
                        tempBid.setBidAmount(currentBid.getDouble("bidAmount"));
                        tempBid.setIsAccepted(currentBid.getBoolean("isAccepted"));
                        bids.addBid(tempBid);
                    }

                    item.setBids(bids);
                    itemList.add(item);
                }
                ItemController.setItemList(itemList);
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
        protected void onPostExecute(ItemList itemList) {
            try {
                this.adapter = new CustomSearchResultsAdapter(itemsListView.getContext(),
                        ItemController.getItemList().getItemList());
                itemsListView.setAdapter(this.adapter);
                this.adapter.notifyDataSetChanged();
            } catch (NullPointerException e) {
                Toast.makeText(context, "Search Failed!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    // used in SignUpActivity and UserController.updateUserElasticSearch
    public static class elasticAddUser extends AsyncTask<User, String, String> {

        BufferedWriter writer;

        @Override
        protected String doInBackground(User... params) {

            HttpURLConnection connection = null;
            URL url;
            User user = params[0];

            try {
                String urlString = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/User/" + user.getUsername();
                url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                OutputStream stream = new BufferedOutputStream(connection.getOutputStream());

                // JSON assist from Mar-13-2016 from http://stackoverflow.com/questions/18983185/how-to-create-correct-jsonarray-in-java-using-jsonobject
                JSONObject jo = new JSONObject();
                jo.put("username", user.getUsername());
                jo.put("emailAddress", user.getEmailAddress());
                jo.put("phoneNumber", user.getPhoneNumber());
                jo.put("photo", user.getPhoto());
                jo.put("password", user.getPassword());
                jo.put("notification", user.getNotification());

                // adding items even though it is an empty array so you can still sign in even if you have never created any items
                JSONArray myItemsIDArray = new JSONArray();
                for (String itemID : user.getMyItems()) {
                    myItemsIDArray.put(itemID);
                }
                jo.put("myItems", myItemsIDArray);


                JSONArray itemsBorrowedIDArray = new JSONArray();
                for (String itemID : user.getItemsBorrowed()) {
                    itemsBorrowedIDArray.put(itemID);
                }
                jo.put("itemsBorrowed", itemsBorrowedIDArray);

                JSONArray itemsBidOnIDArray = new JSONArray();
                for (String itemID : user.getItemsBidOn()) {
                    itemsBidOnIDArray.put(itemID);
                }
                jo.put("itemsBidOn", itemsBidOnIDArray);

                // set up ratings here, i think this works?
                /*JSONArray itemsToRateArray = new JSONArray();
                for (Item item : user.getPendingRatings()) {
                    itemsToRateArray.put(item);
                }
                jo.put("itemsToRate", itemsToRateArray);*/

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
                        if (ESResponse.getString("_id") != user.getUsername()) {
                            user.setID(ESResponse.getString("_id"));
                        }
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

    // Used in NewListingActivity and ItemController.updateItemElasticSearch
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
                jo.put("renter", item.getRenterID());
                jo.put("sport", item.getSport());
                jo.put("latitude", item.getLatitude());
                jo.put("longitude", item.getLongitude());

                JSONArray ja = new JSONArray();
                for (int i = 0; i < item.getBids().getBids().size(); i++) {
                    Bid bid = item.getBids().getBids().get(i);
                    JSONObject joBid = new JSONObject();
                    joBid.put("isAccepted", bid.getIsAccepted());
                    joBid.put("ownerID", bid.getOwnerID());
                    joBid.put("renterID", bid.getRenterID());
                    joBid.put("itemID", bid.getItemID());
                    joBid.put("bidAmount", bid.getBidAmount());
                    ja.put(joBid);
                }
                jo.put("bids", ja);

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

    public static class elasticDeleteUser extends AsyncTask<User, String, Void> {

        @Override
        protected Void doInBackground(User... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url;
            User user = params[0];

            try {
                String urlText = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/User/" + user.getID();
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

    public static class elasticGetUserByID extends AsyncTask<String, String, Void> {

        String username;
        User user = new User();

        @Override
        protected Void doInBackground(String... params) {
            username = params[0];

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url;

            try {
                String urlText = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/User/" + username;
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

                user.setUsername(userFromES.getString("username"));
                user.setEmailAddress(userFromES.getString("emailAddress"));
                user.setPhoneNumber(userFromES.getString("phoneNumber"));
                user.setPhoto(userFromES.getString("photo").getBytes());
                user.setPassword(userFromES.getString("password"));
                user.setNotification(userFromES.getBoolean("notification"));
                user.setID(user.getUsername());

                // getting the item lists back from JSON and continuing to store them as lists of IDs
                ArrayList<String> myItemsIds = new ArrayList<String>();
                JSONArray myItems = userFromES.getJSONArray("myItems");
                for (int i = 0; i < myItems.length(); i++) {
                    myItemsIds.add(myItems.getString(i));
                }
                user.setMyItems(myItemsIds);

                ArrayList<String> itemsBidOnIds = new ArrayList<>();
                JSONArray itemsBidOn = userFromES.getJSONArray("itemsBidOn");
                for (int i = 0; i < itemsBidOn.length(); i++) {
                    itemsBidOnIds.add(itemsBidOn.getString(i));
                }
                user.setItemsBidOn(itemsBidOnIds);

                ArrayList<String> itemsBorrowedIds = new ArrayList<>();
                JSONArray itemsBorrowed = userFromES.getJSONArray("itemsBorrowed");
                for (int i = 0; i < itemsBorrowed.length(); i++) {
                    itemsBorrowedIds.add(itemsBorrowed.getString(i));
                }
                user.setItemsBorrowed(itemsBorrowedIds);

                // ratings here, i think this works?
                /*ArrayList<Item> itemsToRateArray = new ArrayList<>();
                JSONArray itemsToRate = userFromES.getJSONArray("itemsToRate");
                for (int i = 0; i < itemsToRate.length(); i++) {
                    itemsBorrowedIds.add(itemsToRate.getString(i));
                }
                user.setPendingRatings(itemsToRateArray);*/

                UserController.setSecondaryUser(user);

            } catch (IOException | JSONException e) {
                Log.e("ElasticSearch", "There was an error retrieving the user from ID");
                e.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();

            return null;
        }
    }

    // very similar to elasticGetItems
    // getting a list of actual item objects from their IDs and setting the current
    // itemList in ItemController as that itemList
    // have to give it a String[] of item IDs (so you can ask for borrowed, or bidded, etc
    // as long as you have the list of IDs)
    // the item lists in user are stored as ArrayList<String> right now, so once that is changed to
    // String[], this will not need to be updated
    public static class elasticGetItemsByID extends AsyncTask<String[], String, ItemList> {

        @Override
        protected ItemList doInBackground(String[]... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url;
            String urlStringBase = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/Item/";
            String urlStringComplete;
            String[] itemIDList = params[0];

            ItemList itemList = new ItemList();

            if (itemIDList != null) {
                for (String ID : itemIDList) {
                    urlStringComplete = urlStringBase + ID;
                    try {
                        url = new URL(urlStringComplete);
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

                        // getting all of the information for that item
                        Item tempItem = new Item();

                        tempItem.setTitle(itemFromES.getString("title"));
                        tempItem.setDescription(itemFromES.getString("description"));
                        tempItem.setAvailability(itemFromES.getBoolean("availability"));
                        tempItem.setPhoto(itemFromES.getString("photo").getBytes());
                        tempItem.setSize(itemFromES.getString("size"));
                        tempItem.setOwnerID(itemFromES.getString("owner"));
                        tempItem.setRenterID(itemFromES.getString("renter"));
                        tempItem.setSport(itemFromES.getString("sport"));
                        tempItem.setLatitude(itemFromES.getDouble("latitude"));
                        tempItem.setLongitude(itemFromES.getDouble("longitude"));

                        BidList bids = new BidList();
                        JSONArray bidList = itemFromES.getJSONArray("bids");
                        for (int i = 0; i < bidList.length(); i++) {
                            JSONObject currentBid = bidList.getJSONObject(i);
                            Bid tempBid = new Bid();
                            tempBid.setOwnerID(currentBid.getString("ownerID"));
                            tempBid.setRenterID(currentBid.getString("renterID"));
                            tempBid.setItemID(currentBid.getString("itemID"));
                            tempBid.setBidAmount(currentBid.getDouble("bidAmount"));
                            tempBid.setIsAccepted(currentBid.getBoolean("isAccepted"));

                            bids.addBid(tempBid);
                        }

                        tempItem.setBids(bids);
                        tempItem.setID(ID);
                        itemList.add(tempItem);


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
                ItemController.setItemList(itemList);
            }
            // the returned item is passed on to onPostExecute as "result"

            return null;
        }
    }

    // Used in IncomingBids Activity
    public static class elasticGetIncomingBids extends AsyncTask<ListView, String, BidList> {

        User user = UserController.getUser();

        @Override
        protected BidList doInBackground(ListView... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url;
            String urlStringBase = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/Item/";
            String urlStringComplete;

            BidList bidsToShow = new BidList();
            ArrayList<String> itemIDList = user.getMyItems();
            if (itemIDList != null) {
                for (String ID : itemIDList) {
                    urlStringComplete = urlStringBase + ID;
                    try {
                        url = new URL(urlStringComplete);
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

                        BidController.setBidList(bidsToShow);
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
            return null;
        }
    }





    //////////////////////////////////////////////////////////////////////////////////  Not In Use:

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
                jo.put("renter", item.getRenterID());
                jo.put("sport", item.getSport());

                JSONArray ja = new JSONArray();
                for (int i = 0; i < item.getBids().getBids().size(); i++) {
                    Bid bid = item.getBids().getBids().get(i);
                    JSONObject joBid = new JSONObject();
                    joBid.put("isAccepted", bid.getIsAccepted());
                    joBid.put("ownerID", bid.getOwnerID());
                    joBid.put("renterID", bid.getRenterID());
                    joBid.put("itemID", bid.getItemID());
                    joBid.put("bidAmount", bid.getBidAmount());
                    ja.put(joBid);
                }
                jo.put("bids", ja);

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
                    item.setID(ESResponse.getString("_id"));
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

    // using elasticSearchDeleteUser and elasticSearchAddUser instead
    // like we may as well delete this it's pretty useless - erin
    public static class elasticUpdateUser extends AsyncTask<Void, String, String> {

        User user = UserController.getUser();
        BufferedWriter writer;

        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection connection = null;
            URL url;

            try {
                String urlString = "http://cmput301.softwareprocess.es:8080/cmput301w16t16/User/" + user.getUsername();
                url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                OutputStream stream = new BufferedOutputStream(connection.getOutputStream());

                // JSON assist from Mar-13-2016 from http://stackoverflow.com/questions/18983185/how-to-create-correct-jsonarray-in-java-using-jsonobject
                JSONObject jo = new JSONObject();
                jo.put("username", user.getUsername());
                jo.put("emailAddress", user.getEmailAddress());
                jo.put("phoneNumber", user.getPhoneNumber());
                jo.put("photo", user.getPhoto());
                jo.put("password", user.getPassword());
                jo.put("notification", user.getNotification());

                // adding items even though it is an empty array so you can still sign in even if
                // you have never created any items
                JSONArray myItemsIDArray = new JSONArray();
                for (String itemID : user.getMyItems()) {
                    myItemsIDArray.put(itemID);
                }
                jo.put("myItems", myItemsIDArray);

                JSONArray itemsBorrowedIDArray = new JSONArray();
                for (String itemID : user.getItemsBorrowed()) {
                    itemsBorrowedIDArray.put(itemID);
                }
                jo.put("itemsBorrowed", itemsBorrowedIDArray);

                JSONArray itemsBidOnIDArray = new JSONArray();
                for (String itemID : user.getItemsBidOn()) {
                    itemsBidOnIDArray.put(itemID);
                }
                jo.put("itemsBidOn", itemsBidOnIDArray);

                // ratings here, i think this works?
                /*JSONArray itemsToRateArray = new JSONArray();
                for (Item itemID : user.getPendingRatings()) {
                    itemsToRateArray.put(itemID);
                }
                jo.put("itemsToRate", itemsToRateArray);*/

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
                }
                Log.e("user ID ", user.getID());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();

            return null;
        }

    }

}