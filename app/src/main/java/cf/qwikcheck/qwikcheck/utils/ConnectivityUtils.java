package cf.qwikcheck.qwikcheck.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by akshit on 23/10/16.
 */

public class ConnectivityUtils {

    public static boolean isAppConnectedViaWiFi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean result =
                cm != null && cm.getActiveNetworkInfo() != null
                        && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI
                        && cm.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED;
        return result;
    }

    public static boolean isAppConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
