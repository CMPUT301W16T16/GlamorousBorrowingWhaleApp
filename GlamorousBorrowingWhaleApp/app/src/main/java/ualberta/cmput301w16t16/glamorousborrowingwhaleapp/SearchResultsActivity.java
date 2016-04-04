package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.net.Network;
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

        if (NetworkUtil.getConnectivityStatus(this) == 1) {
            ItemController.getItemsElasticSearch(itemsListView);

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
}
