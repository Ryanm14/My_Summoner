package me.ryanmiles.mysummoner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.ryanmiles.mysummoner.R;
import me.ryanmiles.mysummoner.adapters.SummonerStatsAdapter;
import me.ryanmiles.mysummoner.adapters.TopChampAdapter;
import me.ryanmiles.mysummoner.data.Constants;
import me.ryanmiles.mysummoner.data.RiotApiHelper;
import me.ryanmiles.mysummoner.model.MyChampion;
import me.ryanmiles.mysummoner.model.MyMatchStats;
import me.ryanmiles.mysummoner.model.MySummoner;
import timber.log.Timber;

import static com.google.android.gms.internal.zzir.runOnUiThread;

/**
 * Created by ryanm on 12/19/2015.
 */
public class SummonerInfoFragment extends Fragment {

    @Bind(R.id.summoner_icon_image_view)
    ImageView icon;
    @Bind(R.id.summoner_name_text_view)
    TextView summonerName;
    @Bind(R.id.summoner_rank_text_view)
    TextView summonerRank;
    @Bind(R.id.summoner_level)
    TextView summonerLevel;
    @Bind(R.id.summoner_region)
    TextView summonerRegion;
    @Bind(R.id.top_champions)
    ListView topChampions;
    @Bind(R.id.grid_view_summoner_stats)
    GridView summonerStats;
    @Bind(R.id.loading_top_info)
    ProgressBar loadTopInfo;
    @Bind(R.id.loading_top_champ_info)
    ProgressBar loadTopChampInfo;
    @Bind(R.id.loading_match_stats_info)
    ProgressBar loadMatchStatsInfo;
    @Bind(R.id.adView)
    AdView mAdView;

    public SummonerInfoFragment() {

    }

    public static SummonerInfoFragment newInstance() {
        return new SummonerInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RiotApiHelper.getBasicInfo();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_summoner_info, container, false);
        ButterKnife.bind(this, v);
        setupAdds();
        return v;
    }

    public void updateBasicInfo() {
        loadTopInfo.setVisibility(View.GONE);
        Picasso.with(getActivity()).load(MySummoner.getIcon()).resize(190, 190).into(icon);
        summonerName.setText(MySummoner.getName());
        summonerRank.setText(MySummoner.getRank());
        summonerLevel.setText("Level: " + MySummoner.getLevel());
        summonerRegion.setText("Region: " + MySummoner.getRegion());
    }


    public void updateTopChampions() {
        loadTopChampInfo.setVisibility(View.GONE);
        topChampions.setAdapter(new TopChampAdapter(getActivity(), (ArrayList<MyChampion>) MySummoner.getChamps()));
    }

    public void updateMatchStats() {
        loadMatchStatsInfo.setVisibility(View.GONE);
        summonerStats.setAdapter(new SummonerStatsAdapter(getActivity(), MyMatchStats.getStatNames()));
    }

    public void setupAdds() {
        if (Prefs.getString(Constants.ADD, "true").equals("true")) {
            Timber.d("Ads Enabled");
            Thread adThread = new Thread() {
                @Override
                public void run() {
                    loadAd();
                }
            };
            adThread.start();
        } else {
            mAdView.setVisibility(View.GONE);
            Timber.d("Ads Disabled");
        }
    }

    private void loadAd() {
        final AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("93AD974A71708301A28232203FD0488C")
                .build();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdView.setVisibility(View.VISIBLE);
                mAdView.loadAd(adRequest);

            }
        });
    }
}
