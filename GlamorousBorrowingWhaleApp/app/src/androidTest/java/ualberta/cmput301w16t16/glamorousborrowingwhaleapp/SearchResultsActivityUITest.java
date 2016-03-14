package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;


import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * Created by erin on 13/03/16.
 */

public class SearchResultsActivityUITest extends ActivityInstrumentationTestCase2 {
    Instrumentation instrumentation;
    Activity activity;
    EditText textInput;
    public SearchResultsActivityUITest() {
        super(SearchResultsActivity.class);
    }

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
}
