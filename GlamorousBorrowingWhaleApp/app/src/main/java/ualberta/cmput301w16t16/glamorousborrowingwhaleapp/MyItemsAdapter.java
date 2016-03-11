package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Martina on 16-03-10.
 */
public class MyItemsAdapter extends ArrayAdapter<Item> {
    private ArrayList<Item> objects;

    public MyItemsAdapter(Context context, int resource, ArrayList<Item> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v==null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.my_items_list_item);
        }
    }
}
