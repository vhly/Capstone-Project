package mobi.vhly.capstone.commonlib.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;
import mobi.vhly.capstone.commonlib.log.MyLog;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */
public final class ImageCache {

    private static ImageCache sOutInstance;

    public static ImageCache getInstance() {
        if (sOutInstance == null) {
            sOutInstance = new ImageCache();
        }
        return sOutInstance;
    }

    private LruCache<String, Bitmap> mLruCache;

    private ImageCache() {

        Runtime runtime = Runtime.getRuntime();

        long maxMemory = runtime.maxMemory();

        mLruCache = new BitmapCache((int) (maxMemory / 8));

    }

    public void putBitmap(String url, Bitmap bitmap) {
        if (url != null && bitmap != null) {
            mLruCache.put(url, bitmap);
        }
    }

    public Bitmap getBitmap(String url) {
        Bitmap ret = null;
        if (url != null) {
            ret = mLruCache.get(url);
        }
        return ret;
    }

    public void clear() {
        mLruCache.evictAll();
    }

    public void remove(String url) {
        if (url != null) {
            mLruCache.remove(url);
        }
    }


    private static class BitmapCache extends LruCache<String, Bitmap> {

        private HashMap<String, SoftReference<Bitmap>> mReferenceCache;

        /**
         * @param maxMemoryByteSize for caches that do not override {@link #sizeOf}, this is
         *                          the maximum number of entries in the cache. For all other caches,
         *                          this is the maximum sum of the sizes of the entries in this cache.
         */
        public BitmapCache(int maxMemoryByteSize) {
            super(maxMemoryByteSize);
            mReferenceCache = new LinkedHashMap<>();
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            int ret;
            if (Build.VERSION.SDK_INT >= 19) {
                ret = value.getAllocationByteCount();
            } else {
                ret = value.getRowBytes() * value.getHeight();
            }
            return ret;
        }

        @Override
        protected Bitmap create(String key) {
            Bitmap ret = null;
            SoftReference<Bitmap> reference = mReferenceCache.get(key);
            if (reference != null) {

                ret = reference.get();

                mReferenceCache.remove(key);
            }
            return ret;
        }

        @Override
        protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
            MyLog.d("ImageCache", "entryRemoved(" + evicted + "," + key + "," + oldValue + ", " + newValue + ")");
            if (evicted) {
                mReferenceCache.put(key, new SoftReference<>(oldValue));
            } else {
                mReferenceCache.remove(key);
            }
        }
    }


}
