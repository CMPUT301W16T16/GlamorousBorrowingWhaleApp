package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by andrew on 14/03/16.
 * This page shows the bids the user has recieved on all their items in a list
 * format.
 * @author adam, andrew, erin, laura, martina
*/

public class IncomingBidsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_bids);
        setTitle("Incoming Bids");
    }

}
