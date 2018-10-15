package com.example.josh.assignment4;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TeamBaseHelper teamsDB = new TeamBaseHelper(this);
        Button mainbutton =  (Button) findViewById(R.id.bttnAddTeam);
        Button exitapp = (Button) findViewById(R.id.bttnExit);

        mainbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TeamAdd.class);
                startActivity(intent);
            }
        });

        exitapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Old Team ListView Block Code
        /*
        final ListView listView1 = (ListView) findViewById(R.id.listViewTeams);
        //populate an ArrayList<String> from the database and then view it
        ArrayList<String> theList = new ArrayList<>();
        Cursor teams = teamsDB.getTeamNames();
        ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
        int teamNameIndex = teams.getColumnIndex("teamName");
        while(teams.moveToNext())
        {
            theList.add(teams.getString(teamNameIndex));
            listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
            //listView1.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_list_item_1, R.id.textView2, theList));
            listView1.setAdapter(listAdapter);
            listView1.deferNotifyDataSetChanged();
        }
        */
        final ListView listView1 = (ListView) findViewById(R.id.listViewTeams);
        final ArrayList<TeamItem> theList = new ArrayList<>();
        Cursor teams = teamsDB.getTeamNames();
        int teamNameIndex = teams.getColumnIndex("teamName");
        int teamCityIndex = teams.getColumnIndex("city");
        while(teams.moveToNext())
        {
            TeamItem teamObj = new TeamItem(teams.getString(teamNameIndex),
                    teams.getString(teamCityIndex));
            theList.add(teamObj);
        }
        CustomAdapter listAdapter = new CustomAdapter(MainActivity.this, theList);
        listView1.setAdapter(listAdapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent  = new Intent(MainActivity.this, TeamDetails.class);
                //intent.putExtra("TeamName", listView1.getItemAtPosition(i).toString());
                intent.putExtra("TeamName", theList.get(i).getTeamName());
                startActivity(intent);
                finish();
            }
        });
    }
}