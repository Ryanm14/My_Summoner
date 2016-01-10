package me.ryanmiles.mysummoner.data;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Season;
import com.robrua.orianna.type.core.common.Side;
import com.robrua.orianna.type.core.league.League;
import com.robrua.orianna.type.core.league.LeagueEntry;
import com.robrua.orianna.type.core.match.Match;
import com.robrua.orianna.type.core.match.MatchTeam;
import com.robrua.orianna.type.core.match.Participant;
import com.robrua.orianna.type.core.matchlist.MatchReference;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.core.stats.AggregatedStats;
import com.robrua.orianna.type.core.stats.ChampionStats;
import com.robrua.orianna.type.core.summoner.Summoner;
import com.robrua.orianna.type.exception.APIException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import me.ryanmiles.mysummoner.App;
import me.ryanmiles.mysummoner.events.BasicInfo;
import me.ryanmiles.mysummoner.events.ExceptionHandle;
import me.ryanmiles.mysummoner.events.MatchStats;
import me.ryanmiles.mysummoner.events.TopChampions;
import me.ryanmiles.mysummoner.model.MyChampion;
import me.ryanmiles.mysummoner.model.MyMatchStats;
import me.ryanmiles.mysummoner.model.MySummoner;
import timber.log.Timber;

/**
 * Created by Ryan on 12/1/2015.
 */
public class RiotApiHelper {
    private static Summoner summoner;
    public static void getBasicInfo() {
        Timber.d("GetBasicInfo()");
        Thread thread = new Thread() {
            @Override
            public void run() {
                new MySummoner();
                App.LOLVERSION = RiotAPI.getVersions().get(0);

                try {
                    summoner = getSummonerFromName();
                    getTopChamps();
                    getMatchStats();
                    getSummonerIconId();
                    summonerGetRankLevel();
                } catch (APIException e) {
                    EventBus.getDefault().post(new ExceptionHandle(e));
                }catch (Exception e){
                    Timber.d(String.valueOf(e));
                } finally {
                    EventBus.getDefault().post(new BasicInfo());
                }


            }
        };
        thread.start();
    }

    private static void summonerGetRankLevel() {
        Timber.d("summonerGetRankLevel() called");
        League league = RiotAPI.getLeagueEntriesBySummonerID(MySummoner.getId()).get(0);
        LeagueEntry leagueEntry = league.getEntries().get(0);
        MySummoner.setRank(league.getTier().toString() + " " + leagueEntry.getDivision() + " " + leagueEntry.getLeaguePoints() + "LP");
        MySummoner.setGamesWon(leagueEntry.getWins());
        MySummoner.setGamesLost(leagueEntry.getLosses());
        MySummoner.calculateWinRatio();
        MySummoner.setLevel((int) summoner.getLevel());
        Timber.d("summonerGetRankLevel() returned: " + MySummoner.toStringInfo());
    }

    private static void getSummonerIconId() {
        Timber.d("getSummonerIconId() called");
        MySummoner.setId(summoner.getID());
        MySummoner.setIcon("http://ddragon.leagueoflegends.com/cdn/" + App.LOLVERSION + "/img/profileicon/" + summoner.getProfileIconID() + ".png");
    }

    private static Summoner getSummonerFromName() {
        Timber.d("getSummonerFromName() returned: " + summoner);
        return RiotAPI.getSummonerByName(MySummoner.getName());

    }

