package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This activity shows the bids an owner has received on their items. They then
 * have the option to reject or accept the bid as well as to see the item that
 * has been bid on (takes them to MyItemActivity.
 * @author adam, andrew, erin, laura, martina
 * @see MyItemActivity
 */

public class IncomingBidsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_bids);
    }
}
