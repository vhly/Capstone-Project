package mobi.vhly.capstone.conf;

import android.content.Context;
import android.content.res.AssetManager;
import mobi.vhly.capstone.R;
import mobi.vhly.capstone.commonlib.io.StreamUtil;
import mobi.vhly.capstone.commonlib.preferences.ProtectedPreferences;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/25
 * Email: vhly@163.com
 */
public final class GameConfiguration {

    public static final String KEY_CURRENT_REGION = "current.region";
    public static final String KEY_CURRENT_GAME = "current.game";
    public static String sIconApiPoint = "http://media.blizzard.com/%s/icons/%s/%s/%s.png";

    private static String sCurrentRegion = "tw";

    public static String sCurrentApiPoint = "https://tw.api.battle.net/";

    public static String sCurrentAuthorizeApiPoint = "https://tw.battle.net/oauth/authorize";

    public static String sCurrentGameType = "d3";

    private static HashMap<String, String> sRegions;
    private static TreeMap<String, String> sGameTypes;
    private static HashMap<String, String> sSupportLanguages;

    public static String sApiKey = "";
    public static String sApiSecret = "";

    public static HashMap<String, Integer> sHeroNames;


    private static GameConfiguration outInstance;

    public static GameConfiguration createInstance(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not null");
        }
        if (outInstance == null) {
            outInstance = new GameConfiguration(context);
        }
        return outInstance;
    }


    public static GameConfiguration getInstance() {
        if (outInstance == null) {
            throw new IllegalStateException("Please invoke createInstance before this method.");
        }
        return outInstance;
    }

    private Context mContext;

    private ProtectedPreferences mProtectedPreferences;

    private GameConfiguration(Context context) {
        mContext = context;
        mProtectedPreferences = ProtectedPreferences.createDefaultPreferences(mContext, "gameConfig");

        loadAssetConfig(context);

        loadFromPreferences();

        loadClientKey();

        initHeroTypeNames();
    }

    public String getPreferenceString(String key) {
        String ret = null;
        if (key != null) {
            if (mProtectedPreferences != null) {
                ret = mProtectedPreferences.getString(key, null);
            }
        }
        return ret;
    }

    public String getHeroTypeName(String type) {
        String ret = "Error";

        if (type != null) {
            if (sHeroNames.containsKey(type)) {
                int strId = sHeroNames.get(type);
                ret = mContext.getString(strId);
            }
        }

        return ret;
    }

    private void initHeroTypeNames() {
        sHeroNames = new HashMap<String, Integer>();
        sHeroNames.put("barbarian", R.string.hero_name_barbarian);
        sHeroNames.put("crusader", R.string.hero_name_crusader);
        sHeroNames.put("demon-hunter", R.string.hero_name_demon_hunter);
        sHeroNames.put("monk", R.string.hero_name_monk);
        sHeroNames.put("witch-doctor", R.string.hero_name_witch_doctor);
        sHeroNames.put("wizard", R.string.hero_name_wizard);
    }

    /**
     * Load API Key and secret from assets/conf/api_key.properties
     * Please NOT check in this file,
     * This file has ignored.
     */
    private void loadClientKey() {
        AssetManager assetManager = mContext.getAssets();
        try {
            InputStream stream = assetManager.open("conf/api_key.properties");
            if (stream != null) {
                Properties p = new Properties();
                p.load(stream);
                sApiKey = p.getProperty("api.key");
                sApiSecret = p.getProperty("api.secret");
                StreamUtil.close(stream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load configuration from SharedPreferences
     */
    private void loadFromPreferences() {
        if (mProtectedPreferences != null) {
            sCurrentRegion = mProtectedPreferences.getString(KEY_CURRENT_REGION, "tw");
            // TODO: load regions from sharedPreferences.
            sCurrentApiPoint = sRegions.get(sCurrentRegion);

            sCurrentGameType = mProtectedPreferences.getString(KEY_CURRENT_GAME, "d3");

            sCurrentAuthorizeApiPoint = "https://" + sCurrentRegion + ".battle.net/oauth/authorize";
        }
    }

    /**
     * Load default configuration from asset file.
     *
     * @param context
     */
    private void loadAssetConfig(Context context) {
        if (context != null) {
            AssetManager assets = context.getAssets();
            try {
                InputStream stream = assets.open("conf/api_points.json");
                if (stream != null) {
                    byte[] data = StreamUtil.readStream(stream);
                    if (data != null && data.length > 0) {
                        JSONObject json = new JSONObject(new String(data));
                        JSONObject jsonObject = json.getJSONObject("regions");
                        if (sRegions == null) {
                            sRegions = new HashMap<String, String>();
                        } else {
                            sRegions.clear();
                        }
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            sRegions.put(key, jsonObject.getString(key));
                        }

                        jsonObject = json.getJSONObject("games");

                        if (sGameTypes == null) {
                            sGameTypes = new TreeMap<String, String>();
                        } else {
                            sGameTypes.clear();
                        }
                        keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            sGameTypes.put(key, jsonObject.getString(key));
                        }

                        sIconApiPoint = json.getString("icon_api");

                    }
                } else {
                    throw new IllegalStateException("configuration file must exist");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("context must not null");
        }
    }

}
