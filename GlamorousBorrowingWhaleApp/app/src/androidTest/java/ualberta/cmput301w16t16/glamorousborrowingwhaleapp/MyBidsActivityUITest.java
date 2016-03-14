package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.test.UiThreadTest;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Martina on 16-02-12.
 */
public class MyBidsActivityUITest extends ActivityInstrumentationTestCase2<MyBidsActivity> {

    Instrumentation instrumentation;
    Activity activity = getActivity();
    EditText textInput;

    public MyBidsActivityUITest() {
        super(MyBidsActivity.class);
    }

    // assert that the activity exists
    public void testActivityExists() {
        assertNotNull(activity);
    }
}