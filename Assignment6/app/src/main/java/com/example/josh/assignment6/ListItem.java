package com.example.josh.assignment6;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ListItem {


    private String earthquakeTitle;
    private String earthquakeDate;
    private SimpleDateFormat earthquakeDateFormat = new SimpleDateFormat("EEE, MMM d yyyy HH:mm:ss z",  java.util.Locale.getDefault());
    private String earthquakeLink;

    public ListItem(String title, String date, String link){
        Long dateLongFormat = Long.parseLong(date);
        this.earthquakeDate = earthquakeDateFormat.format(new Date(dateLongFormat));
        this.earthquakeLink = link;
        this.earthquakeTitle = title;
    }

    public String getEarthquakeTitle() {
        return earthquakeTitle;
    }

    public String getEarthquakeDate() {
        return earthquakeDate;
    }

    public String getEarthquakeLink() {
        return earthquakeLink;
    }
}
