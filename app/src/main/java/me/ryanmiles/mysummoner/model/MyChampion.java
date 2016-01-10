package me.ryanmiles.mysummoner.model;

import me.ryanmiles.mysummoner.App;

/**
 * Created by ryanm on 12/21/2015.
 */
public class MyChampion {
    private String image;
    private String name;
    private int games_played;
    private int games_lost;
    private int games_won;
    private int total_kills;
    private int total_deaths;
    private int total_assists;
    private int total_creep_kills;

    public MyChampion() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = "http://ddragon.leagueoflegends.com/cdn/" + App.LOLVERSION + "/img/champion/" + image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGames_played() {
        return games_played;
    }

    public void setGames_played(int games_played) {
        this.games_played = games_played;
    }

    public int getGames_lost() {
        return games_lost;
    }

    public void setGames_lost(int games_lost) {
        this.games_lost = games_lost;
    }

    public int getGames_won() {
        return games_won;
    }

    public void setGames_won(int games_won) {
        this.games_won = games_won;
    }

    public int getTotal_kills() {
        return total_kills;
    }

    public void setTotal_kills(int total_kills) {
        this.total_kills = total_kills;
    }

    public int getTotal_deaths() {
        return total_deaths;
    }

    public void setTotal_deaths(int total_deaths) {
        this.total_deaths = total_deaths;
    }

    public int getTotal_assists() {
        return total_assists;
    }

    public void setTotal_assists(int total_assists) {
        this.total_assists = total_assists;
    }

    public int getTotal_creep_kills() {
        return total_creep_kills;
    }

    public void setTotal_creep_kills(int total_creep_kills) {
        this.total_creep_kills = total_creep_kills;
    }

    public double getAvgKills() {
        return Math.round((total_kills * 1.0 / games_played) * 10) / 10.0;
    }

    public double getAvgDeaths() {
        return Math.round((total_deaths * 1.0 / games_played) * 10) / 10.0;
    }

    public double getAvgAssists() {
        return Math.round((total_assists * 1.0 / games_played) * 10) / 10.0;
    }

    public double getAvgCs() {
        return Math.round((total_creep_kills * 1.0 / games_played) * 10) / 10.0;
    }

    public int getWinRatio() {
        return (int) Math.round((games_won * 1.0 / games_played * 100));
    }

    public double getKdaToOne() {
        return Math.round(((getAvgKills() + getAvgAssists()) / getAvgDeaths()) * 100.0) / 100.0;
    }
}
