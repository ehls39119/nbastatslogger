package com.example.nba_stats_ia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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

public class StatsOverview extends AppCompatActivity {


    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    RecyclerView recView;
    ArrayList<User> userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_overview);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        userInfo = new ArrayList<User>();
        userInfo.clear();

        recView = (RecyclerView) findViewById(R.id.recycler1ID);
        StatAdapter myAdapter = new StatAdapter(userInfo, this);
        recView.setAdapter(myAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getAndPopulateData(View v) {
        userInfo.clear();
        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot != null && !documentSnapshot.getDocuments().isEmpty()) {
                    List<DocumentSnapshot> documents = documentSnapshot.getDocuments();

                    for (DocumentSnapshot value : documents) {
                        User info = value.toObject(User.class);
                        if (info.ret_leading_performances()!=null){
                            userInfo.add(info);
                        }
                    }

                    for (User z: userInfo){
                        System.out.println("has user info " + z.getName() + "\n");
                    }

                    StatAdapter recAdapter = (StatAdapter) recView.getAdapter();
                    assert recAdapter != null;
                    recAdapter.setGradeData(userInfo);
                    recAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}