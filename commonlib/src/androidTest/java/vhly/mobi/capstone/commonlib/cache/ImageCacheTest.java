package vhly.mobi.capstone.commonlib.cache;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.AndroidTestCase;
import mobi.vhly.capstone.commonlib.cache.ImageCache;
import vhly.mobi.capstone.commonlib.R;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */
public class ImageCacheTest extends AndroidTestCase {

    public void testImageCacheCreate(){

        ImageCache imageCache = ImageCache.getInstance();

        Context context = getContext();

        Resources resources = context.getResources();

        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher);

        for (int i = 0; i < 10000; i++) {
            imageCache.putBitmap("url " + i, bitmap);
        }

        imageCache.clear();


        imageCache = null;

    }

    public void testRemove(){

        ImageCache imageCache = ImageCache.getInstance();

        Context context = getContext();

        Resources resources = context.getResources();

        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher);

        for (int i = 0; i < 10; i++) {
            imageCache.putBitmap("url " + i, bitmap);
        }

        imageCache.remove("url " + 9);


        imageCache = null;

    }

}
