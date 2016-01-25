package mobi.vhly.capstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import mobi.vhly.capstone.commonlib.log.MyLog;
import mobi.vhly.capstone.commonlib.task.TaskCallback;
import mobi.vhly.capstone.commonlib.task.TaskResult;

public class MainActivity extends AppCompatActivity implements TaskCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyLog.d("MainActivity", "Start process");

    }

    @Override
    public void taskStart() {

    }

    @Override
    public void taskFinished(TaskResult result) {

    }
}
