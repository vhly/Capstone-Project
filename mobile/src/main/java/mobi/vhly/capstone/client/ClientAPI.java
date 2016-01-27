package mobi.vhly.capstone.client;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/25
 * Email: vhly@163.com
 */

import mobi.vhly.capstone.commonlib.io.HttpUtil;
import mobi.vhly.capstone.conf.GameConfiguration;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Blizzard Open API client
 */
public final class ClientAPI {


    /*

    https://us.api.battle.net/

    https://eu.api.battle.net/

    https://kr.api.battle.net/

    https://tw.api.battle.net/

    https://api.battlenet.com.cn/

     */

    private ClientAPI() {

    }

    public static String getD3HeroIconUrl(String size, String icon){
        return getIconUrl("d3", "portraits", size, icon);
    }

    public static String getIconUrl(String gameType, String type, String size, String icon) {
        String ret = null;
        if (type != null && size != null && icon != null) {

            ret = String.format(
                    GameConfiguration.sIconApiPoint,
                    gameType,
                    type,
                    size,
                    icon
            );

        }
        return ret;
    }

    public static String getAuthorizeUrl() {
        // https://<region>.battle.net/oauth/authorize
        String ret = null;
        StringBuilder sb = new StringBuilder(GameConfiguration.sCurrentAuthorizeApiPoint);

        sb.append("?client_id=").append(GameConfiguration.sApiKey);
        sb.append("&state=").append(System.currentTimeMillis());
        try {
            sb.append("&redirect_uri=").append(URLEncoder.encode("https://localhost/", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&response_type=code");

        ret = sb.toString();

        return ret;
    }

    public static JSONObject getProfileHero(String battleTag, String heroId) {
        JSONObject ret = null;
        if (battleTag != null && heroId != null) {
            String apiKey = GameConfiguration.sApiKey;
            String currentApiPoint = GameConfiguration.sCurrentApiPoint;
            String gameType = GameConfiguration.sCurrentGameType;
            String strUrl = currentApiPoint + gameType + "/profile/" + battleTag + "/hero/" + heroId + "?apikey=" + apiKey;

            byte[] data = HttpUtil.doGet(strUrl);
            if (data != null && data.length > 0) {
                try {
                    String str = new String(data, "UTF-8");
                    ret = new JSONObject(str);
                    str = null;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                data = null;
            }
        }
        return ret;
    }

    public static JSONObject getProfile(String battleTag, String lang) {
        JSONObject ret = null;
        if (battleTag != null) {
            String apiKey = GameConfiguration.sApiKey;
            String currentApiPoint = GameConfiguration.sCurrentApiPoint;
            String gameType = GameConfiguration.sCurrentGameType;
            String strUrl = currentApiPoint + gameType + "/profile/" + battleTag + "/?apikey=" + apiKey;

            byte[] data = HttpUtil.doGet(strUrl);
            if (data != null && data.length > 0) {
                try {
                    String str = new String(data, "UTF-8");
                    ret = new JSONObject(str);
                    str = null;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                data = null;
            }
        }
        return ret;
    }

}
