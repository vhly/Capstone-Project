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

        if (password == null) {
            throw new IllegalArgumentException("password must be set");
        }

        mPassword = CryptUtil.md5(password); // for 16 bytes

        String serial = DeviceUtil.getDeviceId(context);

        mIvData = CryptUtil.md5(serial.getBytes()); // for 16 bytes
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
