package mobi.vhly.capstone.commonlib.imageload;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */
public class AsyncBitmapDrawable extends BitmapDrawable {

    private WeakReference<ImageLoadTask> mTaskReference;

    public AsyncBitmapDrawable(Resources res, Bitmap bitmap) {
        super(res, bitmap);
    }

    public void setImageLoadTask(ImageLoadTask task) {
        mTaskReference = new WeakReference<ImageLoadTask>(task);
    }

    public ImageLoadTask getCurrentTask() {
        ImageLoadTask ret = null;
        if (mTaskReference != null) {
            ret = mTaskReference.get();
        }

        return ret;
    }


}
