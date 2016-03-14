package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MyItemsIncomingBidsActivity extends AppCompatActivity {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENTS = 2;

    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager viewPager;
    private List<FragmentActivity> fragments = new ArrayList<FragmentActivity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_items_incoming_bids);

        fragments.add(FRAGMENT_ONE, new MyItems());
        fragments.add(FRAGMENT_TWO, new IncomingBids());

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()){
            @Override
            public int getCount() {
                return FRAGMENTS;
            }
            @Override
            public FragmentActivity getItem(final int position) {
                return fragments.get(position);
            }
            @Override
            public CharSequence getPageTitle(final int position) {
                switch (position) {
                    case FRAGMENT_ONE:
                        return "My Items";
                    case FRAGMENT_TWO:
                        return "My Incoming Bids";
                    default:
                        return null;
                }
            }
        };
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(fragmentPagerAdapter);
    }
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

}
