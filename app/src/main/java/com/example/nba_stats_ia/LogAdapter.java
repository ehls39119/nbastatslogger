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

public class LogAdapter extends RecyclerView.Adapter {

    ArrayList userData;
    Context currentContext;

    public LogAdapter(ArrayList<User> studentInfoInput, Context context) {
        userData = studentInfoInput;
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

        User newStudent = (User) userData.get(position);
        ArrayList<Map<String, String>> leading_info = new ArrayList<Map<String, String>>();


        leading_info = newStudent.ret_leading_performances();
        System.out.println("retrieved info " + leading_info);

        String player_name = "";
        String stat_to_show_value = "";

        String team_name = "";
        String date = "";

        String stat_to_show = "";

        for(int i = 0; i < leading_info.size(); i++)
        {
            for (Map.Entry<String, String> entry : leading_info.get(i).entrySet()) {

                if (entry.getKey().equals("Player Name")) {
                    player_name = entry.getValue();
                } else if (entry.getKey().equals("Team Name")) {
                    team_name = entry.getValue();
                } else if (entry.getKey().equals("Date")) {
                    date = entry.getValue();
                } else {
                    // map for progress and transcript
                    stat_to_show = entry.getKey();
                    stat_to_show_value = entry.getValue();
                }

            }

            System.out.println("name " + player_name);
            System.out.println("val " + stat_to_show_value);
            System.out.println("date " + date);
            System.out.println("team_name " + team_name);

            ((StatHolder) holder).nameText.setText(player_name);
            ((StatHolder) holder).statText.setText(stat_to_show_value);
            ((StatHolder) holder).dateText.setText(date);
            ((StatHolder) holder).teamText.setText(team_name);

            String finalPlayer_name = player_name;
            String finalStat_to_show = stat_to_show;
            String finalStat_to_show_value = stat_to_show_value;

            ((StatHolder) holder).getLayout().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(view.getContext(), addFavouriteStats.class);
                    myIntent.putExtra("Player Name", finalPlayer_name);
                    myIntent.putExtra(finalStat_to_show, finalStat_to_show_value);
                    currentContext.startActivity(myIntent);
                }
            });
        }
    }





    @Override
    public int getItemCount() {
        return userData.size();
    }

    public void setGradeData(ArrayList<User> students)
    {
        this.userData = students;
    }








}
