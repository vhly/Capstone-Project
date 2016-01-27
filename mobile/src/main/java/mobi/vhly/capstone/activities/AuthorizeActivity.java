package mobi.vhly.capstone.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import mobi.vhly.capstone.R;
import mobi.vhly.capstone.conf.GameConfiguration;
import mobi.vhly.capstone.web.MyWebClient;

import java.io.IOException;
import java.util.Arrays;

public class AuthorizeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_authorize);

        WebView webView = (WebView) findViewById(R.id.authorize_web);

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new MyWebClient());

        String authorizeApiPoint = GameConfiguration.sCurrentAuthorizeApiPoint;

        webView.loadUrl(authorizeApiPoint);

    }

}
