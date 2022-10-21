package com.example.nba_stats_ia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is an adapter class
 * @author Ernest Sze
 */

public class StatAdapter extends RecyclerView.Adapter {
    ArrayList<Map<String,String>> users_leading_performances;
    Context currentContext;
    public StatAdapter(ArrayList<Map<String, String>> userInput, Context context) {
        users_leading_performances = userInput;
        this.currentContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_stat_holder, parent, false);
        return new StatHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Map<String, String> current_map_leading = users_leading_performances.get(position);
        String player_name = "";
        String stat_to_show_value = "";
        String team_name = "";
        String date = "";
        String stat_to_show = "";

        for (Map.Entry<String, String> ent: current_map_leading.entrySet()){
            if (ent.getKey().equals("Player Name")){
                player_name = ent.getValue();
            }
            else if (ent.getKey().equals("Team Name")){
                team_name =  ent.getValue();
            }
            else if (ent.getKey().equals("Date")){
                date =  ent.getValue();
            }
            else if (!(ent.getKey().equals("Player Name")) || (!ent.getKey().equals("Team Name")) || (!ent.getKey().equals("Date"))) {
                stat_to_show = ent.getKey();
                stat_to_show_value = ent.getValue();
            }

            ((StatHolder) holder).nameText.setText(player_name);
            ((StatHolder) holder).statText.setText(stat_to_show_value);
            ((StatHolder) holder).dateText.setText(date);
            ((StatHolder) holder).teamText.setText(team_name);

            String finalPlayer_name = player_name;
            String finalStat_to_show_value = stat_to_show_value;
            String finalTeam_name = team_name;
            String finalDate = date;
            ((StatHolder) holder).getLayout().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(view.getContext(), addFavouriteStats.class);
                    myIntent.putExtra("Player Name", finalPlayer_name);
                    myIntent.putExtra("Stat", finalStat_to_show_value);
                    myIntent.putExtra("Date", finalDate);
                    myIntent.putExtra("Team Name", finalTeam_name);
                    currentContext.startActivity(myIntent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return users_leading_performances.size();
    }

    public void setGradeData(ArrayList<Map<String, String>> users_leading_performances)
    {
        this.users_leading_performances = users_leading_performances;
    }
}
