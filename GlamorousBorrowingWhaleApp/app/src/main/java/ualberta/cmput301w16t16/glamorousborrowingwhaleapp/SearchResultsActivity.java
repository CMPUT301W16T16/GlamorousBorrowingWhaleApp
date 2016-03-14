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

//        adapter = new ArrayAdapter<Item>(this, R.layout.list_item, ItemController.getItemList().getItemList());
//        itemsListView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

        // user popup: this should be moved to the Borrow page.
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProfileDialog profile = new ProfileDialog(SearchResultsActivity.this, null);
                profile.show();
                //android says memory leak error here.
            }
        });

        itemsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ItemController.setItem((Item) parent.getAdapter().getItem(position));
                Intent intent = new Intent(view.getContext(), TheirItemActivity.class);
                startActivity(intent);
                return false;
            }
        });

    }




}
