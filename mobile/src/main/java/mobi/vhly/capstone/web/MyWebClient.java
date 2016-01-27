package mobi.vhly.capstone.web;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import mobi.vhly.capstone.log.MyLog;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/26
 * Email: vhly@163.com
 */
public class MyWebClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        MyLog.d("MyWebClient", "request url = " + url);

        return super.shouldOverrideUrlLoading(view, url);
    }
}
