package mobi.vhly.capstone.commonlib.preferences;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */
public interface PreferencesObfuscator {

    String obfuscateString(String origString);

    String unobfuscateString(String str);

}
