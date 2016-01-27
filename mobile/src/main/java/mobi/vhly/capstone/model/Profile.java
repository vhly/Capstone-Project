package mobi.vhly.capstone.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/26
 * Email: vhly@163.com
 */
public class Profile implements JsonModel {

    private String battleTag; //vhly#3282",
    private int paragonLevel; //94,
    private int paragonLevelHardcore; //,
    private int paragonLevelSeason; //,
    private int paragonLevelSeasonHardcore; //,
    private String guildName; //聖休亞瑞",
    private int lastHeroPlayed; //2200316,
    private int lastUpdated; //453634447,
    private int highestHardcoreLevel; //

    @Override
    public void parseJson(JSONObject json) throws JSONException {
        if (json != null) {

            battleTag = json.getString("battleTag");

            paragonLevel = json.getInt("paragonLevel");
            paragonLevelHardcore = json.getInt("paragonLevelHardcore");
            paragonLevelSeason = json.getInt("paragonLevelSeason");
            paragonLevelSeasonHardcore = json.getInt("paragonLevelSeasonHardcore");
            guildName = json.getString("guildName");
            lastHeroPlayed = json.getInt("lastHeroPlayed");
            lastUpdated = json.getInt("lastUpdated");
            highestHardcoreLevel = json.getInt("highestHardcoreLevel");


        }
    }
}
