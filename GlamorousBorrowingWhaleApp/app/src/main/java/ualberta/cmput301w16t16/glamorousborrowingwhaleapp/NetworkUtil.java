package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.CONNECTED) {
            status = "Connected to Internet";
        } else if (conn == NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
}