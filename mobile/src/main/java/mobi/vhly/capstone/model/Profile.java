package mobi.vhly.capstone.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/26
 * Email: vhly@163.com
 */
public class Profile implements JsonModel {

    private String mBattleTag; //vhly#3282",
    private int mParagonLevel; //94,
    private int paragonLevelHardcore; //,
    private int paragonLevelSeason; //,
    private int paragonLevelSeasonHardcore; //,
    private String guildName; //聖休亞瑞",
    private int lastHeroPlayed; //2200316,
    private int lastUpdated; //453634447,
    private int highestHardcoreLevel; //

    private List<ProfileHero> mProfileHeroes;

    @Override
    public void parseJson(JSONObject json) throws JSONException {
        if (json != null) {

            mBattleTag = json.getString("battleTag");

            mParagonLevel = json.getInt("paragonLevel");
            paragonLevelHardcore = json.getInt("paragonLevelHardcore");
            paragonLevelSeason = json.getInt("paragonLevelSeason");
            paragonLevelSeasonHardcore = json.getInt("paragonLevelSeasonHardcore");
            guildName = json.getString("guildName");
            lastHeroPlayed = json.getInt("lastHeroPlayed");
            lastUpdated = json.getInt("lastUpdated");
            highestHardcoreLevel = json.getInt("highestHardcoreLevel");

            JSONArray array = json.optJSONArray("heroes");

            if (array != null) {

                int count = array.length();
                if (count > 0) {
                    if (mProfileHeroes == null) {
                        mProfileHeroes = new LinkedList<ProfileHero>();
                    } else {
                        mProfileHeroes.clear();
                    }
                    for (int i = 0; i < count; i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        ProfileHero profileHero = new ProfileHero();
                        profileHero.parseJson(jsonObject);
                        mProfileHeroes.add(profileHero);
                    }
                }

            }
        }
    }

    public String getBattleTag() {
        return mBattleTag;
    }

    public List<ProfileHero> getProfileHeroes() {
        return mProfileHeroes;
    }
}
