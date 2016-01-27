package mobi.vhly.capstone.model;

import android.os.Parcel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/27
 * Email: vhly@163.com
 */
public class Hero extends ProfileHero {

    private List<Skill> activeSkills;

    private List<Skill> passiveSkills;

    protected Hero(Parcel in) {
        super(in);
    }

    public Hero() {
    }

    @Override
    public void parseJson(JSONObject json) throws JSONException {
        super.parseJson(json);

        if (json != null) {

            JSONObject skills = json.getJSONObject("skills");

            JSONArray jsonArray = skills.getJSONArray("active");

            int len = jsonArray.length();

            if (len > 0) {
                activeSkills = new LinkedList<Skill>();
                parseSkill(activeSkills, Skill.SKILL_TYPE_ACTIVE, jsonArray);
            }

            jsonArray = skills.getJSONArray("passive");

            len = jsonArray.length();

            if (len > 0) {
                passiveSkills = new LinkedList<Skill>();
                parseSkill(passiveSkills, Skill.SKILL_TYPE_PASSIVE, jsonArray);
            }

        }

    }

    private void parseSkill(List<Skill> skillList, int skillType, JSONArray jsonArray) throws JSONException {
        if (jsonArray != null && skillList != null) {
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONObject skillJson = jsonObject.getJSONObject("skill");

                Skill skill = new Skill();
                skill.parseJson(skillJson);
                skill.setSkillType(skillType);

                JSONObject runeJson = jsonObject.optJSONObject("rune");
                if (runeJson != null) {
                    Skill skillRune = new Skill();
                    skillRune.setSkillType(Skill.SKILL_TYPE_RUNE);
                    skillRune.parseJson(runeJson);

                    skillRune.setParentSkill(skill);
                    skill.setRune(skillRune);
                }

                skillList.add(skill);
            }
        }
    }
}
