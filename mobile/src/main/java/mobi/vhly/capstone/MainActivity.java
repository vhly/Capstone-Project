package mobi.vhly.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import mobi.vhly.capstone.activities.AuthorizeActivity;
import mobi.vhly.capstone.adapters.HeroAdapter;
import mobi.vhly.capstone.client.ClientAPI;
import mobi.vhly.capstone.log.MyLog;
import mobi.vhly.capstone.commonlib.task.TaskCallback;
import mobi.vhly.capstone.commonlib.task.TaskResult;
import mobi.vhly.capstone.model.Profile;
import mobi.vhly.capstone.model.ProfileHero;
import mobi.vhly.capstone.task.ProfileTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskCallback {

    private ArrayList<ProfileHero> mProfileHeroes;
    private HeroAdapter mHeroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyLog.d("MainActivity", "Start process");


        ListView listView = (ListView) findViewById(R.id.list_hero);

        mProfileHeroes = new ArrayList<ProfileHero>();

        mHeroAdapter = new HeroAdapter(this, mProfileHeroes);

        listView.setAdapter(mHeroAdapter);


        ProfileTask task = new ProfileTask(this);
        task.execute("vhly-3282");

        String authorizeUrl = ClientAPI.getAuthorizeUrl();

        MyLog.d("MainActivity", "auth url = " + authorizeUrl);

    }

    @Override
    public void taskStart() {

    }

    @Override
    public void taskFinished(TaskResult result) {
        if (result != null) {
            if (result.action == 1) {
                if (result.data != null) {
                    JSONObject json = (JSONObject) result.data;
                    MyLog.d("MainActivity", "profile json = " + json);

                    try {
                        Profile profile = new Profile();
                        profile.parseJson(json);
                        List<ProfileHero> profileHeros = profile.getProfileHeroes();

                        if (profileHeros != null) {
                            mProfileHeroes.addAll(profileHeros);
                        }

                        mHeroAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public void btnAuthorizeOnClick(View view) {

        Intent intent = new Intent(this, AuthorizeActivity.class);
        startActivity(intent);

    }
}
