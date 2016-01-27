package mobi.vhly.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import mobi.vhly.capstone.activities.AuthorizeActivity;
import mobi.vhly.capstone.client.ClientAPI;
import mobi.vhly.capstone.commonlib.log.MyLog;
import mobi.vhly.capstone.commonlib.task.TaskCallback;
import mobi.vhly.capstone.commonlib.task.TaskResult;
import mobi.vhly.capstone.task.ProfileTask;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements TaskCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyLog.d("MainActivity", "Start process");

        ProfileTask task = new ProfileTask(this);
        task.execute("vhly-3282");

        String authorizeUrl = ClientAPI.getAuthorizeUrl();

        Log.d("MainActivity", "auth url = " + authorizeUrl);

    }

    @Override
    public void taskStart() {

    }

    @Override
    public void taskFinished(TaskResult result) {
        if (result != null) {
            if (result.action == 1) {
                if (result.data != null) {
                    JSONObject json = (JSONObject) result.data;
                    Log.d("MainActivity", "profile json = " + json);
                }
            }
        }
    }

    public void btnAuthorizeOnClick(View view) {

        Intent intent = new Intent(this, AuthorizeActivity.class);
        startActivity(intent);

    }
}
