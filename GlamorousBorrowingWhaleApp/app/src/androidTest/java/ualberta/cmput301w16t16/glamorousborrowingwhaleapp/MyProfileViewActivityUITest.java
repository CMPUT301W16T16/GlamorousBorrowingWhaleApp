package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

/**
 * Created by erin on 13/03/16.
 */
public class MyProfileViewActivityUITest extends ActivityInstrumentationTestCase2<MyProfileViewActivity> {
    public MyProfileViewActivityUITest() { super(MyProfileViewActivity.class); }

    MyProfileViewActivity activity = getActivity();

    // assert that the activity exists
    public void testActivityExists() {
        assertNotNull(activity);
    }

    public void longClickTest() {
        // idk how this works
    }

    public void onPauseTest() {
        // Nothing in this function yet
    }

    public void onResumeTest() {
        // Nothing in this function yet
    }

    public void onStopTest() {
        // Nothing in this function yet
    }
}
