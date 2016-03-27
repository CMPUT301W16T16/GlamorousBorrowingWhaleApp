package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.sql.Time;
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

    // hand this method a user and it will update that user's data on Elastic Search
    public static void updateUserElasticSearch(User user) {

        try {
            new ElasticSearch.elasticDeleteUser().execute(user).get(1, TimeUnit.DAYS);
            new ElasticSearch.elasticAddUser().execute(user).get(1, TimeUnit.DAYS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    // referenced Mar-26-2016 from http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences/
    public static void setLoggedIn(Context context, boolean logged_in) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        if (logged_in) {
            editor.putString("user_id", user.getID()); // Storing boolean - true/false
        } else {
            editor.putString("user_id", null); // Storing boolean - true/false
        }
        editor.apply();

        Log.d("TEST", "setting the user as logged in");
    }

    public static String getLoggedIn(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

        return pref.getString("user_id", null);
    }
}
