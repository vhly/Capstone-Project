package mobi.vhly.capstone.commonlib.task;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/25
 * Email: vhly@163.com
 */
public interface TaskCallback {

    void taskStart();

    void taskFinished(TaskResult result);

}