    public static void getMatchStats() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                int begin = 0;
                if (MySummoner.getGamesWon() + MySummoner.getGamesLost() > 10) {
                    begin = MySummoner.getGamesWon() + MySummoner.getGamesLost() - 10;
                }
                List<MatchReference> matches = summoner.getMatchList();
                for (int i = 0; i < 15; i++) {
                    MatchReference match = matches.get(i);
                    long champId = match.getChampionID();
                    long matchId = match.getID();
                    Side team = Side.BLUE;
                    Match playedMatch = RiotAPI.getMatch(matchId);
                    MatchTeam summonerTeam = null;
                    for (Participant participant : playedMatch.getParticipants()) {
                        if (participant.getChampionID() == champId) {
                            team = participant.getTeam();
                        }

                        if (team == Side.BLUE) {
                            summonerTeam = playedMatch.getTeams().get(0);
                        } else {
                            summonerTeam = playedMatch.getTeams().get(1);
                        }

                    }
                    MyMatchStats.addNum_of_matches();
                    if (summonerTeam.getWinner()) {
                        MyMatchStats.addNum_of_wins();
                        if (summonerTeam.getFirstBaron()) {
                            MyMatchStats.addFirst_baron_win();
                        }
                        if (summonerTeam.getFirstBlood()) {
                            MyMatchStats.addFirst_blood_win();
                        }
                        if (summonerTeam.getFirstDragon()) {
                            MyMatchStats.addFirst_dragon_win();
                        }
                        if (summonerTeam.getFirstInhibitor()) {
                            MyMatchStats.addFirst_inhib_win();
                        }
                    }else{
                        if (summonerTeam.getFirstBaron()) {
                            MyMatchStats.addFirst_baron_lost();
                        }
                        if (summonerTeam.getFirstBlood()) {
                            MyMatchStats.addFirst_blood_loss();
                        }
                        if (summonerTeam.getFirstDragon()) {
                            MyMatchStats.addFirst_dragon_loss();
                        }
                        if (summonerTeam.getFirstInhibitor()) {
                            MyMatchStats.addFirst_inhib_loss();
                        }
                    }
                }
                Timber.d(Arrays.toString(MyMatchStats.getStatNames()) + " " + MyMatchStats.getStatValues());
                EventBus.getDefault().post(new MatchStats());
            }
        };
        thread.start();
    }

    public static void getTopChamps(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    setChampStats(getTop5Champs());
                }  catch (APIException e) {
                    EventBus.getDefault().post(new ExceptionHandle(e));
                } finally {
                    EventBus.getDefault().post(new TopChampions());
                }
            }
        };
        thread.start();
    }

    private static void setChampStats(ArrayList<ChampionStats> top5Champs) {
        Timber.d("setChampStats() called with: " + "top5Champs = [" + top5Champs + "]");
        for (ChampionStats top5champ : top5Champs) {
            MyChampion myChampion = new MyChampion();
            AggregatedStats asStats = top5champ.getStats();
            myChampion.setName(top5champ.getChampion().getName());
            myChampion.setImage(top5champ.getChampion().getImage().getFull());
            myChampion.setGames_played(asStats.getTotalGamesPlayed());
            myChampion.setGames_won(asStats.getTotalWins());
            myChampion.setGames_lost(asStats.getTotalLosses());
            myChampion.setTotal_assists(asStats.getTotalAssists());
            myChampion.setTotal_deaths(asStats.getTotalDeaths());
            myChampion.setTotal_kills(asStats.getTotalKills());
            myChampion.setTotal_creep_kills(asStats.getTotalMinionKills());
            MySummoner.addChamp(myChampion);
        }
        Timber.d("setChampStats() returned");
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


    public static ArrayList<ChampionStats> getTop5Champs() {
        ArrayList<ChampionStats> top5champs = new ArrayList<>();
        Map<Champion, ChampionStats> champs = summoner.getRankedStats(Season.SEASON2015);
        ArrayList<ChampionStats> allChampStats = new ArrayList<>(champs.values());
        for (int i = 0; i < 6; i++) {
            ChampionStats champstat = null;
            int total_games = 0;
            for (ChampionStats allChampStat : allChampStats) {
                if (allChampStat.getStats().getTotalGamesPlayed() > total_games) {
                    total_games = allChampStat.getStats().getTotalGamesPlayed();
                    champstat = allChampStat;
                }
            }
            allChampStats.remove(champstat);
            top5champs.add(champstat);
        }
        top5champs.remove(0);
        return top5champs;
    }
}
