package mobi.vhly.capstone.commonlib.log;

import android.util.Log;
import vhly.mobi.capstone.commonlib.BuildConfig;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/5
 * Email: vhly@163.com
 */
public final class MyLog {

    public static final String LIB_NAME = "Common";

    private MyLog(){

    }

    public static void d(String tag, String msg){
        if (BuildConfig.DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if (BuildConfig.DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if (BuildConfig.DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if (BuildConfig.DEBUG){
            Log.d(tag, msg);
        }
    }
}
