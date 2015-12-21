package me.ryanmiles.mysummoner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.ryanmiles.mysummoner.R;
import me.ryanmiles.mysummoner.data.RiotApiHelper;
import me.ryanmiles.mysummoner.model.MySummoner;

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
    @Bind(R.id.won_games_text_view)
    TextView summonerWonGames;
    @Bind(R.id.lost_games_text_view)
    TextView summonerLostGames;
    @Bind(R.id.win_ratio_text_view)
    TextView summonerWinRatio;
    @Bind(R.id.summoner_level)
    TextView summonerLevel;
    @Bind(R.id.summoner_region)
    TextView summonerRegion;
    @Bind(R.id.total_games_tezt_view)
    TextView summonerTotalGames;
    @Bind(R.id.double_kills_text_view)
    TextView summonerDoubleKills;
    @Bind(R.id.triple_kills_text_view)
    TextView summonerTripleKills;
    @Bind(R.id.quada_kills_text_view)
    TextView summonerQuadaKills;
    @Bind(R.id.penta_kills_text_view)
    TextView summonerPentaKills;

    public SummonerInfoFragment() {

    }

    public static SummonerInfoFragment newInstance() {
        SummonerInfoFragment fragment = new SummonerInfoFragment();
        return fragment;
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
        return v;
    }

    public void updateBasicInfo() {
        Picasso.with(getActivity()).load(MySummoner.getIcon()).resize(190, 190).into(icon);
        summonerName.setText(MySummoner.getName());
        summonerRank.setText(MySummoner.getRank());
        summonerWonGames.setText(MySummoner.getGamesWon() + "");
        summonerLostGames.setText(MySummoner.getGamesLost() + "");
        summonerWinRatio.setText(MySummoner.getWinRatio() + "%");
        summonerLevel.setText("Level: " + MySummoner.getLevel());
        summonerRegion.setText("Region: " + MySummoner.getRegion());
        summonerTotalGames.setText(MySummoner.getGamesWon() + MySummoner.getGamesLost() + "");
        summonerDoubleKills.setText(MySummoner.getTotalDoubleKills() + "");
        summonerTripleKills.setText(MySummoner.getTotalTripleKills() + "");
        summonerQuadaKills.setText(MySummoner.getTotalQuadraKills() + "");
        summonerPentaKills.setText(MySummoner.getTotalPentaKills() + "");

    }
}
