package ualberta.cmput301w16t16.glamorousborrowingwhaleapp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Martina on 16-03-26.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    User user = UserController.getUser();

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkUtil.getConnectivityStatus(context);

        // we just gained connectivity
        if (status == 1) {
            // we have offline items to push
            if (!user.getOfflineItems().isEmpty()) {
                // adding the items that were defined offline to ElasticSearch
                UserController.pushOfflineItems();
                NetworkUtil.stopListeningForNetwork(context);
            }
        }
    }
}
