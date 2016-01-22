package mobi.vhly.capstone.commonlib.io;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */
public final class StreamUtil {
    private StreamUtil() {
    }


    public static void close(Object stream) {
        if (stream != null) {

            try {
                if (stream instanceof InputStream) {
                    ((InputStream) stream).close();
                } else if (stream instanceof OutputStream) {
                    ((OutputStream) stream).close();
                } else if (stream instanceof Reader) {
                    ((Reader) stream).close();
                } else if (stream instanceof Writer) {
                    ((Writer) stream).close();
                } else if (stream instanceof HttpURLConnection) {
                    ((HttpURLConnection) stream).disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static long readStream(InputStream in, OutputStream out) {
        long ret = 0;
        if (in != null && out != null) {
            byte[] buf = new byte[128];
            int len;
            try {
                while (true) {
                    len = in.read(buf);
                    if(len == -1){
                        break;
                    }
                    out.write(buf, 0, len);
                    ret += len;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                buf = null;
            }
        }
        return ret;
    }

    public static byte[] readStream(InputStream in){
        byte[] ret = null;

        if (in != null) {

            ByteArrayOutputStream bout = new ByteArrayOutputStream();

            long len = readStream(in, bout);

            if(len > 0){
                ret = bout.toByteArray();
            }

            close(bout);
            bout = null;
        }

        return ret;
    }

}
