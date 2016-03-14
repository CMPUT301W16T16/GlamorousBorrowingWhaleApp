package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.test.ViewAsserts;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Martina on 16-02-12.
 */
public class MyItemActivityUITest extends ActivityInstrumentationTestCase2 {
    Instrumentation instrumentation;
    Activity activity;
    ImageButton save;
    ImageButton pictureView;
    ImageButton delete;
    ImageButton rejectBid;
    ImageButton acceptBid;
    TextView decisionPrompt;
    TextView comment2;
    TextView highestBid;
    TextView comment1;
    EditText name;
    EditText owner;
    EditText status;
    EditText size;
    public MyItemActivityUITest() {
        super(MyItemActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
    }

    // assert that the activity exists
    public void testActivityExists() {
        assertNotNull(activity);
    }

    public void testViews() {

    }

    public void onActivityResultTest() {

    }

    public void onResumeTest() {

    }

    public void onStopTest() {

    }
}
