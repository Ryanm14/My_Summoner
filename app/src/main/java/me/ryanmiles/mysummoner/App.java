package me.ryanmiles.mysummoner;

import android.app.Application;
import android.content.ContextWrapper;
import android.os.StrictMode;

import com.pixplicity.easyprefs.library.Prefs;
import com.robrua.orianna.api.core.RiotAPI;

import timber.log.Timber;

/**
 * Created by ryanm on 12/19/2015.
 */
public class App extends Application {

    public static String LOLVERSION;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());

            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyFlashScreen()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());

            Timber.d("Strict mode and Timber initialized");
        } else {
            // TODO Crashlytics.start(this);
            // TODO Timber.plant(new CrashlyticsTree());
        }
        // TODO: 11/28/2015 Change and save Regions and Strict Mode
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        RiotAPI.setAPIKey(getString(R.string.api_key));


    }
}
