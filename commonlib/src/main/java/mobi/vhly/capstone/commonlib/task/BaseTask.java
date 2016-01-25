package mobi.vhly.capstone.commonlib.task;

import android.os.AsyncTask;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/25
 * Email: vhly@163.com
 */

/**
 * Base AsyncTask, subclasses implements doInBackground() and return result ok.
 */
public abstract class BaseTask extends AsyncTask<String, Integer, TaskResult> {

    private TaskCallback mCallback;

    public BaseTask(TaskCallback callback) {
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        if (mCallback != null) {
            mCallback.taskStart();
        }
    }

    @Override
    protected void onPostExecute(TaskResult result) {
        if (mCallback != null) {
            mCallback.taskFinished(result);
        }
    }
}
