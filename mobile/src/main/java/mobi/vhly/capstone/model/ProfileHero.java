package mobi.vhly.capstone.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/26
 * Email: vhly@163.com
 */
public class ProfileHero implements JsonModel {

    /*
      "id": 18290379,
      "name": "Jessica",
      "class": "demon-hunter",
      "gender": 1,
      "level": 70,
      "kills": {
        "elites": 7737
      },
      "paragonLevel": 394,
      "hardcore": false,
      "seasonal": false,
      "last-updated": 1453299413,
      "dead": false
             */

    private long mId;
    private String mName;
    private String mHeroClass;
    private int mGender;
    private int mLevel;
    private int mParagonLevel;
    private boolean hardcore;
    private boolean seasonal;
    private long lastUpdated;
    private boolean dead;

    @Override
    public void parseJson(JSONObject json) throws JSONException {
        if (json != null) {

            mId = json.getLong("id");
            mName = json.getString("name");
            mHeroClass = json.getString("class");
            mGender = json.getInt("gender");
            mLevel = json.getInt("level");
            mParagonLevel = json.getInt("paragonLevel");
            hardcore = json.getBoolean("hardcore");
            seasonal = json.getBoolean("seasonal");
            lastUpdated = json.getLong("last-updated");
            dead = json.getBoolean("dead");
        }
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getHeroClass() {
        return mHeroClass;
    }

    public int getGender() {
        return mGender;
    }

    public int getLevel() {
        return mLevel;
    }

    public int getParagonLevel() {
        return mParagonLevel;
    }
}
