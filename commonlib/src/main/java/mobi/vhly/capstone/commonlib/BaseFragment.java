package mobi.vhly.capstone.commonlib;

import android.support.v4.app.Fragment;
import mobi.vhly.capstone.commonlib.task.TaskCallback;
import mobi.vhly.capstone.commonlib.task.TaskResult;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/25
 * Email: vhly@163.com
 */
public class BaseFragment extends Fragment implements TaskCallback {

    @Override
    public void taskStart() {

    }

    @Override
    public void taskFinished(TaskResult result) {

    }
}
