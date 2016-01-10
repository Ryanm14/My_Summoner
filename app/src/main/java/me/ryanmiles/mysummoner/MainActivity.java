package me.ryanmiles.mysummoner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.pixplicity.easyprefs.library.Prefs;

import de.greenrobot.event.EventBus;
import me.ryanmiles.mysummoner.data.Constants;
import me.ryanmiles.mysummoner.data.RiotApiHelper;
import me.ryanmiles.mysummoner.events.BaseInfo;
import me.ryanmiles.mysummoner.events.BasicInfo;
import me.ryanmiles.mysummoner.events.ErrorSummonerName;
import me.ryanmiles.mysummoner.events.ExceptionHandle;
import me.ryanmiles.mysummoner.events.MatchStats;
import me.ryanmiles.mysummoner.events.TopChampions;
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
        MySummoner.setRegion(Prefs.getString(Constants.REGION, ""));
        RiotApiHelper.setVersion();
        initCrashlyticsInfo();
    }

    private void initCrashlyticsInfo() {
        Crashlytics.setUserIdentifier(MySummoner.getName() + ":" + MySummoner.getRegion());
        Crashlytics.setString(Constants.NAME, MySummoner.getName());
        Crashlytics.setString(Constants.REGION, MySummoner.getRegion());
    }

    private void showInfoDialog() {
        DialogInfoFragment dialogFragment = new DialogInfoFragment();
        dialogFragment.show(getSupportFragmentManager(), "Sample Fragment");
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(BasicInfo basicInfo) {
        mSummonerInfoFragment.updateBasicInfo();
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(TopChampions topChampions) {
        mSummonerInfoFragment.updateTopChampions();
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(MatchStats matchStats) {
        mSummonerInfoFragment.updateMatchStats();
        Toast.makeText(this, "Data Updated!", Toast.LENGTH_SHORT).show();
    }


    @SuppressWarnings("unused")
    public void onEventMainThread(BaseInfo baseInfo) {
        Timber.d("onEventMainThread() called with: " + "baseInfo = [" + baseInfo + "]");
        Prefs.putString(Constants.NAME, baseInfo.getName());
        Prefs.putString(Constants.REGION, baseInfo.getRegion());
        initRiotApi();
        startActivity(new Intent(this, MainActivity.class));
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(ErrorSummonerName errorSummonerName) {
        Toast.makeText(this, "Check Summoner Name!", Toast.LENGTH_LONG).show();
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(ExceptionHandle e) {
        Timber.i(String.valueOf(e));
        Toast.makeText(this, e.getErrorString(), Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                RiotApiHelper.getBasicInfo();
                return true;

            case R.id.action_change_summoner:
                showInfoDialog();
                return true;
            case R.id.action_toggle_ads:
                if (Prefs.getString(Constants.ADD, "true").equals("true")) {
                    Prefs.putString(Constants.ADD, "false");
                    Toast.makeText(MainActivity.this, "Ads Disabled!", Toast.LENGTH_LONG).show();
                } else {
                    Prefs.putString(Constants.ADD, "true");
                    Toast.makeText(MainActivity.this, "Ads Enabled <3", Toast.LENGTH_LONG).show();
                }
                mSummonerInfoFragment.setupAdds();

                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}