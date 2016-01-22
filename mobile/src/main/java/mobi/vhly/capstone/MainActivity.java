package mobi.vhly.capstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import mobi.vhly.capstone.commonlib.log.MyLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyLog.d("MainActivity", "Start process");

    }
}
