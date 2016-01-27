package mobi.vhly.capstone;

import android.app.Application;
import android.content.Context;
import mobi.vhly.capstone.commonlib.cache.FileCache;
import mobi.vhly.capstone.conf.GameConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/26
 * Email: vhly@163.com
 */

/**
 * Startup enter point
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();

        // Init FileCache
        FileCache.createInstance(context);

        // Init GameConfiguration
        GameConfiguration.createInstance(context);

    }
}
