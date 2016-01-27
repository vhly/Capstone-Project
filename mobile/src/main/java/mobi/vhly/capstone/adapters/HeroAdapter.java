package mobi.vhly.capstone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import mobi.vhly.capstone.R;
import mobi.vhly.capstone.client.ClientAPI;
import mobi.vhly.capstone.commonlib.adapters.AbstractBaseAdapter;
import mobi.vhly.capstone.commonlib.imageload.ImageLoadTask;
import mobi.vhly.capstone.model.ProfileHero;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/27
 * Email: vhly@163.com
 */
public class HeroAdapter extends AbstractBaseAdapter<ProfileHero> {

    public HeroAdapter(Context context, List<ProfileHero> items) {
        super(context, items);
    }

    @Override
    protected ViewHolder createViewHolder(int position, ViewGroup parent) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_hero, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void bindView(ViewHolder holder, int position) {
        ProfileHero hero = mItems.get(position);

        TextView txtName = (TextView) holder.findViewById(R.id.item_hero_name);
        txtName.setText(hero.getName());

        int gender = hero.getGender(); // 1 for woman, 0 for man

        String heroClass = hero.getHeroClass(); // hero type english

        heroClass = heroClass.replaceAll("-", "");

        if (heroClass.equals("crusader")) {
            heroClass = "x1_" + heroClass;
        }

        switch (gender) {
            case 0:
                heroClass += "_male";
                break;
            case 1:
                heroClass += "_female";
                break;
        }

        String iconUrl = ClientAPI.getD3HeroIconUrl("64", heroClass);

        ImageView imgIcon = (ImageView) holder.findViewById(R.id.item_hero_icon);

        ImageLoadTask task = new ImageLoadTask(imgIcon);
        task.execute(iconUrl);

    }
}
