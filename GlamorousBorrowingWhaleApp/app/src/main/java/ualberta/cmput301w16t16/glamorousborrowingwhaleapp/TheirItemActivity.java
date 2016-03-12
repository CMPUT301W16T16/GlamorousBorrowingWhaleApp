package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This class is used to display someone elses entry and the owner information
 * when a borrower selects an item in the list of SearchResultsActivity.
 * @author adam, andrew, erin, laura, martina
 * @see SearchResultsActivity
 */

public class TheirItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_their_item);
    }
}
