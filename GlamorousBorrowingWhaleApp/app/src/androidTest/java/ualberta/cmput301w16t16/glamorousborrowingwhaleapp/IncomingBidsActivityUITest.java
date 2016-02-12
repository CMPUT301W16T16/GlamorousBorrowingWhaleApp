package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by Martina on 16-02-12.
 */
public class IncomingBidsActivityUITest extends ActivityInstrumentationTestCase2 {
        Instrumentation instrumentation;
        Activity activity;
        EditText textInput;
        public IncomingBidsActivityUITest() {
            super(IncomingBidsActivity.class);
        }

        protected void setUp() throws Exception {
            super.setUp();
            instrumentation = getInstrumentation();
            activity = getActivity();
            textInput = ((ListView) activity.findViewById(ualberta.cmput301w16t16.glamorousborrowingwhaleapp.R.id.incoming_bids_list));
        }
}
