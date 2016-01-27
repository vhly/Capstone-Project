package mobi.vhly.capstone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import mobi.vhly.capstone.Constants;
import mobi.vhly.capstone.R;
import mobi.vhly.capstone.commonlib.task.TaskCallback;
import mobi.vhly.capstone.commonlib.task.TaskResult;
import mobi.vhly.capstone.log.MyLog;
import mobi.vhly.capstone.model.Hero;
import mobi.vhly.capstone.task.HeroInfoTask;


public class HeroInfoActivity extends AppCompatActivity implements TaskCallback {

    public static final String EXTRA_HERO_ID = "ehi";
    public static final String EXTRA_BATTLE_TAG = "ebt";

    private long mHeroId;

    private String mBattleTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_info);

        Intent intent = getIntent();
        mBattleTag = intent.getStringExtra(EXTRA_BATTLE_TAG);
        mHeroId = intent.getLongExtra(EXTRA_HERO_ID, -1);
        if (mBattleTag != null && mHeroId > 0) {
            HeroInfoTask task = new HeroInfoTask(this);
            task.execute(mBattleTag, Long.toString(mHeroId));
        }
    }

    @Override
    public void taskStart() {

    }

    @Override
    public void taskFinished(TaskResult result) {
        if (result != null) {
            int action = result.action;
            Object data = result.data;
            if (action == Constants.TASK_ACTION_PROFILE_HERO) {

                if (data != null) { // split if for data type;
                    if (data instanceof Hero) {
                        Hero hero = (Hero) data;
                        MyLog.d("Hero", "hero " + hero);
                    }
                }

            }
        }
    }
}
