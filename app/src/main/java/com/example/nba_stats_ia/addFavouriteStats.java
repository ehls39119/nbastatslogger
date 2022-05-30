package com.example.nba_stats_ia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addFavouriteStats extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    public EditText nameText;
    public EditText statText;

    ArrayList<Map<String,String>> favourite_performances = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favourite_stats);

        nameText = findViewById(R.id.inputNameID);
        statText = findViewById(R.id.inputStatID);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

    }


    public void addGrade(View v) {

        String player_name = nameText.getText().toString();
        String player_stat = statText.getText().toString();

        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override

            public void onEvent(@Nullable QuerySnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null && !documentSnapshot.getDocuments().isEmpty()) {
                    List<DocumentSnapshot> documents = documentSnapshot.getDocuments();

                    for (DocumentSnapshot value : documents) {
                        User info = value.toObject(User.class);

                        ArrayList<Map<String,String>> leading_performances = info.getLeading_performances();
                        System.out.println(leading_performances.toString());


                        for (int i=0; i<leading_performances.size(); i++) {

                            System.out.println("inside");
                            Map<String, String> x = leading_performances.get(i);
                            System.out.println("curr map " + x);

                            if ((x.containsValue(player_name)) && (x.containsValue(player_stat))){
                                System.out.println("map found that contains both " + x);
                                favourite_performances.add(x);
                                System.out.println("before added " + favourite_performances);
                                break;
                            }
                            break;

                        }
                    }
                }

                db.collection("Users").document(mUser.getEmail()).update("favouritePerformances", favourite_performances);

            }
        });

        Intent z = new Intent(this, addFavouriteStats.class);
        startActivity(z);
    }
}