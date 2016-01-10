package me.ryanmiles.mysummoner;

import android.app.Application;
import android.content.ContextWrapper;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.pixplicity.easyprefs.library.Prefs;
import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.api.RateLimit;

import io.fabric.sdk.android.Fabric;
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
           // Picasso.with(this).setLoggingEnabled(true);
            Timber.d("Strict mode and Timber initialized");
        } else {
            Fabric.with(this, new Crashlytics());
        }
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        RiotAPI.setAPIKey(getString(R.string.api_key));
        RiotAPI.setRateLimit(new RateLimit(3000, 10), new RateLimit(180000, 600));


    }
}
