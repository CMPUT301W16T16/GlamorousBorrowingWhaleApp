package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by erin on 13/03/16.
 */
public class IncomingBids extends FragmentActivity {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_incoming_bids, null);
        return view;
    }
}
