package vhly.mobi.capstone.commonlib.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.AndroidTestCase;
import mobi.vhly.capstone.commonlib.cache.FileCache;
import mobi.vhly.capstone.commonlib.imageload.ImageLoadTask;
import mobi.vhly.capstone.commonlib.io.StreamUtil;
import vhly.mobi.capstone.commonlib.R;

import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */
public class FileCacheTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        FileCache.createInstance(mContext);
    }

    public void testLoad() throws ExecutionException, InterruptedException {

        FileCache fileCache = FileCache.getInstance();

        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher);

        OutputStream outputStream = fileCache.getStreamForWrite("url1");

        assertNotNull(outputStream);

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        StreamUtil.close(outputStream);

        outputStream = null;


        byte[] data = fileCache.load("url1");

        assertNotNull(data);

        ImageLoadTask task = new ImageLoadTask(null);

        Bitmap bmp = task.doInBackground("http://www.baidu.com/img/bd_logo1.png", "24", "24");

        assertNotNull(bmp);

    }

}
