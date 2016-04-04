package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import java.util.ArrayList;

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
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Your Items");
        actionBar.setHomeButtonEnabled(true);

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

    //setting up the action bar icons
    //taken from http://www.androidhive.info/2013/11/android-working-with-action-bar/
    // Apr3/16
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_search_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.home:
                goToHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToHome() {
        Intent i = new Intent(MyItemsActivity.this, MyProfileViewActivity.class);
        startActivity(i);
    }

    /**
     * Refreshes the adapter so the view is refreshed
     */
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
        adapter = new CustomSearchResultsAdapter(this, myItems);
        myItemsView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}