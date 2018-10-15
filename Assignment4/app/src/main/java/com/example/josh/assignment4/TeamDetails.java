package com.example.josh.assignment4;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class TeamDetails extends AppCompatActivity {
    ImageView teamImagePreview;
    byte[] updatedTeamImage;
    Spinner spinnerUpdateSportsCat;
    String sportsCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);
        //Setup buttons and edit text fields
        Button editTeamDetailsbttn= (Button) findViewById(R.id.submitBttn2);
        Button exitTeamDetailsbttn = (Button) findViewById(R.id.exitBttn2);
        Button deleteTeambbtn = (Button) findViewById(R.id.deleteTeamBttn);
        final  EditText editTeamName= (EditText) findViewById(R.id.tDetailsTeamName);
        final  EditText editTeamMVP = (EditText) findViewById(R.id.tDetailsTeamMVP);
        final  EditText editCity = (EditText) findViewById(R.id.tDetailsCity);
        final  EditText editStadium = (EditText) findViewById(R.id.tDetailsStadium);
        //final  EditText editSportCategory  = (EditText) findViewById(R.id.tDetailsSportCategory);
        spinnerUpdateSportsCat = (Spinner) findViewById(R.id.spinnerSportsCategoryUpdate);
        final  TextView  displayTeamName = (TextView) findViewById(R.id.teamNameDisplay);
        final Button updateImagebttn = (Button) findViewById(R.id.bttnUpdateImg);
        teamImagePreview = (ImageView) findViewById(R.id.imgViewTeamDetail);
        String team = "";
        final String[] projection = {
                TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_TEAMNAME,
                TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_STADIUM,
                TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_MVP,
                TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_SPORT,
                TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_CITY,
                TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_IMAGENAME,
        };

        //Setup Database
        final TeamBaseHelper teamDBHelper = new TeamBaseHelper(getApplicationContext());
        final SQLiteDatabase teamDBWritable =  teamDBHelper.getWritableDatabase();
        final SQLiteDatabase teamDBReadable  =  teamDBHelper.getReadableDatabase();

        //Retrieve "TeamName" value sent from the intent
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            team = bundle.getString("TeamName");
            displayTeamName.setText(team);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No team name is passed on!", Toast.LENGTH_SHORT).show();
        }

        //Retrieve team details by using Cursor and populate the fields
        final String selection = TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_TEAMNAME + " LIKE ?";
        final String teamNameArgs[] = { team };
        Cursor teamDetails = teamDBReadable.query(TeamDbSchema.TeamsTable.Cols.TABLE_NAME, projection,selection,
                teamNameArgs,null,null,null);
        teamDetails.moveToNext();
        int teamNameIndex = teamDetails.getColumnIndex("teamName");
        int teamMVPIndex = teamDetails.getColumnIndex("teamMVP");
        int stadiumIndex = teamDetails.getColumnIndex("teamStadium");
        int categoryIndex = teamDetails.getColumnIndex("sportCategory");
        int cityIndex = teamDetails.getColumnIndex("city");
        int imgIndex = teamDetails.getColumnIndex("image");
        //Extra step for retrieving image
        byte[] teamImageRawData = teamDetails.getBlob(imgIndex);
        Bitmap teamImgBMP = BitmapFactory.decodeByteArray(teamImageRawData, 0, teamImageRawData.length);
        //Populate the edit text fields
        editTeamName.setText(teamDetails.getString(teamNameIndex));
        editTeamMVP.setText(teamDetails.getString(teamMVPIndex));
        //Extra steps for handling spinner options
        sportsCategory = teamDetails.getString(categoryIndex);
        if(sportsCategory.equals("Baseball"))
        {
            spinnerUpdateSportsCat.setSelection(0);
        }

        else if(sportsCategory.equals("Basketball"))
        {
            spinnerUpdateSportsCat.setSelection(1);
        }
        else if(sportsCategory.equals("Football"))
        {
            spinnerUpdateSportsCat.setSelection(2);
        }
        else
        {
            spinnerUpdateSportsCat.setSelection(3);
        }
        //editSportCategory.setText(teamDetails.getString(categoryIndex));
        editCity.setText(teamDetails.getString(cityIndex));
        editStadium.setText(teamDetails.getString(stadiumIndex));
        //Set team image bitmap icon preview
        teamImagePreview.setImageBitmap(teamImgBMP);

        //Edit team details button with no empty input
        editTeamDetailsbttn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if((editTeamName.getText().toString().trim().equals("")) ||
                        ( editTeamMVP.getText().toString().trim().equals("")) ||
                        ( editCity.getText().toString().trim().equals("")) ||
                        ( editStadium.getText().toString().trim().equals("")) )
                {
                    Toast.makeText(getApplicationContext(), "One of your fields is empty! Try Again!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    updatedTeamImage = imageViewToByte(teamImagePreview);
                    //Insert values into database
                    ContentValues values = new ContentValues();
                    values.put(TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_TEAMNAME, editTeamName.getText().toString());
                    values.put(TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_CITY, editCity.getText().toString());
                    values.put(TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_MVP, editTeamMVP.getText().toString());
                    values.put(TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_SPORT, spinnerUpdateSportsCat.getSelectedItem().toString());
                    values.put(TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_STADIUM, editStadium.getText().toString());
                    values.put(TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_IMAGENAME, updatedTeamImage);
                    long result = teamDBWritable.update(TeamDbSchema.TeamsTable.Cols.TABLE_NAME, values, selection, teamNameArgs);
                    if(result == -1)
                    {
                        Toast.makeText(getBaseContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), "Successfully updated team information!", Toast.LENGTH_SHORT).show();
                        Intent teamsList = new Intent(TeamDetails.this, MainActivity.class);
                        startActivity(teamsList);
                        finish();
                    }
                }
            }
        });

        exitTeamDetailsbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent teamsList = new Intent(TeamDetails.this, MainActivity.class);
                startActivity(teamsList);
                finish();
            }
        });

        deleteTeambbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deletedRows = teamDBWritable.delete(TeamDbSchema.TeamsTable.Cols.TABLE_NAME, selection, teamNameArgs);
                if(deletedRows == -1)
                {
                    Toast.makeText(getBaseContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Successfully deleted team!", Toast.LENGTH_SHORT).show();
                    Intent teamsList = new Intent(TeamDetails.this, MainActivity.class);
                    startActivity(teamsList);
                    finish();
                }
            }
        });

        //Image Upload Button
       updateImagebttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 999);
            }
        });
    }

    public static byte[] imageViewToByte(ImageView uploadedImg){
        Bitmap imgBitmapRep = ((BitmapDrawable)uploadedImg.getDrawable()).getBitmap();
        ByteArrayOutputStream imgStream = new ByteArrayOutputStream();
        imgBitmapRep.compress(Bitmap.CompressFormat.PNG, 100,imgStream);
        byte[] imgByteArray = imgStream.toByteArray();
        return imgByteArray;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 999 && resultCode == RESULT_OK &&
                data != null)
        {
            Uri uri = data.getData();
            try
            {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
                teamImagePreview.setImageBitmap(imgBitmap);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
    //Override back button to disable going back to previous activity
    @Override
    public void onBackPressed() { }
}