package vhly.mobi.capstone.commonlib.preferences;

import android.test.AndroidTestCase;
import mobi.vhly.capstone.commonlib.preferences.AesPreferencesObfuscator;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */
public class AesPreferencesObfuscatorTest extends AndroidTestCase {

    private byte[] mPassword;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mPassword = new byte[]{
                0x22, 0x43, (byte) 0x87, (byte) 0xFF,
                0x22, 0x43, (byte) 0x87, (byte) 0xFF,
                0x22, 0x43, (byte) 0x87, (byte) 0xFF,
                0x22, 0x43, (byte) 0x87, (byte) 0xFF
        };

    }

    public void testObfuscateString() {
        AesPreferencesObfuscator obfuscator = new AesPreferencesObfuscator(mContext, mPassword);

        String origString = "Hello World";

        String string = obfuscator.obfuscateString(origString);

        assertNotNull(string);

        String s = obfuscator.unobfuscateString(string);

        assertNotNull(s);

        assertEquals(origString, s);

    }

    public void testSingleConstructor(){
        AesPreferencesObfuscator obfuscator = new AesPreferencesObfuscator(mContext);

        String origString = "Hello World";

        String string = obfuscator.obfuscateString(origString);

        assertNotNull(string);

        String s = obfuscator.unobfuscateString(string);

        assertNotNull(s);

        assertEquals(origString, s);

    }

}
