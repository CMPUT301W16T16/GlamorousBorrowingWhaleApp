package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.net.Network;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This activity displays to the user all the items that match their entered
 * search criteria (by using elastic search). A user can then select an item
 * and bid on it if they wish, this action also displays the owners contact
 * information.
 * @author adam, andrew, erin, laura, martina
 */

public class SearchResultsActivity extends AppCompatActivity {
    ListView itemsListView;
    SearchView searchView;
    ItemList items = new ItemList();
    String query;
    User owner;
    public CustomSearchResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        //get the action bar object so we can customize it using the support libraries
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Search Results: All");
        actionBar.setHomeButtonEnabled(true);
        itemsListView = (ListView) findViewById(R.id.myItemsListView);
        searchView = (SearchView) findViewById(R.id.searchView);

        if (NetworkUtil.getConnectivityStatus(this) == 1) {
            //query will always be null here, so when this activity is created and we have
            //network access, the search view will always be first populated with all the items
            //in elasticsearch.
            
            //ElasticSearch has its own null case to take care of, so we just pass whatever as the query.
            ItemController.getItemsElasticSearch(itemsListView, query, adapter, getApplicationContext());

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //If a user presses "enter" on the keyboard it submits whatever is in the
                    //field as long as it is not "" (searchView handles "" and null in its own way
                    if (adapter != null) {
                        //if the adapter has stuff going on, lets reset the view to prep for a new
                        //set of data coming from the new search
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                    }
                    //Getting search from elasticsearch using the query
                    ItemController.getItemsElasticSearch(itemsListView, query, adapter, getApplicationContext());
                    //Just setting some fun dynamic action bar action
                    if (!query.equals("")) {
                        actionBar.setTitle("Search Results: " + query);
                    } else {
                        actionBar.setTitle("Search Results: All");
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //If a user enters text in the searchView the view auto submits the
                    //text to the below. Otherwise same as above.
                    if (adapter != null) {
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                    }
                    ItemController.getItemsElasticSearch(itemsListView, query, adapter, getApplicationContext());
                    if (!query.equals("")) {
                        actionBar.setTitle("Search Results: " + query);
                    } else {
                        actionBar.setTitle("Search Results: All");
                    }
                    return false;
                }
            });

            itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Item item = ItemController.getItemList().getItemList().get(position);
                    UserController.getUserByIDElasticSearch(item.getOwnerID());
                    owner = UserController.getSecondaryUser();
                    ItemController.setItem((Item) parent.getAdapter().getItem(position));
                    Intent intent = new Intent(view.getContext(), TheirItemActivity.class);
                    startActivity(intent);
                    //android says memory leak error here.
                }
            });

        } else {
            Toast.makeText(this, "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                goToHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToHome() {
        Intent i = new Intent(SearchResultsActivity.this, MyProfileViewActivity.class);
        startActivity(i);
    }
}
