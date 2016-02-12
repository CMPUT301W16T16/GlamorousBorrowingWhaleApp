package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Martina on 16-02-12.
 */
public class MainActivityUITest extends ActivityInstrumentationTestCase2 {
    Instrumentation instrumentation;
    Activity activity;
    Button my_stuff_button;
    Button my_bids_button;
    public MainActivityUITest() {
        super(MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
        my_stuff_button = ((Button) activity.findViewById(ualberta.cmput301w16t16.glamorousborrowingwhaleapp.R.id.my_stuff_button);
        my_bids_button = ((Button) activity.findViewById(ualberta.cmput301w16t16.glamorousborrowingwhaleapp.R.id.my_bids_button));
    }
}
