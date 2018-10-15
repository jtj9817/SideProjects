package com.example.josh.assignment4;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TeamItem {
    private String teamName;
    private String teamCity;

    public TeamItem(String name, String city)
    {
        this.teamName = name;
        this.teamCity = city;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamCity() {
        return teamCity;
    }
}

