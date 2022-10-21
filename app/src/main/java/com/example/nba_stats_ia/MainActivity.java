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
import android.widget.Toast;

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


/**
 * This is the MainActivity class
 * @author Ernest Sze
 */

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


    /**
     * This function allows user to sign in through database
     */


    public void signIn(View v) {db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        String inputEmail = emailField.getText().toString();
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task)
        {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> ds = task.getResult().getDocuments();
                for (DocumentSnapshot doc : ds) {
                    Map<String, Object> docData = doc.getData();
                    String found = (String) docData.get("email");
                    if (found.equals(inputEmail)) {
                        switchScreen();
                        }
                    }
                }
            }
        });
    }

    /**
     * This function allows user to sign up through database
     */

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
                    db.collection("Users").document(email).set(user);
                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w("SIGN UP", "SIGNUP FAIL", task.getException());
                    Toast.makeText(MainActivity.this, "Given password invalid or Given Email invalid",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * This function allows user to go to NavigationActivity
     */

    public void switchScreen(){
        Intent z = new Intent(this, NavigationActivity.class);
        startActivity(z);
    }
}