package mobi.vhly.capstone.commonlib.preferences;

import android.content.Context;
import android.util.Base64;
import mobi.vhly.capstone.commonlib.CryptUtil;
import mobi.vhly.capstone.commonlib.DeviceUtil;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */

/**
 * Encrypt and Decrypt use same key, support AES, DES
 */
public class AesPreferencesObfuscator implements PreferencesObfuscator {

    private byte[] mPassword;

    private byte[] mIvData;

    public AesPreferencesObfuscator(Context context, byte[] password) {

        if (context == null) {
            throw new IllegalArgumentException("context must be set");
        }

        if (password == null || password.length != 16) {
            throw new IllegalArgumentException("password is bad");
        }

        mPassword = password;

        String serial = DeviceUtil.getDeviceId(context);
        int len = serial.length();
        if (len > 16) {
            serial = serial.substring(0, 16);
        } else {
            int cc = 16 - len;
            for (int i = 0; i < cc; i++) {
                serial += "A";
            }
        }

        mIvData = serial.getBytes();
    }

    @Override
    public String obfuscateString(String origString) {
        String ret = null;
        byte[] data = CryptUtil.aesIvEncrypt(origString.getBytes(), mPassword, mIvData);
        if (data != null) {
            ret = Base64.encodeToString(data, Base64.NO_WRAP);
        }
        return ret;
    }

    @Override
    public String unobfuscateString(String str) {
        String ret = null;

        if (str != null) {
            if (str.length() > 0) {
                byte[] data = Base64.decode(str, Base64.NO_WRAP);

                data = CryptUtil.aesIvDecrypt(data, mPassword, mIvData);

                ret = new String(data);
            } else {
                ret = str;
            }
        }

        return ret;
    }

}
