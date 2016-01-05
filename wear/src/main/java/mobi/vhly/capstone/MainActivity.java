package mobi.vhly.capstone;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(vhly.mobi.capstone.R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(vhly.mobi.capstone.R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(vhly.mobi.capstone.R.id.text);
            }
        });
    }
}
