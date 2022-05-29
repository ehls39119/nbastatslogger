package com.example.nba_stats_ia;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


public class StatHolder extends RecyclerView.ViewHolder {
    protected TextView nameText;
    protected TextView statusText1;


    public StatHolder(@NonNull View itemView) {
        super(itemView);
        nameText = (itemView).findViewById(R.id.playerNameID);
        statusText1 = (itemView).findViewById(R.id.statNameID);
    }

    public ConstraintLayout getLayout(){
        return itemView.findViewById(R.id.rowLayout);
    }
}
