package mobi.vhly.capstone.commonlib.task;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

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

    private WeakReference<TaskCallback> mCallbackReference;

    public BaseTask(TaskCallback callback) {
        if (callback != null) {
            mCallbackReference = new WeakReference<TaskCallback>(callback);
        }
    }

    @Override
    protected void onPreExecute() {
        if (mCallbackReference != null) {
            TaskCallback callback = mCallbackReference.get();
            if (callback != null) {
                callback.taskStart();
            }
        }
    }

    @Override
    protected void onPostExecute(TaskResult result) {
        if (mCallbackReference != null) {
            TaskCallback callback = mCallbackReference.get();
            if (callback != null) {
                callback.taskFinished(result);
            }
        }
    }
}
