package me.ryanmiles.mysummoner.events;

/**
 * Created by ryanm on 12/19/2015.
 */
public class BaseInfo {
    private String mName;
    private String mRegion;

    public BaseInfo() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setRegion(String region) {
        mRegion = region;
    }

    public String toString() {
        return "Name: " + getName() + " Region: " + getRegion();
    }

}
