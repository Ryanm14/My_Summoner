package me.ryanmiles.mysummoner.model;

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
    private static int sKillingSpree;
    private static int sMostKills;
    private static int sTotalChampionKills;
    private static int sMaxTimeSpentLiving;
    private static int sTotalAssists;
    private static int sTotalFirstBlood;
    private static int sTotalDoubleKills;
    private static int sTotalPentaKills;
    private static int sTotalQuadraKills;
    private static int sTotalTripleKills;
    private static int sTotalTurretsKilled;

    public MySummoner() {

    }

    public MySummoner(String name, String rank, String icon) {
        sName = name;
        sRank = rank;
        sIcon = icon;
    }

    public static int getKillingSpree() {
        return sKillingSpree;
    }

    public static void setKillingSpree(int killingSpree) {
        sKillingSpree = killingSpree;
    }

    public static int getMostKills() {
        return sMostKills;
    }

    public static void setMostKills(int mostKills) {
        sMostKills = mostKills;
    }

    public static int getTotalChampionKills() {
        return sTotalChampionKills;
    }

    public static void setTotalChampionKills(int totalChampionKills) {
        sTotalChampionKills = totalChampionKills;
    }

    public static int getMaxTimeSpentLiving() {
        return sMaxTimeSpentLiving;
    }

    public static void setMaxTimeSpentLiving(int maxTimeSpentLiving) {
        sMaxTimeSpentLiving = maxTimeSpentLiving;
    }

    public static int getTotalAssists() {
        return sTotalAssists;
    }

    public static void setTotalAssists(int totalAssists) {
        sTotalAssists = totalAssists;
    }

    public static int getTotalFirstBlood() {
        return sTotalFirstBlood;
    }

    public static void setTotalFirstBlood(int totalFirstBlood) {
        sTotalFirstBlood = totalFirstBlood;
    }

    public static int getTotalDoubleKills() {
        return sTotalDoubleKills;
    }

    public static void setTotalDoubleKills(int totalDoubleKills) {
        sTotalDoubleKills = totalDoubleKills;
    }

    public static int getTotalPentaKills() {
        return sTotalPentaKills;
    }

    public static void setTotalPentaKills(int totalPentaKills) {
        sTotalPentaKills = totalPentaKills;
    }

    public static int getTotalQuadraKills() {
        return sTotalQuadraKills;
    }

    public static void setTotalQuadraKills(int totalQuadraKills) {
        sTotalQuadraKills = totalQuadraKills;
    }

    public static int getTotalTripleKills() {
        return sTotalTripleKills;
    }

    public static void setTotalTripleKills(int totalTripleKills) {
        sTotalTripleKills = totalTripleKills;
    }

    public static int getTotalTurretsKilled() {
        return sTotalTurretsKilled;
    }

    public static void setTotalTurretsKilled(int totalTurretsKilled) {
        sTotalTurretsKilled = totalTurretsKilled;
    }

    public static String getRegion() {
        return sRegion;
    }

    public static void setRegion(String region) {
        sRegion = region;
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
}
