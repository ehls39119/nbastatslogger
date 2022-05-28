package com.example.nba_stats_ia.getStats;

public class PlayerTeams extends Player {

    public String seasonStart;
    public String seasonEnd;

    public PlayerTeams() {

    }

    public String getSeasonStart() {
        return seasonStart;
    }

    public void setSeasonStart(String seasonStart) {
        this.seasonStart = seasonStart;
    }

    public String getSeasonEnd() {
        return seasonEnd;
    }

    public void setSeasonEnd(String seasonEnd) {
        this.seasonEnd = seasonEnd;
    }

}
