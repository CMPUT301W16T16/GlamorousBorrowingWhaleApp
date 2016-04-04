package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewBorrowingOutActivity extends AppCompatActivity {
    //private ItemList myItemsList;
    private ArrayAdapter<Item> adapter;
    private User user = UserController.getUser();
    private User renter;
    private String renterID;
    private String oldID;
    private ListView itemsView;
    private ArrayList<String> usersItems;
    private ArrayList<Item> usersItemsArrayList;
    private ArrayList<String> rentersBorrowedItems;
    private String[] itemsList;
    private Item selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_borrowing_out);
        setTitle("My Items Currently Borrowed");

        SetItems();

        itemsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /*
            The ViewAdapter saves the position of what is clicked in the dynamic list.
            Set that Item as the current Item and send the user to the MyItemActivity with the current Item
             */
            // maybe a popup to ask if they wanna mark as returned actually
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ItemController.setItem((Item) parent.getAdapter().getItem(position));
                Intent intent = new Intent(view.getContext(), MyItemActivity.class);
                startActivity(intent);
                return false;
            }

        });

        itemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android 04/03/16
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Mark Returned")
                        .setMessage("Mark this item as returned?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                selectedItem = usersItemsArrayList.get(position);
                                selectedItem.setAvailability(true);
                                selectedItem.setRenterID("");
                                oldID = selectedItem.getID();
                                renterID = selectedItem.getRenterID();
                                renter = UserController.getUserByIDElasticSearch(renterID);
                                rentersBorrowedItems = renter.getItemsBorrowed();
                                rentersBorrowedItems.remove(selectedItem.getID());
                                UserController.updateUserElasticSearch(renter);
                                ItemController.updateItemElasticSearch(selectedItem);
                                usersItems = user.getMyItems();
                                usersItems.remove(oldID);
                                usersItems.add(selectedItem.getID());
                                UserController.updateUserElasticSearch(user);
                                SetItems();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
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
        usersItems = user.getMyItems();
        itemsList = new String[usersItems.size()];
        itemsList = usersItems.toArray(itemsList);
        ItemController.getItemsByIDElasticSearch(itemsList);
        usersItemsArrayList = ItemController.getItemList().getItemList();
        adapter.notifyDataSetChanged();

    }

    public void SetItems() {
        itemsView = (ListView) findViewById(R.id.myItemsListView);

        if (NetworkUtil.getConnectivityStatus(this) == 1) {
            usersItems = user.getMyItems();
            itemsList = new String[usersItems.size()];
            itemsList = usersItems.toArray(itemsList);
            ItemController.getItemsByIDElasticSearch(itemsList);
            usersItemsArrayList = ItemController.getItemList().getItemList();
            for (Item item : usersItemsArrayList) {
                if (item.getAvailability()) {
                    usersItemsArrayList.remove(item);
                }
            }

            if (usersItemsArrayList.isEmpty()) {
                Toast.makeText(ViewBorrowingOutActivity.this, "None of your items are being borrowed.", Toast.LENGTH_SHORT).show();
            } else {
                //TODO: can stop converting the items list into String[] once that's it's actual type
                adapter = new CustomSearchResultsAdapter(this, usersItemsArrayList);
                itemsView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(this, "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
        }
    }

}
