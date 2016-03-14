package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

/**
 * Created by erin on 13/03/16.
 */
public class TheirItemActivityUITest extends ActivityInstrumentationTestCase2<TheirItemActivity> {
    public TheirItemActivityUITest() { super(TheirItemActivity.class); }

    Instrumentation instrumentation;
    TheirItemActivity activity;

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
