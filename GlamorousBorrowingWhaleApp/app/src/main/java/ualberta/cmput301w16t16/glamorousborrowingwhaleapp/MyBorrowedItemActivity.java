package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * This displays a single item that the user is borrowing and allows them to
 * view the information on it.
 * @author adam, andrew, erin, laura, martina
 */

public class MyBorrowedItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrowed_item);
    }

}
