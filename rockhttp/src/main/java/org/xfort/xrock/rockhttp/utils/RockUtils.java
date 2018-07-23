package org.xfort.xrock.rockhttp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class RockUtils {

    /**
     * 检查网络情况
     *
     * @param context
     * @return true  网络可用
     */
    public static boolean networkIsOK(Context context) {
        try {
            ConnectivityManager connectM = (ConnectivityManager) context.getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = connectM.getActiveNetworkInfo();
            return networkinfo != null && networkinfo.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
