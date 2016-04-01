package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Martina on 16-03-10.
 * Holds the current + another user so it can be referenced throughout the app
 * @author martina
 */

//Is the controller for the user. Persistent throughout the user session.
public class UserController {
    private static User user;
    // I created this to be able to use UserController with other users and pass them around like
    // we use ItemController. This may not be the best place for it though
    private static User secondaryUser;

    public UserController(User user) {
        UserController.user = user;
    }

    public static User getUser() {
        return UserController.user;
    }

    public static void setUser(User user) {
        UserController.user = user;
    }

    public static User getSecondaryUser() {
        return secondaryUser;
    }

    public static void setSecondaryUser(User secondaryUser) {
        UserController.secondaryUser = secondaryUser;
    }

    // checks given password against secondary user's password since secondary user
    // is returned from elastic search
    public static boolean checkPassword(String password) {
        return secondaryUser.getPassword().equals(password);
    }

    // push items that were defined offline to ElasticSearch
    public static void pushOfflineItems() {
        for (Item item: user.getOfflineItems()) {
            ItemController.addItemElasticSearch(item);

            String itemID = item.getID();
            user.addMyItem(itemID);
            UserController.updateUserElasticSearch(user);

            // removing the item from offline items
            //TODO: check to make sure item was successfully added first
            user.removeOfflineItem(item);
        }
    }

    public static void addUserElasticSearch(User latestUser) {
        try {
            new ElasticSearch.elasticAddUser().execute(latestUser).get(1, TimeUnit.DAYS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Log.d("TEST", "problem occurred while adding user to elastic search");
            e.printStackTrace();
        }
    }

    // hand this method a user and it will update that user's data on Elastic Search
    public static void updateUserElasticSearch(User user) {
        try {
            new ElasticSearch.elasticDeleteUser().execute(user).get(1, TimeUnit.DAYS);
            new ElasticSearch.elasticAddUser().execute(user).get(1, TimeUnit.DAYS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static User getUserByIDElasticSearch(String user_id) {
        try {
            new ElasticSearch.elasticGetUserByID().execute(user_id).get(1, TimeUnit.DAYS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return UserController.getSecondaryUser();
    }
}
