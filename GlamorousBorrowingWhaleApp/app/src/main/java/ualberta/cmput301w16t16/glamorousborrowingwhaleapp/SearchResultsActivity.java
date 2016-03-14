package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This activity displays to the user all the items that match their entered
 * search criteria (by using elastic search). A user can then select an item
 * and bid on it if they wish, this action also displays the owners contact
 * information.
 * @author adam, andrew, erin, laura, martina
 */

public class SearchResultsActivity extends AppCompatActivity {

    TextView tv;
    ListView itemsListView;
    ArrayAdapter<Item> adapter;
    ItemList items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        tv = (TextView) findViewById(R.id.textViewForTesting);
        itemsListView = (ListView) findViewById(R.id.myItemsListView);
        setTitle("Search Results: All");
        // TODO ES CALL: get items to populate list
        //adapter = new ArrayAdapter<Item>(SearchResultsActivity.this, R.layout.list_item, items.getItemList());
        //itemsListView.setAdapter(adapter);
        new ElasticSearch.elasticGetItems(getApplicationContext()).execute(itemsListView);
        // I have no clue if the following is proper, it was Android Studio doing that autocomplete
        // thing.
        /* Doesn't work atm, IDK why yet.*/
        /*adapter = new ArrayAdapter<Item>(this, R.layout.list_item, ItemController.getItemList().getItemList());
        itemsListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        itemsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //This chunk is pretty neat. The ViewAdapter makes note of what is clicked in
                //the dynamic list which is used to get the corresponding Item at that "position".
                //Again leveraging the ItemController, we can set that Item as the current Item
                //and send the user to the MyItemActivity with the current Item (the one
                //that they selected!).
                ItemController.setItem((Item) parent.getAdapter().getItem(position));
                Intent intent = new Intent(view.getContext(), TheirItemActivity.class);
                startActivity(intent);
                return false;
            }
        });*/

    }




}
