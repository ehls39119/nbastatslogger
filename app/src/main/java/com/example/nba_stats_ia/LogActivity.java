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
 * This class allows user acts a repository for user selected performances
 * @author Ernest Sze
 */

public class LogActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    RecyclerView recView;
    ArrayList<Map<String, String>> fav_performances_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_overview);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        fav_performances_input = new ArrayList<Map<String, String>>();

        recView = (RecyclerView) findViewById(R.id.recycler1ID);
        StatAdapter myAdapter = new StatAdapter(fav_performances_input, this);
        recView.setAdapter(myAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));
    }


    /**
     * This function retrieves data from firebase and displays it through the recycler view
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
                        System.out.println("object " + info.toString());
                        if (info.ret_favourite_performances()!=null){
                            fav_performances_input = (info.ret_favourite_performances());
                        }
                    }
                    StatAdapter recAdapter = (StatAdapter) recView.getAdapter();
                    assert recAdapter != null;
                    recAdapter.setGradeData(fav_performances_input);
                    recAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * This function allows user to go to NavigationActivity
     * @author Ernest Sze
     */

    public void goBack(View v){
        Intent z = new Intent(this, NavigationActivity.class);
        startActivity(z);
    }
}