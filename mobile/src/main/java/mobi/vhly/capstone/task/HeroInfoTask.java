package mobi.vhly.capstone.task;

import mobi.vhly.capstone.Constants;
import mobi.vhly.capstone.client.ClientAPI;
import mobi.vhly.capstone.commonlib.task.BaseTask;
import mobi.vhly.capstone.commonlib.task.TaskCallback;
import mobi.vhly.capstone.commonlib.task.TaskResult;
import mobi.vhly.capstone.model.Hero;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/27
 * Email: vhly@163.com
 */
public class HeroInfoTask extends BaseTask {

    public HeroInfoTask(TaskCallback callback) {
        super(callback);
    }

    @Override
    protected TaskResult doInBackground(String... params) {
        TaskResult ret = new TaskResult();

        ret.action = Constants.TASK_ACTION_PROFILE_HERO;

        if (params != null && params.length > 1) { // [0] battleTag, [1] heroId

            String battleTag = params[0];

            String strHeroId = params[1];

            JSONObject jsonObject = ClientAPI.getProfileHero(battleTag, strHeroId);

            // TODO: Should parse JSONObejct and return Hero
            if (jsonObject != null) {
                Hero hero = new Hero();
                try {
                    hero.parseJson(jsonObject);
                    ret.data = hero;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        return ret;
    }
}
