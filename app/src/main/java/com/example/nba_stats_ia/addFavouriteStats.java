package com.example.nba_stats_ia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class
 * @author Ernest Sze
 */

public class addFavouriteStats extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    public EditText nameText;
    public EditText statText;

    public ArrayList<Map<String,String>> favourite_performances = new ArrayList<>();
    public ArrayList<Map<String,String>> leading_performances_gotten = new ArrayList<>();

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


    public void addFavouritePerformance(View v) {
        try{
            String player_name = nameText.getText().toString();
            String player_stat = statText.getText().toString();

            db.collection("Users").document(mUser.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        User current_user = document.toObject(User.class);
                        leading_performances_gotten = (current_user.ret_leading_performances());
                        add_performance(leading_performances_gotten, player_name, player_stat);
                    }
                    else{
                        Toast.makeText(addFavouriteStats.this, "Player Name or Statistical Value is invalid, Try again.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        catch (Exception input){
            Toast.makeText(addFavouriteStats.this, "Player Name or Statistical Value is invalid, Try again.",
                    Toast.LENGTH_LONG).show();
        }
    }


    public void add_performance(ArrayList<Map<String, String>> map_input, String player_name_input, String player_stat_input) {
        ArrayList<Map<String, String>> leading_performances =  map_input;

        for (int i = 0; i < leading_performances.size(); i++) {
            Map<String, String> x = leading_performances.get(i);

            if ((x.containsValue(player_name_input)) && (x.containsValue(player_stat_input))) {
                favourite_performances.add(x);
                break;
            }
        }
        db.collection("Users").document(mUser.getEmail()).update("favouritePerformances", favourite_performances);
    }


    public void goBack(View v){
        Intent z = new Intent(this, StatsOverview.class);
        startActivity(z);
    }
}
