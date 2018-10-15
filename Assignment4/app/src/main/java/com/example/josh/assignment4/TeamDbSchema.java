package com.example.josh.assignment4;

import android.provider.BaseColumns;

import java.sql.Blob;

/**
 * Created by Josh on 3/25/2018.
 */

public class TeamDbSchema {
    public static final class TeamsTable {
        public static final class Cols implements BaseColumns {
            public static final String TABLE_NAME = "teams";
            public static final String COLUMN_NAME_TEAMNAME = "teamName";
            public static final String COLUMN_NAME_MVP = "teamMVP";
            public static final String COLUMN_NAME_SPORT = "sportCategory";
            public static final String COLUMN_NAME_STADIUM = "teamStadium";
            public static final String COLUMN_NAME_CITY = "city";
            public static final String COLUMN_NAME_IMAGENAME = "image";
        }
    }
}
