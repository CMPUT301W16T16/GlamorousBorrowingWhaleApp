package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Martina on 16-03-26.
 */
// taken Mar-26-2016 fromhttp://stackoverflow.com/questions/18632823/how-to-monitor-network-status-in-android
public class NetworkUtil {

    public static int CONNECTED = 1;
    public static int NOT_CONNECTED = 0;


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return CONNECTED;
        }
        return NOT_CONNECTED;
    }

    // turn on the NetworkChangeReceiver to start listening for a change in connectivity
    public static void startListeningForNetwork(Context context) {
        ComponentName receiver = new ComponentName(context, NetworkChangeReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    // turn off the NetworkChangeReceiver to stop listening for a change in connectivity
    public static void stopListeningForNetwork(Context context) {
        ComponentName receiver = new ComponentName(context, NetworkChangeReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}