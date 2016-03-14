package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.URL;

import ualberta.cmput301w16t16.glamorousborrowingwhaleapp.ElasticSearch.elasticGetItems;

/**
 * This activity displays to the user all the items that match their entered
 * search criteria (by using elastic search). A user can then select an item
 * and bid on it if they wish, this action also displays the owners contact
 * information.
 * @author adam, andrew, erin, laura, martina
 */

public class SearchResultsActivity extends AppCompatActivity {

    // TODO ES CALL: get items to populate list
    // new ElasticSearch.elasticGetItems().execute( // URL // );
    // use ElasticSearch.elasticGetItems()... somehow.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setTitle("Search Results: All");
    }

}
