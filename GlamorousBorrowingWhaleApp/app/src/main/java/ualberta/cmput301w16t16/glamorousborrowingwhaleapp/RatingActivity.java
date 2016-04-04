package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class is used to see ratings/comments on a given item.
 * It does not matter who the owner is. (someone else or the user)
 * You get to it by pressing the comment buttons to the right of star
 * ratings.
 * @author adam, andrew, erin, laura, martina
 */

public class RatingActivity extends AppCompatActivity {
    //private ItemList myItemsList;
    private ArrayList<Item> ratings;
    private ArrayAdapter<Item> adapter;
    private User user = UserController.getUser();
    // Probably never used
    public ArrayAdapter<Item> getAdapter() {
        return adapter;
    }
    ListView ratingsView;
    ArrayList<String> ratingsArray;
    String[] ratingsList;

    //TODO: this code below is incomplete and doesn't populate the listview, crashes when try to make a rating
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ratings);
        //taken from http://stackoverflow.com/questions/3438276/change-title-bar-text-in-android March12,2016
        setTitle("Ratings and Comments");
        ratingsView = (ListView) findViewById(R.id.ratingsListView);

        if (NetworkUtil.getConnectivityStatus(this) == 1) {

            //!!

        } else {
            Toast.makeText(this, "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Refreshes the adapter so the view is refreshed
     */
    //TODO: improve the way the view/adapter is updated here
    @Override
    protected void onResume() {
        super.onResume();
        //  put things in here
    }
}