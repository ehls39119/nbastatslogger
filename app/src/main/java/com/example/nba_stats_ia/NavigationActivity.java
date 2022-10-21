package com.example.nba_stats_ia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * This class allows user to direct to other activities as a main page
 * @author Ernest Sze
 */

public class NavigationActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    /**
     * This function allows user to go to AddStatsActivity
     * @author Ernest Sze
     */

    public void goToAddStatsActivity(View v){
        if (mUser != null){
            Intent intent = new Intent(this, AddStatsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * This function allows user to go to LogActivity
     * @author Ernest Sze
     */

    public void goToLogActivity(View v){
        if (mUser != null){
            Intent intent = new Intent(this, LogActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * This function allows user to go to MainActivity
     * @author Ernest Sze
     */

    public void goToPrevActivity(View v){
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);
    }
}