package me.ryanmiles.mysummoner.data;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.league.League;
import com.robrua.orianna.type.core.league.LeagueEntry;
import com.robrua.orianna.type.core.stats.AggregatedStats;
import com.robrua.orianna.type.core.stats.PlayerStatsSummary;
import com.robrua.orianna.type.core.stats.PlayerStatsSummaryType;
import com.robrua.orianna.type.core.summoner.Summoner;

import java.util.Map;

import de.greenrobot.event.EventBus;
import me.ryanmiles.mysummoner.App;
import me.ryanmiles.mysummoner.events.BasicInfo;
import me.ryanmiles.mysummoner.model.MySummoner;
import timber.log.Timber;

/**
 * Created by Ryan on 12/1/2015.
 */
public class RiotApiHelper {

    public static void getBasicInfo() {
        Timber.d("GetBasicInfo()");
        Thread thread = new Thread() {
            @Override
            public void run() {
                Summoner summoner = RiotAPI.getSummonerByName(MySummoner.getName());
                MySummoner.setId(summoner.getID());
                MySummoner.setIcon("http://ddragon.leagueoflegends.com/cdn/" + App.LOLVERSION + "/img/profileicon/" + summoner.getProfileIconID() + ".png");

                League league = RiotAPI.getLeagueEntriesBySummonerID(MySummoner.getId()).get(0);

                LeagueEntry leagueEntry = league.getEntries().get(0);
                MySummoner.setRank(league.getTier().toString() + " " + leagueEntry.getDivision() + " " + leagueEntry.getLeaguePoints() + "LP");
                MySummoner.setGamesWon(leagueEntry.getWins());
                MySummoner.setGamesLost(leagueEntry.getLosses());
                MySummoner.calculateWinRatio();
                MySummoner.setLevel((int) summoner.getLevel());
                Map<PlayerStatsSummaryType, PlayerStatsSummary> stats = summoner.getStats();
                AggregatedStats as = stats.get(PlayerStatsSummaryType.RankedSolo5x5).getAggregatedStats();
                MySummoner.setTotalAssists(as.getTotalAssists());
                MySummoner.setKillingSpree(as.getMaxKillingSpree());
                MySummoner.setMaxTimeSpentLiving(as.getMaxTimeSpentLiving());
                MySummoner.setTotalChampionKills(as.getTotalKills());
                MySummoner.setTotalDoubleKills(as.getTotalDoubleKills());
                MySummoner.setTotalFirstBlood(as.getTotalFirstBloods());
                MySummoner.setTotalPentaKills(as.getTotalPentaKills());
                MySummoner.setTotalQuadraKills(as.getTotalQuadraKills());
                MySummoner.setTotalTripleKills(as.getTotalTripleKills());
                MySummoner.setTotalTurretsKilled(as.getTotalTurretsKilled());
                MySummoner.setMostKills(as.getMaxKills());
                EventBus.getDefault().post(new BasicInfo());
            }
        };
        thread.start();
    }

    public static void setVersion() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                App.LOLVERSION = RiotAPI.getVersions().get(0);
            }
        };
        thread.start();
    }


}
