package mobi.vhly.capstone.commonlib.cache;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import mobi.vhly.capstone.commonlib.crypto.CryptUtil;
import mobi.vhly.capstone.commonlib.io.StreamUtil;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */
public class FileCache {
    private static FileCache ourInstance;

    public static FileCache createInstance(Context context) {

        if (context != null) {

            if (ourInstance == null) {
                ourInstance = new FileCache(context);
            }

        }

        return ourInstance;
    }

    public static FileCache getInstance() {

        if (ourInstance == null) {
            throw new IllegalStateException("Please invoke createInstance(Context) method before this");
        }

        return ourInstance;
    }

    private Context mContext;

    private File mCacheDir;

    private BroadcastReceiver mExternalChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            mCacheDir = null;
            mCacheDir = getCacheDir();
        }
    };

    private FileCache(Context context) {
        mContext = context;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        context.registerReceiver(mExternalChangeReceiver, intentFilter);

        mCacheDir = getCacheDir();
    }

    public void destroy() {
        mContext.unregisterReceiver(mExternalChangeReceiver);
        mExternalChangeReceiver = null;
        mCacheDir = null;
        ourInstance = null;
    }


    private File getCacheDir() {
        File ret = null;

        if (mCacheDir != null && mCacheDir.exists()) {
            ret = mCacheDir;
        } else {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File dir = mContext.getExternalCacheDir();
                if (dir != null) {
                    if (!dir.exists()) {
                        if (dir.mkdirs()) {
                            ret = dir;
                        }
                    } else {
                        ret = dir;
                    }
                }
            }
            if (ret == null) {
                ret = mContext.getCacheDir();
            }
        }
        return ret;
    }


    private static String mapFileName(String url) {
        String ret = null;
        if (url != null) {
            ret = CryptUtil.md5Hex(url.getBytes());
        }
        return ret;
    }

    public InputStream getStreamForRead(String url) {
        InputStream ret = null;
        if (url != null) {
            String fileName = mapFileName(url);
            File file = new File(mCacheDir, fileName);
            if (file.exists()) {
                try {
                    ret = new BufferedInputStream(new FileInputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public OutputStream getStreamForWrite(String url) {
        OutputStream ret = null;
        if (url != null) {
            String fileName = mapFileName(url);
            File file = new File(mCacheDir, fileName);
            boolean bok = true;
            if (!file.exists()) {
                try {
                    bok = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bok) {
                try {
                    ret = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public byte[] load(String url) {
        byte[] ret = null;
        if (url != null) {
            InputStream inputStream = getStreamForRead(url);
            if (inputStream != null) {

                ret = StreamUtil.readStream(inputStream);

                StreamUtil.close(inputStream);
                inputStream = null;
            }
        }
        return ret;
    }

    public void save(String url, byte[] data) {
        if (url != null && data != null && data.length > 0) {
            OutputStream outputStream = getStreamForWrite(url);
            if (outputStream != null) {
                try {
                    outputStream.write(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                StreamUtil.close(outputStream);
                outputStream = null;
            }
        }
    }

}
