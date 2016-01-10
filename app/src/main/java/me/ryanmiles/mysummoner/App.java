package me.ryanmiles.mysummoner;

import android.app.Application;
import android.content.ContextWrapper;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.pixplicity.easyprefs.library.Prefs;
import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.api.LoadPolicy;
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
            Timber.plant(new CrashlyticsTree());
        }
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        RiotAPI.setAPIKey(getString(R.string.api_key));
        RiotAPI.setLoadPolicy(LoadPolicy.UPFRONT);
        RiotAPI.setRateLimit(new RateLimit(3000, 10), new RateLimit(180000, 600));


    }
}

class CrashlyticsTree extends Timber.Tree {
    private static final String CRASHLYTICS_KEY_PRIORITY = "priority";
    private static final String CRASHLYTICS_KEY_TAG = "tag";
    private static final String CRASHLYTICS_KEY_MESSAGE = "message";

    @Override
    protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return;
        }

        Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority);
        Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag);
        Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message);

        if (t == null) {
            Crashlytics.logException(new Exception(message));
        } else {
            Crashlytics.logException(t);
        }
    }
}
