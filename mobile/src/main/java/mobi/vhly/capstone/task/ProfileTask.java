package mobi.vhly.capstone.task;

import mobi.vhly.capstone.client.ClientAPI;
import mobi.vhly.capstone.commonlib.task.BaseTask;
import mobi.vhly.capstone.commonlib.task.TaskCallback;
import mobi.vhly.capstone.commonlib.task.TaskResult;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/26
 * Email: vhly@163.com
 */
public class ProfileTask extends BaseTask {

    public ProfileTask(TaskCallback callback) {
        super(callback);
    }

    @Override
    protected TaskResult doInBackground(String... params) {
        TaskResult ret = new TaskResult();
        ret.action = 1;
        if (params != null && params.length > 0) {
            JSONObject object = ClientAPI.getProfile(params[0], null);
            if (object != null) {
                ret.state = 1;
                ret.data = object;
            }
        }
        return ret;
    }
}
