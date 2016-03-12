package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This activity allows a user to see their items that are available to be
 * rented out (items NOT bidded or borrowed). If the user selects any item on
 * the listView, they are brought to the MyItemActivity. If the user selects
 * the floating action plus sign, they are taken to NewListingActivity where
 * they can add a new piece of equipment. If they swipe right they are taken to
 * IncomingBidsActivity
 * @author adam, andrew, erin, laura, martina
 * @see MyItemActivity
 * @see NewListingActivity
 * @see IncomingBidsActivity
 */

/*Where the user can see the items available for RENTING.
A listview updates via adapter on every resume.
NEEDS WORK THOUGH AS IT DOES TOO MUCH RIGHT NOW>>>>
And lifecycle work.
 */

public class MyItemsActivity extends AppCompatActivity {
    //ListView vaguely follows in lab example LonelyTwitter
    //https://github.com/AdamGualberta/lonelyTwitter/blob/tuesday/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java
    //lol that's my github oh well.
    private ItemList myItemsList;
    private ArrayList<Item> myItems;
    private ArrayAdapter<Item> adapter;
    private ListView myItemsView;
    private User user;

    public ArrayAdapter<Item> getAdapter() {
        return adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_items);

        myItemsView = (ListView) findViewById(R.id.myItemsListView);

        user = UserController.getUser();
        myItemsList = user.getItemsRenting();
        //a different class should probably handle this part - say when a new user is created
        //there is a whole bunch of stuff that is initiated. Anyways, the reason for this next
        //part of code is to catch the nullpointerexception without actually firing the exception.
        //If the user has no items at all, the class does not auto-intialize a ItemsList.
        //Naturally, this throws a null if one tries to access or add or do stuff with it.
        if (myItemsList == null) {
            Item firstItem = new Item();
            //Create a "First Item" to get the ItemList going.
            firstItem.setAvailability(false);
            firstItem.setDescription("This is your first Thing! You can delete it of course.");
            firstItem.setOwner(user);
            firstItem.setSize("Medium");
            firstItem.setTitle("First Thing!");
            //Actually initialize the itemList.
            myItemsList = new ItemList();
            myItemsList.add(firstItem);
            //diiirrrtttyy programming right here - will have to fix. Just as a proof of concept.
            user.setItemsRenting(myItemsList);
            //redundant but for "testing"
            myItemsList = user.getItemsRenting();
            Toast.makeText(MyItemsActivity.this, "First Thing Created!", Toast.LENGTH_SHORT).show();
            //finally, set the variable to what we want.
            myItems = myItemsList.getItemList();
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
                Intent intent = new Intent(view.getContext(), NewListingActivity.class);
                startActivity(intent);
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
}
