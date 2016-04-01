package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewBorrowingOutActivity extends AppCompatActivity {
    //private ItemList myItemsList;
    private ArrayList<Item> itemsBorrowingOut;
    private ArrayAdapter<Item> adapter;
    private User user = UserController.getUser();
    // Probably never used
    private ArrayAdapter<Item> getAdapter() {
        return adapter;
    }
    private ListView itemsView;
    private ArrayList<String> itemsArray;
    private String[] itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_borrowing_out);
        setTitle("My Items Currently Borrowed");
        itemsView = (ListView) findViewById(R.id.myItemsListView);

        if (NetworkUtil.getConnectivityStatus(this) == 1) {
            // here we will have to make myItems into actual items instead of IDs
            // this is a pretty lame way to do it
            itemsArray = user.getItemsBorrowed();
            if (itemsArray.isEmpty()) {
                Toast.makeText(ViewBorrowingOutActivity.this, "None of your items are being borrowed.", Toast.LENGTH_SHORT).show();
            } else {
                //TODO: can stop converting the items list into String[] once that's it's actual type
                itemsList = new String[itemsArray.size()];
                itemsList = itemsArray.toArray(itemsList);
                ItemController.getItemsByIDElasticSearch(itemsList);

                // myItems contains actual items, not IDs
                itemsBorrowingOut = ItemController.getItemList().getItemList();
                adapter = new CustomSearchResultsAdapter(this, itemsBorrowingOut);
                itemsView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(this, "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
        }

        itemsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
        // checking that there is an item to add to the adapter, and that it belongs to the current user
        if (ItemController.getItem() != null
                && ItemController.getItem().getOwnerID().equals(UserController.getUser().getID())
                && !itemsBorrowingOut.contains(ItemController.getItem())) {
            itemsBorrowingOut.add(ItemController.getItem());
            adapter.notifyDataSetChanged();
        }
    }

}
