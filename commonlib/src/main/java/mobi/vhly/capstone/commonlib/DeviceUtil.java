package mobi.vhly.capstone.commonlib;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */
public final class DeviceUtil {

    private DeviceUtil() {
    }


    public static String getDeviceId(Context context) {
        String ret = null;
        try {
            ret = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception ex) {

        }
        if (ret == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                ret = Build.SERIAL;
            }
            if (ret == null) {
                ret = Settings.Secure.getString(
                        context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }

        return ret;
    }

    public static boolean isConnectionAlive(Context context) {
        boolean bret = false;
        if (context != null) {

            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();

            // 监测 网络状态权限
            int permission = context.checkPermission(
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    callingPid,
                    callingUid
            );

            // 请求 联网权限
            int internetPermission = context.checkPermission(
                    Manifest.permission.INTERNET,
                    callingPid,
                    callingUid
            );

            if (permission == PackageManager.PERMISSION_GRANTED
                    && internetPermission == PackageManager.PERMISSION_GRANTED) {
                ConnectivityManager manager =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null) {
                    if (networkInfo.isAvailable() && networkInfo.isConnected()) {
                        bret = true;
                    }
                }
            } else {
                MyLog.e("HttpUtil", "Need ACCESS_NETWORK_STATE & INTERNET Permission!");
            }

        }
        return bret;
    }
}
