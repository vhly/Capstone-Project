package vhly.mobi.capstone.conf;

import android.test.AndroidTestCase;
import android.util.Log;
import mobi.vhly.capstone.conf.GameConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/25
 * Email: vhly@163.com
 */
public class GameConfigurationTest extends AndroidTestCase {

    public void testStringFormat() {

        String url = String.format("http://media.blizzard.com/%s/icons/%s/%s/%s.png", "d3", "items", "large", "unique_helm_set_07_x1_demonhunter_male");

        Log.d("GCT", "url = " + url);


    }

    public void testLoadAssetConfig() {
        GameConfiguration.createInstance(mContext);
    }

}
