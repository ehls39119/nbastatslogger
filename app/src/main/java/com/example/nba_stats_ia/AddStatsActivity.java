package com.example.nba_stats_ia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nba_stats_ia.getStats.Boxscore;
import com.example.nba_stats_ia.getStats.Scoreboard;
import com.example.nba_stats_ia.getStats.Team;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddStatsActivity extends AppCompatActivity{

    FirebaseFirestore db;
    FirebaseUser mUser;
    FirebaseAuth mAuth;

    private Spinner statSpinner;
    public EditText inputDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stats);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();


        inputDate = findViewById(R.id.inputDateID);

        statSpinner = findViewById(R.id.statsSpinnerID);
        ArrayAdapter<CharSequence> statAdapter = ArrayAdapter.createFromResource(this,
                R.array.Statistics, android.R.layout.simple_spinner_item);
        statAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statSpinner.setAdapter(statAdapter);

    }




    ArrayList<String> statistics_strings = new ArrayList<String>();


    ArrayList<Integer> sorted_statistics_integers = new ArrayList<Integer>();
    ArrayList<String> sorted_player_name_strings = new ArrayList<String>();
    ArrayList<String> sorted_date_strings = new ArrayList<String>();
    ArrayList<String> sorted_team_name_strings = new ArrayList<String>();

    String user_stat = "";


    public void getStatistics(View v){


        ArrayList<Map<String, String>> bestPerformances = new ArrayList<Map<String, String>>();
        ArrayList<Map<String, String>> allPerformances = new ArrayList<Map<String, String>>();
;

        String date_string = inputDate.getText().toString();

//        System.out.println("received date " + date_string);

        Scoreboard sb = new Scoreboard();
        Boxscore bs = new Boxscore();


        ArrayList<Scoreboard> sbList = sb.getScoreboardListByDate(date_string);

        for (Scoreboard b : sbList) {
            System.out.println("game id "+ b.getGameId());


            ArrayList<Boxscore> bsList = bs.getBoxscoreByDateAndGame(date_string, b.getGameId());


            for (Boxscore current_box: bsList){
                Map<String, String> individual_map = new HashMap<>();

                user_stat = statSpinner.getSelectedItem().toString();

                if (user_stat.equals("Points")) {

                    individual_map.put("Player Name", current_box.getFullName());
                    individual_map.put("Points", current_box.getPoints());
                    individual_map.put("Date", date_string);

                    Team team_gotten = new Team().getTeamById(current_box.getTeamId());
                    String team_name = team_gotten.getFullName();
                    individual_map.put("Team", team_name);
                    allPerformances.add(individual_map);
                }

                else if (user_stat.equals("Assists")) {
                    individual_map.put("Player Name", current_box.getFullName());
                    individual_map.put("Assists", current_box.getAssists());
                    individual_map.put("Date", date_string);

                    Team team_gotten = new Team().getTeamById(current_box.getTeamId());
                    String team_name = team_gotten.getFullName();
                    individual_map.put("Team", team_name);
                    allPerformances.add(individual_map);
                }

                else if (user_stat.equals("Steals")) {

                    individual_map.put("Player Name", current_box.getFullName());
                    individual_map.put("Steals", current_box.getSteals());
                    individual_map.put("Date", date_string);
                    Team team_gotten = new Team().getTeamById(current_box.getTeamId());
                    String team_name = team_gotten.getFullName();
                    individual_map.put("Team", team_name);
                    allPerformances.add(individual_map);

                }
                else if (user_stat.equals("Rebounds")){

                    individual_map.put("Player Name", current_box.getFullName());
                    individual_map.put("Rebounds", current_box.getTotReb());
                    individual_map.put("Date", date_string);

                    Team team_gotten = new Team().getTeamById(current_box.getTeamId());
                    String team_name = team_gotten.getFullName();
                    individual_map.put("Team", team_name);
                    allPerformances.add(individual_map);
                }

                else{
                    System.out.println("Statistic Not Found");
                }
            }
        }

        System.out.println("final dictionary " + allPerformances.toString());



        for (Map<String, String> x: allPerformances){

            Map<String, String> currentMap = x;

            //{Points=6, Team=Miami Heat, Date=20220527, Player Name=P.J. Tucker}

            for (Map.Entry<String, String> current_entry: currentMap.entrySet()) {
                if (current_entry.getKey().equals("Player Name")) {
                    String player_name_string = current_entry.getValue();
//                    System.out.print("player name str " + player_name_string + " ");
                    sorted_player_name_strings.add(player_name_string);

                } else if (current_entry.getKey().equals("Team")) {
                    String team_name_string = current_entry.getValue();
//                    System.out.print("team name str " + team_name_string + " ");
                    sorted_team_name_strings.add(team_name_string);


                } else if (current_entry.getKey().equals("Date")) {
                    String team_name_string = current_entry.getValue();
//                    System.out.print("date name str " + team_name_string + " ");
                    sorted_date_strings.add(team_name_string);


                } else {
                    String stat_string = current_entry.getValue();
                    System.out.print("\n");
                    statistics_strings.add(stat_string);

                }
//                System.out.println("\n");
            }
        }


//        System.out.println("strings" + statistics_strings);

        for (String x: statistics_strings){
            if (x.equals("")){
                sorted_statistics_integers.add(0);
            }
            else{
                Integer stat_int = Integer.valueOf(x);
                sorted_statistics_integers.add(stat_int);

            }

        }

//        System.out.println("before sort stats: " + statistics_integers);
//        System.out.println("before sort names: " + player_name_strings);
//        System.out.println("before sort dates: " + date_string);
//        System.out.println("before sort teamnames:   " + team_name_strings);


        for (int i = 0; i< sorted_statistics_integers.size(); i++){
            for (int j = i+1; j< sorted_statistics_integers.size(); j++){

                Integer curr_int = sorted_statistics_integers.get(i);
                Integer next_int = sorted_statistics_integers.get(j);

                String curr_player = sorted_player_name_strings.get(i);
                String next_player = sorted_player_name_strings.get(j);

                String curr_date = sorted_date_strings.get(i);
                String next_date = sorted_date_strings.get(j);

                String curr_team_name = sorted_team_name_strings.get(i);
                String next_team_name = sorted_team_name_strings.get(j);

                if (next_int > curr_int){

                    Integer temp = curr_int;
                    sorted_statistics_integers.set(i, next_int);
                    sorted_statistics_integers.set(j, temp);

                    String temp_player = curr_player;
                    sorted_player_name_strings.set(i, next_player);
                    sorted_player_name_strings.set(j, temp_player);


                    String temp_date = curr_date;
                    sorted_date_strings.set(i, next_date);
                    sorted_date_strings.set(j, temp_date);

                    String temp_team_name = curr_team_name;
                    sorted_team_name_strings.set(i, next_team_name);
                    sorted_team_name_strings.set(j, temp_team_name);

                }
            }
        }

        System.out.println("\n\n");
        System.out.println("1.   " + sorted_statistics_integers);
        System.out.println("2.    " + sorted_player_name_strings);
        System.out.println("3.    " + sorted_team_name_strings);
        System.out.println("4.    " + sorted_date_strings);

        ArrayList<Map<String, String>> update_map = (ArrayList<Map<String, String>>) getList();
        db.collection("Users").document(mUser.getEmail()).update("leadingPerformances", update_map, "date", date_string);

        System.out.println("reached");

        Intent z = new Intent(this, StatsOverview.class);
        startActivity(z);




    }

    public List getList(){
        ArrayList<Map<String, String>> best_performances = new ArrayList<Map<String, String>>();
        for (int i=0; i<5; i++){
            Integer curr_stat = sorted_statistics_integers.get(i);
            String stat_str = curr_stat.toString();

            Map<String, String> new_sorted_entry = new HashMap<>();
            new_sorted_entry.put("Player Name", sorted_player_name_strings.get(i));
            new_sorted_entry.put(user_stat, stat_str);
            new_sorted_entry.put("Date", sorted_date_strings.get(i));
            new_sorted_entry.put("Team Name", sorted_team_name_strings.get(i));


            best_performances.add(new_sorted_entry);

        }
//        System.out.println("top 5 " + "\n" + best_performances);
        return best_performances;

    }



//implements AdapterView.OnItemSelectedListener
//    public void onItemSelected(AdapterView<?> parent, View view,
////        int pos, long id) {
////            // An item was selected. You can retrieve the selected item using
////            // parent.getItemAtPosition(pos)
////        }
////
////        public void onNothingSelected(AdapterView<?> parent) {
////
////            // Another interface callback
////        }
////
////
//////        statSpinner.setOnItemSelectedListener(this);

















}