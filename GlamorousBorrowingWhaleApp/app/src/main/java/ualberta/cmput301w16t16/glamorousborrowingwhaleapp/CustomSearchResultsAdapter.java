package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.midi.MidiOutputPort;
import android.renderscript.Allocation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by andrew on 18/03/16.
 */
public class CustomSearchResultsAdapter extends ArrayAdapter<Item> {

    public CustomSearchResultsAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.custom_row_my_items, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_row_my_items, parent, false);

        TextView title = (TextView) view.findViewById(R.id.my_items_title);
        TextView description = (TextView) view.findViewById(R.id.my_items_description);
        TextView renter = (TextView) view.findViewById(R.id.my_items_renter);

        Item item = getItem(position);
        title.setText(item.getTitle());
        description.setText(item.getDescription());
        if (item.getRenterID().equals("")) {
            renter.setText("This item isn't being rented");
        } else {
            renter.setText(item.getRenterID());
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);

        if (item.getPhoto() != null) {
            byte[] tempPhoto = item.getPhoto();
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
        }

        return view;
    }
}

