package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
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
 * they can add a new piece of equipment. If they swipe right they are taken to
 * IncomingBidsActivity (tbi).
 * @author adam, andrew, erin, laura, martina
 * @see MyItemActivity
 * @see NewListingActivity
 * @see IncomingBidsActivity
 */

//TODO review lifecycle code
public class MyItemsActivity extends AppCompatActivity {
    //ListView vaguely follows in lab example LonelyTwitter
    //https://github.com/AdamGualberta/lonelyTwitter/blob/tuesday/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java
    //lol that's my github oh well.
    private ItemList myItemsList;
    private ArrayList<Item> myItems;
    private ArrayAdapter<Item> adapter;
    private User user;

    public ArrayAdapter<Item> getAdapter() {
        return adapter;
        //getAdapter probably never used but that's fine.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_items);
        ListView myItemsView;
        myItemsView = (ListView) findViewById(R.id.myItemsListView);

        user = UserController.getUser();
        myItemsList = user.getItemsRenting();
        //a different class should probably handle this part - say when a new user is created
        //there is a whole bunch of stuff that is initiated. Anyways, the reason for this next
        //part of code is to catch the nullpointerexception without actually firing the exception.
        //If the user has no items at all, the class does not auto-intialize a ItemsList.
        //Naturally, this throws a null if one tries to access or add or do stuff with it.
        if (myItemsList == null) {
            Item item = new Item();
            //Create a "First Item" to get the ItemList going.

            item.setAvailability(false);
            item.setDescription("This is your first Thing! You can delete it of course.");
            item.setOwner(user);
            item.setSize("Medium");
            item.setTitle("First Thing!");
            //Actually initialize the itemList.
            myItemsList = new ItemList();
            myItemsList.add(item);
            //diiirrrtttyy programming right here - will have to fix. Just as a proof of concept.
            user.setItemsRenting(myItemsList);
            //redundant but for "testing"
            myItemsList = user.getItemsRenting();
            Toast.makeText(MyItemsActivity.this, "First Thing Created!", Toast.LENGTH_SHORT).show();
            //finally, set the variable to what we want.
            myItems = myItemsList.getItemList();
            //"initialize" the itemcontroller
            ItemController.setItem(item);
            //Now that the item is created and "in use", lets create a bidlist to use with it!
            BidList bids = new BidList();
            setFirstBids(bids, item);
        } else {
            myItems = myItemsList.getItemList();
        }

        //finally creating that adapter to use with the myItems
        adapter = new ArrayAdapter<Item>(this, R.layout.list_item, myItems);
        //See Item.java for how this can work (hint - the bottom)
        myItemsView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //Create that fancy floating button using built-in android stuff.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This sends the user to the NewListingActivity. No need to worry about
                //the ItemController yet - that is dealt with in the NewListingActivity class.
                Intent intent = new Intent(view.getContext(), NewListingActivity.class);
                startActivity(intent);
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////////// for user popup
        myItemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProfileDialog profile = new ProfileDialog(MyItemsActivity.this, getAdapter().getItem(position));
                profile.show();
            }
        });

        // I have no clue if the following is proper, it was Android Studio doing that autocomplete
        // thing.
        myItemsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //This chunk is pretty neat. The ViewAdapter makes note of what is clicked in
                //the dynamic list which is used to get the corresponding Item at that "position".
                //Again leveraging the ItemController, we can set that Item as the current Item
                //and send the user to the MyItemActivity with the current Item (the one
                //that they selected!).
                ItemController.setItem((Item) parent.getAdapter().getItem(position));
                Intent intent = new Intent(view.getContext(), MyItemActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //needs error correction
        //refreshes (I think) the adapter so the view is refreshed. (Think press back button after
        //editing something somewhere)
        myItemsList = user.getItemsRenting();
        myItems = myItemsList.getItemList();
        adapter.notifyDataSetChanged();
    }

    //Sets the "first item" with a bid list for fun.
    private void setFirstBids(BidList bids, Item item) {
        item.setBids(bids);
    }
}
