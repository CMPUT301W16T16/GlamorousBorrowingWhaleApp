package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by erin on 13/03/16.
 */
public class NewListingActivityUITest extends ActivityInstrumentationTestCase2<NewListingActivity> {
    public NewListingActivityUITest() { super(NewListingActivity.class); }
    Instrumentation instrumentation;
    NewListingActivity activity;

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
        // add xml components here
    }

    // assert that the activity exists
    public void testActivityExists() {
        assertNotNull(activity);
    }

    public void onActivityResultTest() {

    }

    public void onPauseTest() {

    }
}
