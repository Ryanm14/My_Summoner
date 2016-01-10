package me.ryanmiles.mysummoner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.ArrayList;

import me.ryanmiles.mysummoner.R;
import me.ryanmiles.mysummoner.model.MyMatchStats;

/**
 * Created by ryanm on 12/21/2015.
 */
public class SummonerStatsAdapter extends ArrayAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String[] mStatNames;
    private ArrayList<Integer> mStatValues;



    public SummonerStatsAdapter(Context context, String[] statNames) {
        super(context, R.layout.grid_view_stats, statNames);
        mContext = context;
        mStatNames = statNames;
        mStatValues = MyMatchStats.getStatValues();
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.grid_view_stats, parent, false);
        }
        ArcProgress arcProgress = (ArcProgress) convertView.findViewById(R.id.arc_progress);
        TextView statName = (TextView) convertView.findViewById(R.id.stat_name);
        if(mStatValues.get(position) == -1){
            arcProgress.setBackgroundColor(R.color.DarkRed);
            arcProgress.setProgress(0);
            statName.setText("No Data For " + mStatNames[position]);
        }else {
            arcProgress.setProgress(mStatValues.get(position));
            statName.setText(mStatNames[position]);
        }
        return convertView;
    }
}
