package me.ryanmiles.mysummoner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.pixplicity.easyprefs.library.Prefs;
import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;

import de.greenrobot.event.EventBus;
import me.ryanmiles.mysummoner.data.Constants;
import me.ryanmiles.mysummoner.data.RiotApiHelper;
import me.ryanmiles.mysummoner.events.BaseInfo;
import me.ryanmiles.mysummoner.events.BasicInfo;
import me.ryanmiles.mysummoner.fragments.DialogInfoFragment;
import me.ryanmiles.mysummoner.fragments.SummonerInfoFragment;
import me.ryanmiles.mysummoner.model.MySummoner;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private SummonerInfoFragment mSummonerInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (Prefs.getString(Constants.NAME, "").equals("") || Prefs.getString(Constants.REGION, "").equals("")) {
            showInfoDialog();
        } else {
            initRiotApi();
            summonerInfoFragment(savedInstanceState);
        }

    }

    private void summonerInfoFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mSummonerInfoFragment = new SummonerInfoFragment();


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mSummonerInfoFragment)
                    .commit();
        }
    }

    private void initRiotApi() {
        new MySummoner().setName(Prefs.getString(Constants.NAME, ""));
        RiotAPI.setRegion(Region.valueOf(Prefs.getString(Constants.REGION, "")));
        MySummoner.setRegion(Prefs.getString(Constants.REGION, ""));
        RiotApiHelper.setVersion();
    }

    private void showInfoDialog() {
        DialogInfoFragment dialogFragment = new DialogInfoFragment();
        dialogFragment.show(getSupportFragmentManager(), "Sample Fragment");
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(BasicInfo basicInfo) {
        Timber.d("onEventMainThread() called with: " + "basicInfo = [" + basicInfo + "]");
        mSummonerInfoFragment.updateBasicInfo();
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(BaseInfo baseInfo) {
        Timber.d("onEventMainThread() called with: " + "baseInfo = [" + baseInfo + "]");
        Prefs.putString(Constants.NAME, baseInfo.getName());
        Prefs.putString(Constants.REGION, baseInfo.getRegion());
        initRiotApi();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}