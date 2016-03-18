package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by andrew on 18/03/16.
 */
public class CustomAdapter extends ArrayAdapter<Item> {

    public CustomAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.custom_row, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        Item item = getItem(position);
        textView.setText(item.getTitle() + " " + item.getSize());

        if (item.getPhoto() != null) {
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
            byte[] tempPhoto = item.getPhoto();
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(tempPhoto, 0, tempPhoto.length));
        }
        return view;
    }
}

