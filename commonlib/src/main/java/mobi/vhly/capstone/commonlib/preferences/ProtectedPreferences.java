package mobi.vhly.capstone.commonlib.preferences;

import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */
public class ProtectedPreferences {

    private SharedPreferences mSharedPreferences;

    private PreferencesObfuscator mObfuscator;

    public ProtectedPreferences(
            SharedPreferences sharedPreferences,
            PreferencesObfuscator obfuscator) {

        if (sharedPreferences == null) {
            throw new IllegalArgumentException("SharedPreferences must not be null");
        }
        if (obfuscator == null) {
            throw new IllegalArgumentException("Obfuscator must not be null");
        }
        mSharedPreferences = sharedPreferences;
        mObfuscator = obfuscator;
    }

    public void putString(String key, String value) {
        if (key != null) {

            if (value == null) {
                value = "";
            }

            SharedPreferences.Editor editor = mSharedPreferences.edit();

            editor.putString(mObfuscator.obfuscateString(key), mObfuscator.obfuscateString(value));
            if (Build.VERSION.SDK_INT >= 9) {
                editor.apply();
            } else {
                editor.commit();
            }
            editor = null;

        }
    }

    public String getString(String key, String defValue) {
        String ret = null;
        if (key != null) {
            String string = mSharedPreferences.getString(mObfuscator.obfuscateString(key), null);
            if (string != null) {
                ret = mObfuscator.unobfuscateString(string);
            } else {
                ret = defValue;
            }
        }
        return ret;
    }

    public void putInt(String key, int value) {
        if (key != null) {

            SharedPreferences.Editor editor = mSharedPreferences.edit();

            editor.putInt(mObfuscator.obfuscateString(key), value);
            if (Build.VERSION.SDK_INT >= 9) {
                editor.apply();
            } else {
                editor.commit();
            }
            editor = null;

        }
    }

    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(mObfuscator.obfuscateString(key), defValue);
    }

}
