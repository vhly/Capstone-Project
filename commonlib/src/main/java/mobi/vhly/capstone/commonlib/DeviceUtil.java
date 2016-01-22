package mobi.vhly.capstone.commonlib;

import android.content.Context;
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
}
