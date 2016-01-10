package me.ryanmiles.mysummoner.model;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanm on 12/19/2015.
 */
public class MySummoner {
    private static String sName;
    private static String sRank;
    private static String sIcon;
    private static String sRegion;
    private static Long sId;
    private static int sGamesWon;
    private static int sGamesLost;
    private static int sWinRatio;
    private static int sLevel;
    private static ArrayList<MyChampion> sChamps = new ArrayList<>();

    public MySummoner() {
        sChamps.clear();
    }

    public MySummoner(String name, String rank, String icon) {
        sName = name;
        sRank = rank;
        sIcon = icon;
    }

    public static List<MyChampion> getChamps() {
        return sChamps;
    }

    public static void addChamp(MyChampion champ) {
        sChamps.add(champ);
    }

    public static String getRegion() {
        return sRegion;
    }

    public static void setRegion(String region) {
        sRegion = region;
        RiotAPI.setRegion(Region.valueOf(region));
    }

    public static int getLevel() {
        return sLevel;
    }

    public static void setLevel(int level) {
        sLevel = level;
    }

    public static int getGamesWon() {
        return sGamesWon;
    }

    public static void setGamesWon(int gamesWon) {
        sGamesWon = gamesWon;
    }

    public static int getGamesLost() {
        return sGamesLost;
    }

    public static void setGamesLost(int gamesLost) {
        sGamesLost = gamesLost;
    }

    public static int getWinRatio() {
        return sWinRatio;
    }

    public static void setWinRatio(int winRatio) {
        sWinRatio = winRatio;
    }

    public static Long getId() {
        return sId;
    }

    public static void setId(long id) {
        sId = id;
    }

    public static String getName() {
        return sName;
    }

    public static void setName(String name) {
        sName = name;
    }

    public static String getRank() {
        return sRank;
    }

    public static void setRank(String rank) {
        sRank = rank;
    }

    public static String getIcon() {
        return sIcon;
    }

    public static void setIcon(String icon) {
        sIcon = icon;
    }


    public static void calculateWinRatio() {
        int total = sGamesWon + sGamesLost;
        sWinRatio = (int) (((double) sGamesWon / total) * 100);
    }

    public static String toStringInfo(){
        return "Name: " + sName + " Icon: " + sIcon + " Region: " + sRegion + " Level: " + sLevel;
    }
}
