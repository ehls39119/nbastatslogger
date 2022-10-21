package com.example.nba_stats_ia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the StatsOverview class
 * @author Ernest Sze
 */

public class StatsOverview extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    RecyclerView recView;
    ArrayList<Map<String, String>> leading_performances_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_overview);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        leading_performances_input = new ArrayList<Map<String, String>>();
        recView = (RecyclerView) findViewById(R.id.recycler1ID);
        StatAdapter myAdapter = new StatAdapter(leading_performances_input, this);
        recView.setAdapter(myAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));
    }


    /**
     * This function retreives data from database for access
     * @author Ernest Sze
     */

    public void getAndPopulateData(View v) {
        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null && !documentSnapshot.getDocuments().isEmpty()) {
                    List<DocumentSnapshot> documents = documentSnapshot.getDocuments();
                    for (DocumentSnapshot value : documents) {
                        User info = value.toObject(User.class);
                        if (info.ret_leading_performances()!=null){
                            leading_performances_input = (info.ret_leading_performances());
                        }
                    }

                    StatAdapter recAdapter = (StatAdapter) recView.getAdapter();
                    assert recAdapter != null;
                    recAdapter.setGradeData(leading_performances_input);
                    recAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * This function goes to AddStatsActivity
     * @author Ernest Sze
     */

    public void goBack(View v){
        Intent z = new Intent(this, AddStatsActivity.class);
        startActivity(z);
    }
}