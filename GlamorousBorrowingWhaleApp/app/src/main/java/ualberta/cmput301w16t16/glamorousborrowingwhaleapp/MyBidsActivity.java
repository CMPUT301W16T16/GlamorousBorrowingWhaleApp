package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;


import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


/**
 * This class shows the user a list of their items that they have bid on and
 * whether the bid has been accepted, rejected or is still pending. If they
 * click an item in the listView they can then view that piece of equipment
 * again.
 * @author adam, andrew, laura, martina
 */

public class MyBidsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bids);
        setTitle("My Bids");

    }
}

