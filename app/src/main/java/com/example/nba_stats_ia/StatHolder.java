package com.example.nba_stats_ia;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This class creates the StatHolder Item View
 * @author Ernest Sze
 */

public class StatHolder extends RecyclerView.ViewHolder {
    protected TextView nameText;
    protected TextView statText;
    protected TextView dateText;
    protected TextView teamText;


    public StatHolder(@NonNull View itemView) {
        super(itemView);
        nameText = (itemView).findViewById(R.id.playerNameID);
        statText = (itemView).findViewById(R.id.statNameID);
        dateText = (itemView).findViewById(R.id.dateID);
        teamText = (itemView).findViewById(R.id.teamNameID);
    }

    public ConstraintLayout getLayout(){
        return itemView.findViewById(R.id.rowLayout);
    }
}
