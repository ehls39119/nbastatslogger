package com.example.nba_stats_ia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nba_stats_ia.getStats.Boxscore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    FirebaseUser mUser;


    private EditText nameField;
    private EditText emailField;
    private EditText passwordField;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        nameField = findViewById(R.id.nameID);
        emailField = findViewById(R.id.emailID);
        passwordField = findViewById(R.id.passwordID);


    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    public void signIn(View v) {db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        String inputEmail = emailField.getText().toString();

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task)
        {
            if (task.isSuccessful()) {
                System.out.println("reach here");

                List<DocumentSnapshot> ds = task.getResult().getDocuments();

                System.out.println("reach here2");

                for (DocumentSnapshot doc : ds) {
                    Map<String, Object> docData = doc.getData();

                    String found = (String) docData.get("email");
                    System.out.println("founded " + found);
                    System.out.println("curr" + inputEmail);

                    if (found.equals(inputEmail)) {

                            switchScreen();
                        }
                    }
                }
            }

        });
    }

    public void signUp(View v) {
        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.d("SIGN UP", "SIGNUP SUCCESS");
                    User user = new User(name, email, password);
//                    System.out.println("name " + name);
//                    System.out.println("email " + email);
//                    System.out.println("pass " + password);
                    db.collection("Users").document(email).set(user);
                    System.out.println("done here");

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SIGN UP", "SIGNUP FAIL", task.getException());
                }
            }
        });
    }

    public void switchScreen(){
        System.out.println("reachedd");
        Intent z = new Intent(this, NavigationActivity.class);
        startActivity(z);
    }


//    void updateUI(FirebaseUser user){
//        if (user != null){
//            System.out.println("user exists and is logged in");
//            Intent intent = new Intent(this, NavigationActivity.class);
//            startActivity(intent);
//        }
//        else{
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
//    }



//    public void getPlayers(View v){
//        Boxscore bs = new Boxscore();
//
//// Get boxscore from a game on a given date
//        ArrayList<Boxscore> bsList = bs.getBoxscoreByDateAndGame("20210622", "0042000312");
//
//// Show player name and points for every Player
//        for (Boxscore b : bsList) {
//            System.out.println(b.getFullName() + " - Points: " + b.getAssists());
//            text.setText(b.getFullName() + " - Weight: " + b.getAssists());
//        }
//    }
//
//
//


}