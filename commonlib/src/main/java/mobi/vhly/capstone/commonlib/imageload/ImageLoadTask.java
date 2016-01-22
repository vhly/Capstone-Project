package mobi.vhly.capstone.commonlib.imageload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ImageView;
import mobi.vhly.capstone.commonlib.cache.FileCache;
import mobi.vhly.capstone.commonlib.cache.ImageCache;
import mobi.vhly.capstone.commonlib.io.HttpUtil;
import mobi.vhly.capstone.commonlib.io.StreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/22
 * Email: vhly@163.com
 */
public class ImageLoadTask extends AsyncTask<String, Integer, Bitmap> {

    private WeakReference<ImageView> mImageViewWeakReference;

    private int mRequestWidth;
    private int mRequestHeight;

    public ImageLoadTask(ImageView imageView) {
        mImageViewWeakReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    public Bitmap doInBackground(String... params) {
        Bitmap ret = null;

        if (params != null) {

            int length = params.length;

            if (length > 0) {

                String url = params[0];

                if (length >= 3) {
                    try {
                        mRequestWidth = Integer.parseInt(params[1]);
                        mRequestHeight = Integer.parseInt(params[2]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        mRequestWidth = 0;
                        mRequestHeight = 0;
                    }
                }

                ImageCache imageCache = ImageCache.getInstance();

                ret = imageCache.getBitmap(url);

                if (ret == null) { // 内存中没有,才从文件获取

                    FileCache fileCache = FileCache.getInstance();

                    InputStream inputStream = fileCache.getStreamForRead(url);

                    if (inputStream == null) {
                        // TODO: Load from Network
                        byte[] data = HttpUtil.doGet(url);

                        if (data != null) {
                            fileCache.save(url, data);
                            data = null;
                            inputStream = fileCache.getStreamForRead(url);
                        }

                    }

                    if (inputStream != null) { // load bitmap data from file

                        if (mRequestWidth > 0 && mRequestHeight > 0) {
                            BitmapFactory.Options options = new BitmapFactory.Options();

                            options.inJustDecodeBounds = true;

                            if (Build.VERSION.SDK_INT >= 19) {
                                inputStream.mark(1024);
                            }
                            BitmapFactory.decodeStream(inputStream, null, options);

                            if (inputStream.markSupported()) {
                                try {
                                    inputStream.reset();
                                } catch (IOException e) {
                                    StreamUtil.close(inputStream);
                                    inputStream = fileCache.getStreamForRead(url);
                                }
                            } else {
                                StreamUtil.close(inputStream);
                                inputStream = fileCache.getStreamForRead(url);
                            }

                            if (inputStream != null) {
                                options.inSampleSize = calcSampleSize(options, mRequestWidth, mRequestHeight);
                                options.inJustDecodeBounds = false;
                                ret = BitmapFactory.decodeStream(inputStream, null, options);
                            }
                        } else {
                            ret = BitmapFactory.decodeStream(inputStream);
                        }

                        StreamUtil.close(inputStream);

                        inputStream = null;
                    }

                    if (ret != null) {

                        ImageCache.getInstance().putBitmap(url, ret);

                    }

                }

            }

        }

        return ret;
    }

    private static int calcSampleSize(BitmapFactory.Options options, int requestWidth, int requestHeight) {
        int ret = 1;
        if (requestWidth > 0 && requestHeight > 0 && options != null) {
            int height = options.outHeight;
            int width = options.outWidth;
            if (height > requestHeight || width > requestWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / ret) >= requestHeight
                        && (halfWidth / ret) >= requestWidth) {
                    ret *= 2;
                }
            }
        }
        return ret;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            if (mImageViewWeakReference != null) {
                ImageView imageView = mImageViewWeakReference.get();
                if (imageView != null) {
                    Drawable drawable = imageView.getDrawable();
                    if (drawable instanceof AsyncBitmapDrawable) {
                        AsyncBitmapDrawable asyncBitmapDrawable = (AsyncBitmapDrawable) drawable;
                        ImageLoadTask currentTask = asyncBitmapDrawable.getCurrentTask();
                        if (this == currentTask) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }else {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }
    }
}
