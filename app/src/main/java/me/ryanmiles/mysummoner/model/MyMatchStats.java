package me.ryanmiles.mysummoner.model;

import java.util.ArrayList;

/**
 * Created by ryanm on 12/21/2015.
 */
public class MyMatchStats {
    private static int first_dragon_win;
    private static int num_of_matches;
    private static int first_blood_win;
    private static int num_of_wins;
    private static int first_inhib_win;
    private static int first_baron_win;
    private static int first_baron_loss;
    private static int first_blood_loss;
    private static int first_dragon_loss;
    private static int first_inhib_loss;

    public static int getFirst_dragon_win() {
        return first_dragon_win / num_of_matches;
    }

    public static void addFirst_dragon_win() {
        first_dragon_win++;
    }

    public static int getNum_of_matches() {
        return num_of_matches;
    }

    public static void addNum_of_matches() {
        num_of_matches++;
    }

    public static int getFirst_blood_win() {
        return first_blood_win / num_of_matches;
    }

    public static void addFirst_blood_win() {
        first_blood_win++;
    }

    public static int getNum_of_wins() {
        return num_of_wins;
    }

    public static void addNum_of_wins() {
        num_of_wins++;
    }

    public static int getFirst_inhib_win() {
        return first_inhib_win / num_of_matches;
    }

    public static void addFirst_inhib_win() {
        first_inhib_win++;
    }

    public static int getFirst_baron_win() {
        return first_baron_win / num_of_matches;
    }

    public static void addFirst_baron_win() {
        first_baron_win++;
    }

    public static String[] getStatNames() {
        return new String[]{"First Blood", "First Dragon", "First Inhib", "First Baron"};
    }

    public static ArrayList<Integer> getStatValues() {
        ArrayList<Integer> statValues = new ArrayList<>();
        statValues.add((int) ((first_blood_win * 1.0 / (first_blood_win + first_blood_loss)) * 100));
        statValues.add((int) ((first_dragon_win * 1.0 / (first_dragon_win + first_dragon_loss)) * 100));
        statValues.add((int) ((first_inhib_win * 1.0 / (first_inhib_win + first_inhib_loss)) * 100));
        if(first_baron_win == 0 && first_baron_loss == 0){
            statValues.add(-1);
        }else {
            statValues.add((int) ((first_baron_win * 1.0 / (first_baron_win + first_baron_loss)) * 100));
        }
        return statValues;
    }

    public static void addFirst_baron_lost() {
        first_baron_loss++;
    }

    public static void addFirst_blood_loss() {
        first_blood_loss++;
    }

    public static void addFirst_dragon_loss() {
        first_dragon_loss++;
    }

    public static void addFirst_inhib_loss() {
        first_inhib_loss++;
    }
}
