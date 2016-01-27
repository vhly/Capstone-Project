package mobi.vhly.capstone.commonlib.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/27
 * Email: vhly@163.com
 */

/**
 * Abstract BaseAdapter for list items which need show in ListView
 *
 * @param <IT> ItemType generic
 */
public abstract class AbstractBaseAdapter<IT> extends BaseAdapter {

    protected Context mContext;

    protected List<IT> mItems;

    public AbstractBaseAdapter(Context context, List<IT> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (mItems != null) {
            ret = mItems.size();
        }
        return ret;
    }

    @Override
    public IT getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected abstract ViewHolder createViewHolder(int position, ViewGroup parent);

    protected abstract void bindView(ViewHolder holder, int position);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret = null;

        ViewHolder holder = null;

        if (convertView != null) {
            ret = convertView;
            holder = (ViewHolder) ret.getTag();
        }

        if (holder == null) {
            holder = createViewHolder(position, parent);
            ret = holder.mItemView;
        }

        if (holder != null) {
            holder.mItemPosition = position;
            bindView(holder, position);
        }

        return ret;
    }

    public static class ViewHolder {

        protected View mItemView;
        protected int mItemPosition;

        protected SparseArrayCompat<View> mChilds;

        public ViewHolder(View itemView) {
            mItemView = itemView;
            mChilds = new SparseArrayCompat<View>();
        }

        public View findViewById(@IdRes int viewId) {
            View ret = null;
            ret = mChilds.get(viewId);
            if (ret == null) {
                ret = mItemView.findViewById(viewId);
                if (ret != null) {
                    mChilds.put(viewId, ret);
                }
            }
            return ret;
        }

        public int getItemPosition() {
            return mItemPosition;
        }

        void setItemPosition(int itemPosition) {
            mItemPosition = itemPosition;
        }
    }

}
