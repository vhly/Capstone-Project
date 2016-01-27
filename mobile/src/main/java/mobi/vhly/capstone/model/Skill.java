package mobi.vhly.capstone.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/26
 * Email: vhly@163.com
 */
public class Skill implements JsonModel {

    public static final int SKILL_TYPE_ACTIVE = 1;
    public static final int SKILL_TYPE_RUNE = 2;
    public static final int SKILL_TYPE_PASSIVE = 3;

    private int mSkillType;

    private String slug; //"evasive-fire",
    private String name; //"迴避射擊",
    private String icon; //"x1_demonhunter_evasivefire",
    private int level; // 14,
    private String categorySlug; //"primary",
    private String tooltipUrl; //"skill/demon-hunter/evasive-fire",
    private String description; //"產生：4 點憎恨\r\n\r\n射出一簇箭矢，擊中主要敵人造成 200% 武器傷害，並對額外兩名敵人造成 100% 武器傷害。\r\n\r\n若前方近距離內有敵人，你將施展後空翻來迴避敵人 5 碼。",
    private String simpleDescription; //"產生：4 點憎恨\r\n\r\n朝三名敵人射擊，若前方近距離內有敵人，你將會施展後空翻來迴避敵人。",
    private String skillCalcId; //"U"

    private int order;
    private String flavor;

    private Skill rune; // if mSkillType == SKILL_TYPE_ACTIVE then this field need set or null

    private Skill parentSkill; // if mSkillType == SKILL_TYPE_RUNE this field is skill which has rune

    public Skill getParentSkill() {
        return parentSkill;
    }

    public void setParentSkill(Skill parentSkill) {
        this.parentSkill = parentSkill;
    }

    public Skill getRune() {
        return rune;
    }

    public void setRune(Skill rune) {
        this.rune = rune;
    }

    public int getSkillType() {
        return mSkillType;
    }

    /**
     * Set Skill Type for Hero
     * @param skillType
     * @see Skill#SKILL_TYPE_ACTIVE
     * @see Skill#SKILL_TYPE_RUNE
     * @see Skill#SKILL_TYPE_PASSIVE
     */
    public void setSkillType(int skillType) {
        mSkillType = skillType;
    }

    @Override
    public void parseJson(JSONObject json) throws JSONException {

        if (json != null) {

            slug = json.getString("slug");
            name = json.getString("name");
            level = json.getInt("level");

            // optional
            description = json.getString("description");
            skillCalcId = json.getString("skillCalcId");

            tooltipUrl = json.optString("tooltipUrl");
            icon = json.optString("icon");
            order = json.optInt("order");
            categorySlug = json.optString("categorySlug");
            simpleDescription = json.optString("simpleDescription");

            flavor = json.optString("flavor");


        }

    }
}
