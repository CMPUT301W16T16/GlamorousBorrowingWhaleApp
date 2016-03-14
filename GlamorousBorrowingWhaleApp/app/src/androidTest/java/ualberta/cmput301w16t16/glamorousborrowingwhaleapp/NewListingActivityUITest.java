package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by erin on 13/03/16.
 */
public class NewListingActivityUITest {
    public void NewListingActivityUITest() { super(NewListingActivity.class); }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
        textInput = ((EditText) activity.findViewById(ualberta.cmput301w16t16.glamorousborrowingwhaleapp.R.id.body));
    }

    public void onActivityResultTest() {

    }

    public void onPauseTest() {

    }
}
