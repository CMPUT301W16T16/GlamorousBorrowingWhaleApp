package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrowed_items);
        setTitle("Items I've Borrowed");
    }
}
