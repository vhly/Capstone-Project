package vhly.mobi.capstone.commonlib;

import android.test.AndroidTestCase;
import mobi.vhly.capstone.commonlib.DeviceUtil;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */
public class DeviceUtilTest extends AndroidTestCase {

    public void testGetDeviceId(){
        String deviceId = DeviceUtil.getDeviceId(mContext);

        assertNotNull(deviceId);

    }

}
