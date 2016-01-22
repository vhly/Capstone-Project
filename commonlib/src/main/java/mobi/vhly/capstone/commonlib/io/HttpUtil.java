package mobi.vhly.capstone.commonlib.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/10
 * Email: vhly@163.com
 */
public final class HttpUtil {

    public enum HttpMethod {
        GET("GET", false),
        POST("POST", true),
        PUT("PUT", true),
        DELETE("DELETE", false),
        HEAD("HEAD", false, false);

        private String method;
        private boolean needOutput;
        private boolean hasResponse;

        HttpMethod(String method, boolean needOutput) {
            this(method, needOutput, true);
        }

        HttpMethod(String method, boolean needOutput, boolean hasResponse) {
            this.method = method;
            this.needOutput = needOutput;
            this.hasResponse = hasResponse;
        }
    }

    private static final int TIMEOUT_CONNECT = 3000;

    private static final int TIMEOUT_READ = 30000;

    private HttpUtil() {

    }

    private static byte[] process(HttpMethod method, String url, HashMap<String, String> headers, byte[] data) {
        byte[] ret = null;
        if (url != null) {

            HttpURLConnection conn;

            try {
                URL u = new URL(url);
                conn = (HttpURLConnection) u.openConnection();

                conn.setRequestMethod(method.method);

                conn.setConnectTimeout(TIMEOUT_CONNECT);

                conn.setReadTimeout(TIMEOUT_READ);

                conn.setRequestProperty("X-User-Agent", "HttpUtil/0.0.1 Android");
                conn.setRequestProperty("Accept-Encoding", "gzip");

                if (headers != null && !headers.isEmpty()) {
                    Set<String> keys = headers.keySet();
                    for (String key : keys) {
                        conn.setRequestProperty(key, headers.get(key));
                    }
                }

                if (method.needOutput && data != null && data.length > 0) {
                    conn.setDoOutput(true);

                    int dLen = data.length;

                    conn.setRequestProperty("Content-Length", Integer.toString(dLen));

                    OutputStream outputStream = conn.getOutputStream();

                    outputStream.write(data);

                    StreamUtil.close(outputStream);

                    //noinspection UnusedAssignment
                    outputStream = null;

                }

                int responseCode = conn.getResponseCode();

                if (responseCode == 200) {

                    if (method.hasResponse) {

                        InputStream inputStream = conn.getInputStream();

                        String encoding = conn.getContentEncoding();

                        if (encoding != null) {
                            if ("gzip".equals(encoding)) {
                                inputStream = new GZIPInputStream(inputStream);
                            }
                        }

                        ret = StreamUtil.readStream(inputStream);

                        StreamUtil.close(inputStream);

                        //noinspection UnusedAssignment
                        inputStream = null;

                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static byte[] doGet(String url, HashMap<String, String> headers) {
        return process(HttpMethod.GET, url, headers, null);
    }

    public static byte[] doGet(String url) {
        return doGet(url, null);
    }

    public static byte[] doPost(String url, HashMap<String, String> headers, byte[] data) {
        return process(HttpMethod.POST, url, headers, data);
    }

    public static byte[] doPost(String url, byte[] data) {
        return doPost(url, null, data);
    }

}
