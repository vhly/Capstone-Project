package mobi.vhly.capstone.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.jackson.JacksonFactory;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.AuthorizationUIController;
import com.wuman.android.auth.DialogFragmentController;
import com.wuman.android.auth.OAuthManager;
import com.wuman.android.auth.oauth2.store.SharedPreferencesCredentialStore;
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

        //webView.loadUrl(authorizeApiPoint);

        SharedPreferencesCredentialStore credentialStore =
                new SharedPreferencesCredentialStore(getApplicationContext(),
                        "auth", new JacksonFactory());

        AuthorizationFlow.Builder builder = new AuthorizationFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                AndroidHttp.newCompatibleTransport(),
                new JacksonFactory(),
                new GenericUrl("https://socialservice.com/oauth2/access_token"),
                new ClientParametersAuthentication(
                        GameConfiguration.sApiKey, GameConfiguration.sApiSecret),
                GameConfiguration.sApiKey,
                GameConfiguration.sCurrentAuthorizeApiPoint);

        builder.setCredentialStore(credentialStore);

        builder.setScopes(Arrays.asList(new String[]{"wow.profile", "sc2.profile"}));

        AuthorizationFlow flow = builder.build();

        AuthorizationUIController controller =
                new DialogFragmentController(getSupportFragmentManager()) {

                    @Override
                    public String getRedirectUri() throws IOException {
                        return "http://localhost/Callback";
                    }

                    @Override
                    public boolean isJavascriptEnabledForWebView() {
                        return true;
                    }

                };

        OAuthManager oauth = new OAuthManager(flow, controller);

        try {
            Credential result = oauth.authorizeExplicitly("avfinder@gmail.com", null, null).getResult();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
