package me.ryanmiles.mysummoner.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.ryanmiles.mysummoner.R;
import me.ryanmiles.mysummoner.model.MyChampion;

/**
 * Created by ryanm on 12/21/2015.
 */
public class TopChampAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    ArrayList<MyChampion> mMyChampions;
    Context mContext;

    public TopChampAdapter(Context context, ArrayList<MyChampion> myChampions) {
        mContext = context;
        mMyChampions = myChampions;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mMyChampions.size();
    }

    @Override
    public Object getItem(int position) {
        return mMyChampions.get(0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_view_champ, null);
        holder.champ_img = (ImageView) rowView.findViewById(R.id.champ_icon);
        holder.champ_name = (TextView) rowView.findViewById(R.id.champ_name);
        holder.champ_cs = (TextView) rowView.findViewById(R.id.avg_cs_text_view);
        holder.champ_kda = (TextView) rowView.findViewById(R.id.kda_text_view);
        holder.champ_total_games = (TextView) rowView.findViewById(R.id.games_played_text_view);
        holder.champ_win_ratio = (TextView) rowView.findViewById(R.id.percent_win_text_view);
        holder.champ_kda_to_one = (TextView) rowView.findViewById(R.id.kda_to_one_text_view);
        Picasso
                .with(mContext)
                .load(mMyChampions.get(position).getImage())
                .into(holder.champ_img);
        holder.champ_name.setText(mMyChampions.get(position).getName());
        holder.champ_cs.setText(mMyChampions.get(position).getAvgCs() + " CS");
        holder.champ_kda.setText(mMyChampions.get(position).getAvgKills() + " / " + mMyChampions.get(position).getAvgDeaths() + " / " + mMyChampions.get(position).getAvgAssists());
        holder.champ_total_games.setText(mMyChampions.get(position).getGames_played() + " Played");
        holder.champ_win_ratio.setText(mMyChampions.get(position).getWinRatio() + "%");
        holder.champ_kda_to_one.setText(mMyChampions.get(position).getKdaToOne() + ":1KDA");
        if (mMyChampions.get(position).getWinRatio() > 60) {
            holder.champ_win_ratio.setTextColor(Color.RED);
        }
        return rowView;
    }

    public class Holder {
        TextView champ_name, champ_cs, champ_kda, champ_total_games, champ_win_ratio, champ_kda_to_one;
        ImageView champ_img;
    }
}
