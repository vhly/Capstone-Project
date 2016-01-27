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
import mobi.vhly.capstone.conf.GameConfiguration;
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

        // --------------------------
        // hero name

        TextView txtName = (TextView) holder.findViewById(R.id.item_hero_name);
        txtName.setText(hero.getName());

        // --------------------------
        // hero level & paragon level

        int level = hero.getLevel();
        int paragonLevel = hero.getParagonLevel();

        TextView txtLevel = (TextView) holder.findViewById(R.id.item_hero_level);
        txtLevel.setText(Integer.toString(level));

        TextView txtParagonLevel = (TextView) holder.findViewById(R.id.item_hero_paragon_level);
        txtParagonLevel.setText("(" + paragonLevel + ")");


        // --------------------------
        // hero type

        String heroClass = hero.getHeroClass(); // hero type english

        String heroTypeName = GameConfiguration.getInstance().getHeroTypeName(heroClass);

        TextView txtType = (TextView) holder.findViewById(R.id.item_hero_type);
        txtType.setText(heroTypeName);

        // --------------------------
        // hero icon

        int gender = hero.getGender(); // 1 for woman, 0 for man

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

        // --------------------------
        // icon load to ImageView

        String iconUrl = ClientAPI.getD3HeroIconUrl("64", heroClass);

        ImageView imgIcon = (ImageView) holder.findViewById(R.id.item_hero_icon);

        ImageLoadTask task = new ImageLoadTask(imgIcon);
        task.execute(iconUrl);

    }
}
