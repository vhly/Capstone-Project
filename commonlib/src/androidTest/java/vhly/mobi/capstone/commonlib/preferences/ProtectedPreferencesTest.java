package vhly.mobi.capstone.commonlib.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;
import mobi.vhly.capstone.commonlib.preferences.AesPreferencesObfuscator;
import mobi.vhly.capstone.commonlib.preferences.ProtectedPreferences;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */
public class ProtectedPreferencesTest extends AndroidTestCase {

    private SharedPreferences mSharedPreferences;
    private byte[] mPassword;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSharedPreferences = mContext.getSharedPreferences("test", Context.MODE_PRIVATE);
        mSharedPreferences.edit().clear().commit();
        mPassword = "AAAAAAAAAAAAAAAA".getBytes();
    }

    @Override
    protected void tearDown() throws Exception {
        mSharedPreferences.edit().clear().commit();
        mSharedPreferences = null;
        mPassword = null;
        super.tearDown();
    }

    public void testPutString(){

        Context context = getContext();

        AesPreferencesObfuscator obfuscator = new AesPreferencesObfuscator(context, mPassword);

        ProtectedPreferences preferences = new ProtectedPreferences(mSharedPreferences, obfuscator);

        String origValue = "I'm a access token";

        String key = "access.token";

        preferences.putString(key, origValue);

        String token = preferences.getString(key, null);

        assertNotNull(token);

        assertEquals(origValue, token);

    }

    public void testPutInt(){
        Context context = getContext();

        AesPreferencesObfuscator obfuscator = new AesPreferencesObfuscator(context, mPassword);

        ProtectedPreferences preferences = new ProtectedPreferences(mSharedPreferences, obfuscator);

        int origValue = 998;

        String key = "access.port";

        preferences.putInt(key, origValue);

        int port = preferences.getInt(key, -1);

        assertEquals(origValue, port);

    }

}
