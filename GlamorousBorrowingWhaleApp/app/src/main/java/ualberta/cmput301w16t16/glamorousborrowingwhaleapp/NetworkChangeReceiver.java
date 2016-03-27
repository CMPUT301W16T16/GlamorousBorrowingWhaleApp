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
                // adding each item to elastic search and updating the user to now own the item
                for (Item item: user.getOfflineItems()) {
                    try {
                        new ElasticSearch.elasticAddItem().execute(item).get(1, TimeUnit.DAYS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        Log.e("ElasticSearch", "problem while waiting for elastic search to add item");
                        e.printStackTrace();
                    }
                    String itemID = item.getID();
                    user.addMyItem(itemID);
                    UserController.updateUserElasticSearch(user);

                    // removing the item from offline items
                    //TODO: check to make sure item was successfully added first
                    user.removeOfflineItem(item);
                }
            }
        }
    }
}
