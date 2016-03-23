package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
 * @see IncomingBids
 */

/**
 * It's very possible that activity may have to be deleted to deal with the fragments
 */

//TODO review lifecycle code
public class MyItemsActivity extends AppCompatActivity {

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
        //taken from http://stackoverflow.com/questions/3438276/change-title-bar-text-in-android March12,2016
        setTitle("My Items");
        //END
        ListView myItemsView;
        myItemsView = (ListView) findViewById(R.id.myItemsListView);

        user = UserController.getUser();


        //TODO: this is no longer how this is done: it needs to fetch from ES
        //myItemsList = user.getItemsRenting();



        //a different class should probably handle this part - say when a new user is created
        //there is a whole bunch of stuff that is initiated. Anyways, the reason for this next
        //part of code is to catch the nullpointerexception without actually firing the exception.
        //If the user has no items at all, the class does not auto-initialize a ItemsList.
        //Naturally, this throws a null if one tries to access or add or do stuff with it.
        myItemsList = new ItemList();
        myItems = myItemsList.getItemList();

            //////////////////////////////////////////////////////////////////////// this should be deleted because no more ItemController
//            Item item = new Item();
//            //Create a "First Item" to get the ItemList going.
//            item.setAvailability(false);
//            item.setDescription("This is your first Thing! You can delete it of course.");
//            item.setOwnerID(user.getID());
//            item.setSize("Medium");
//            item.setTitle("First Thing!");
//            myItemsList.add(item);
//            //user.addMyItem(item.getID());
//            Toast.makeText(MyItemsActivity.this, "First Thing Created!", Toast.LENGTH_SHORT).show();
//
//            //"initialize" the itemcontroller
//            ItemController.setItem(item);
//            //add some fake bids
//            setFirstBids(item);
//            //if you wanna add it to elastic search for some reason
//            //new ElasticSearch.elasticAddItem().execute(item);
            /////////////////////////////////////////////////////////////////////////// up through here

        myItems = myItemsList.getItemList();
        //finally creating that adapter to use with the myItems
        adapter = new CustomSearchResultsAdapter(this, myItems);
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

        //TODO: this is no longer how this is done: it needs to fetch from ES
        //myItemsList = user.getItemsRenting();



        myItems = myItemsList.getItemList();
        adapter.notifyDataSetChanged();
    }

    //Sets the "first item" with a bid list for fun.
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
