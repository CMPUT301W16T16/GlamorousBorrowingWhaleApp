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
    ArrayList<String> myItemsArray;
    String[] myItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_items);
        //taken from http://stackoverflow.com/questions/3438276/change-title-bar-text-in-android March12,2016
        setTitle("My Items");
        myItemsView = (ListView) findViewById(R.id.myItemsListView);

        if (NetworkUtil.getConnectivityStatus(this) == 1) {
            // here we will have to make myItems into actual items instead of IDs
            // this is a pretty lame way to do it
            myItemsArray = user.getMyItems();
            //TODO: can stop converting the items list into String[] once that's it's actual type
            myItemsList = new String[myItemsArray.size()];
            myItemsList = myItemsArray.toArray(myItemsList);
            ItemController.getItemsByIDElasticSearch(myItemsList);

            // myItems contains actual items, not IDs
            myItems = ItemController.getItemList().getItemList();
            adapter = new CustomSearchResultsAdapter(this, myItems);
            myItemsView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // changed this from MyItemActivity.class to NewListingActivity.class
                Intent intent = new Intent(view.getContext(), NewListingActivity.class);
                startActivity(intent);
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
    // this seems like a fairly shady way to update the adapter but it's the only way I could
    // seem get it to work
    //TODO: improve the way the view/adapter is updated here
    @Override
    protected void onResume() {
        super.onResume();
        myItemsArray = user.getMyItems();
        //TODO: can stop converting the items list into String[] once that's it's actual type
        myItemsList = new String[myItemsArray.size()];
        myItemsList = myItemsArray.toArray(myItemsList);
        ItemController.getItemsByIDElasticSearch(myItemsList);

        myItems = ItemController.getItemList().getItemList();
        adapter.notifyDataSetChanged();
        /*
        // checking that there is an item to add to the adapter, and that it belongs to the current user
        if (ItemController.getItem() != null
                && ItemController.getItem().getOwnerID().equals(UserController.getUser().getID())
                && !myItems.contains(ItemController.getItem())) {
            myItems.add(ItemController.getItem());
            adapter.notifyDataSetChanged();
        }
        */
    }
}