package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This activity, much like MyItemsActivity, lists the items that the user is
 * currently borrowing. If they select one it brings them to
 * MyBorrowedItemActivity. If the user swipes right it brings them to
 * MyBidsActivity
 * @author adam, andrew, erin, laura, martina
 * @see MyBorrowedItemActivity
 * @see MyBidsActivity
 */

public class MyBorrowedItemsActivity extends AppCompatActivity {
    ArrayList<Item> items = new ArrayList<>();
    ArrayAdapter<Item> adapter;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrowed_items);
        setTitle("Items You've Borrowed");

        user = UserController.getUser();

        ListView borrowedItemsListView = (ListView) findViewById(R.id.borrowedItemsListView);
        ArrayList<String> borrowedItemsArray = user.getItemsBorrowed();
        String[] borrowedItemsList = new String[borrowedItemsArray.size()];
        borrowedItemsList = borrowedItemsArray.toArray(borrowedItemsList);
        ItemController.getItemsByIDElasticSearch(borrowedItemsList);

        // myItems contains actual items, not IDs
        items = ItemController.getItemList().getItemList();
        adapter = new CustomBorrowedItemsAdapter(this, items);
        borrowedItemsListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        borrowedItemsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /*
            The ViewAdapter saves the position of what is clicked in the dynamic list.
            Set that Item as the current Item and send the user to the MyItemActivity with the current Item
             */
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ItemController.setItem((Item) parent.getAdapter().getItem(position));
                Intent intent = new Intent(view.getContext(), TheirItemActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }
    public class CustomBorrowedItemsAdapter extends ArrayAdapter<Item> {

        public CustomBorrowedItemsAdapter(Context context, ArrayList<Item> items) {
            super(context, R.layout.custom_row_my_items, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.custom_row_my_items, parent, false);

            TextView title = (TextView) view.findViewById(R.id.my_items_title);
            TextView description = (TextView) view.findViewById(R.id.my_items_description);
            TextView renter = (TextView) view.findViewById(R.id.my_items_renter);
            renter.setVisibility(View.GONE);

            Item item = getItem(position);
            title.setText(item.getTitle());
            description.setText(item.getDescription());
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view);

            if (item.getPhoto() != null) {
                byte[] tempPhoto = item.getPhoto();
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
            }

            return view;
        }
    }


}
