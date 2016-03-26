package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This activity allows a user to see their items that are available to be
 * rented out (items NOT bidded or borrowed). If the user longClicks any item on
 * the listView, they are brought to the MyItemActivity. If the user selects
 * the floating action plus sign, they are taken to NewListingActivity where
 * they can add a new piece of equipment.
 * @author adam, andrew, erin, laura, martina
 * @see MyItemActivity
 * @see NewListingActivity
 */

//TODO: Review lifecycle code
public class MyItemsActivity extends AppCompatActivity {
    //private ItemList myItemsList;
    private ArrayList<Item> myItems;
    private ArrayAdapter<Item> adapter;
    private User user = UserController.getUser();
    // Probably never used
    public ArrayAdapter<Item> getAdapter() {
        return adapter;
    }
    ListView myItemsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_items);
        //taken from http://stackoverflow.com/questions/3438276/change-title-bar-text-in-android March12,2016
        setTitle("My Items");
        myItemsView = (ListView) findViewById(R.id.myItemsListView);
        ArrayList<String> myItemsArray = user.getMyItems();

        // here we will have to make myItems into actual items instead of IDs
        // this is a pretty lame way to do it
        //TODO: can stop converting the items list into String[] once that's it's actual type
        String[] myItemsList = new String[myItemsArray.size()];
        myItemsList = myItemsArray.toArray(myItemsList);
        try {
            new ElasticSearch.elasticGetItemsByID(getApplicationContext()).execute(myItemsList).get(1, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Log.e("EXCEPTION", "problem while waiting for items to be retrieved in MyItemsActivity");
            e.printStackTrace();
        }

        myItems = ItemController.getItemList().getItemList();

        adapter = new CustomSearchResultsAdapter(this, myItems);

        //TODO: Use fetch from ES
        // this doesn't currently update immediately since after an item is created, the user's list
        // of items doesn't add the id of the newest item since elasticUpdateUser isn't working right
        // at the moment
        myItemsView.setAdapter(adapter);
        //new ElasticSearch.elasticGetItems(getApplicationContext()).execute(myItemsView);
        adapter.notifyDataSetChanged();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // changed this from MyItemActivity.class to NewListingActivity.class
                Intent intent = new Intent(view.getContext(), NewListingActivity.class);
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });

        myItemsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /*
            The ViewAdapter saves the position of what is clicked in the dynamic list.
            Set that Item as the current Item and send the user to the MyItemActivity with the current Item
             */
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ItemController.setItem((Item) parent.getAdapter().getItem(position));
                Intent intent = new Intent(view.getContext(), MyItemActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    /**
     * Refreshes the adapter so the view is refreshed
     */
    @Override
    protected void onResume() {
        super.onResume();
        new ElasticSearch.elasticUpdateUser().execute();
        //TODO: Use fetch from ES
        ArrayList<String> myItemsArray = user.getMyItems();
        // here we will have to make myItems into actual items instead of IDs
        // this is a pretty lame way to do it
        //TODO: can stop converting the items list into String[] once that's it's actual type
        String[] myItemsList = new String[myItemsArray.size()];
        myItemsList = myItemsArray.toArray(myItemsList);
        new ElasticSearch.elasticGetItemsByID(getApplicationContext()).execute(myItemsList);
        myItems = ItemController.getItemList().getItemList();
        adapter.notifyDataSetChanged();
    }

    // Gonna delete this once we get elastic search working with bids
    private void setFirstBids(Item item) {
        BidList bids = new BidList();
        Bid bid1 = new Bid(item, 0.30);
        Bid bid2 = new Bid(item, 0.50);
        Bid bid3 = new Bid(item, 1.20);
        Bid bid4 = new Bid(item, 0.25);
        item.setBids(bids);
        item.addBid(bid1);
        item.addBid(bid2);
        item.addBid(bid3);
        item.addBid(bid4);
    }
}