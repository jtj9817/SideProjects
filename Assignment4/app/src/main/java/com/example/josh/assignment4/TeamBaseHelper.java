package com.example.josh.assignment4;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Josh on 3/25/2018.
 */

public class TeamBaseHelper extends SQLiteOpenHelper{
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "teamBase.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TeamDbSchema.TeamsTable.Cols.TABLE_NAME + " (" +
                    TeamDbSchema.TeamsTable.Cols._ID + " INTEGER PRIMARY KEY," +
                    TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_CITY + " TEXT," +
                    TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_SPORT + " TEXT," +
                    TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_TEAMNAME + " TEXT," +
                    TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_MVP + " TEXT," +
                    //TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_STADIUM + " TEXT)";
                    TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_STADIUM + " TEXT," +
                    TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_IMAGENAME + " BLOB)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TeamDbSchema.TeamsTable.Cols.TABLE_NAME;

    public TeamBaseHelper(Context context) {
        super(context, DATABASE_NAME, null  , VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public Cursor getTeamNames(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor teamList = db.rawQuery("SELECT * FROM " + TeamDbSchema.TeamsTable.Cols.TABLE_NAME, null);
        return teamList;
    }
}
