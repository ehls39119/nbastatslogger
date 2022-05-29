package com.example.nba_stats_ia;

import java.util.ArrayList;
import java.util.Map;

public class User {
    public String name;
    public String email;
    public String password;
    public String date;

    public ArrayList<Map<String, String>> favouritePerformances;
    public ArrayList<Map<String, String>> leadingPerformances;

    public User (String inputName, String inputEmail, String inputPass){
        name = inputName;
        email = inputEmail;
        password = inputPass;
    }

    public User(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Map<String, String>> getFavouritePerformances() {
        return favouritePerformances;
    }

    public void setFavouritePerformances(ArrayList<Map<String, String>> favouritePerformances) {
        this.favouritePerformances = favouritePerformances;
    }

    public ArrayList<Map<String, String>> getLeading_performances() {
        return leadingPerformances;
    }

    public void set_leading_performances(ArrayList<Map<String, String>> leading_performances) {
        this.leadingPerformances = leading_performances;
    }

    public ArrayList<Map<String, String>> ret_leading_performances(){
        return leadingPerformances;

    }




}
