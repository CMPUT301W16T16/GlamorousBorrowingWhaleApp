package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    ItemList items;
    User owner;
    ArrayAdapter<Item> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        itemsListView = (ListView) findViewById(R.id.myItemsListView);
        setTitle("Search Results: All");

        // setting the time unit to wait to DAYS may not be the best decision
        try {
            new ElasticSearch.elasticGetItems(getApplicationContext()).execute(itemsListView).get(1, TimeUnit.DAYS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        items = ItemController.getItemList();
        if (items != null) {
            adapter = new CustomSearchResultsAdapter(this, items.getItemList());
            itemsListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Search Finished!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sorry, nothing here.", Toast.LENGTH_SHORT).show();
        }

        // Opens pop up for user info
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = ItemController.getItemList().getItemList().get(position);
                Log.d("TEST", "item: "+ item.getOwnerID());
                try {
                    new ElasticSearch.elasticGetUserByID(getApplicationContext()).execute(item.getOwnerID()).get(1, TimeUnit.MINUTES);
                    owner = UserController.getSecondaryUser();
                    ProfileDialog profile = new ProfileDialog(SearchResultsActivity.this, null);
                    profile.show();
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    Log.e("TEST", "there was a problem while waiting in SearchResultsActivity");
                    e.printStackTrace();
                }
                //android says memory leak error here.
            }
        });

        // Takes you to TheirItem page
        itemsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = ItemController.getItemList().getItemList().get(position);
                Log.d("TEST", "item: "+ item.getOwnerID());
                try {
                    new ElasticSearch.elasticGetUserByID(getApplicationContext()).execute(item.getOwnerID()).get(1, TimeUnit.MINUTES);
                    owner = UserController.getSecondaryUser();
                    ItemController.setItem((Item) parent.getAdapter().getItem(position));
                    Intent intent = new Intent(view.getContext(), TheirItemActivity.class);
                    startActivity(intent);
                    return false;
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    Log.e("TEST", "there was a problem while waiting in SearchResultsActivity");
                    e.printStackTrace();
                }
                return false;
            }
        });
    }
}
