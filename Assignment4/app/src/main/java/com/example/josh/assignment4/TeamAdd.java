package com.example.josh.assignment4;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Josh on 3/25/2018.
 */

public class TeamAdd extends AppCompatActivity{
    final int REQUEST_CODE_GALLERY = 999;
    ImageView imagePreviewAdd;
    Spinner sportsCategorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        //Setup buttons and edit text fields
        Button addTeambttn = (Button) findViewById(R.id.submitBttn2);
        Button exitAddTeambttn = (Button) findViewById(R.id.exitBttn);
        final EditText etextTeamName = (EditText) findViewById(R.id.eTxtTeamName);
        final  EditText etextTeamMVP = (EditText) findViewById(R.id.eTxtMVP);
        final  EditText etextCity = (EditText) findViewById(R.id.eTxtCity);
        final  EditText etextStadium = (EditText) findViewById(R.id.eTxtStadium);
        //final  EditText etextSportCategory  = (EditText) findViewById(R.id.eTxtSportCategory);
        sportsCategorySpinner = (Spinner) findViewById(R.id.spinnerSportsSelection);
        Button uploadImagebttnAdd = (Button) findViewById(R.id.bttnUploadImg);
        imagePreviewAdd = (ImageView) findViewById(R.id.imgViewUploadPrev);


        //Setup Request Code

        //Setup Database
        final TeamBaseHelper teamDBHelper = new TeamBaseHelper(getApplicationContext());
        final SQLiteDatabase teamDBWritable =  teamDBHelper.getWritableDatabase();
        final SQLiteDatabase teamDBReadable  =  teamDBHelper.getReadableDatabase();


        //Image Upload Button
        uploadImagebttnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
        });


        addTeambttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuffer teamNames = new StringBuffer();

                String teamname = etextTeamName.getText().toString();
                String mvp = etextTeamMVP.getText().toString();
                String city = etextCity.getText().toString();
                String stadium = etextStadium.getText().toString();
                String category = sportsCategorySpinner.getSelectedItem().toString();
                //String category = etextSportCategory.getText().toString();
                byte[] teamImage = imageViewToByte(imagePreviewAdd);

                if(( etextTeamName.getText().toString().trim().equals("")) ||
                        ( etextTeamMVP.getText().toString().trim().equals("")) ||
                        ( etextCity.getText().toString().trim().equals("")) ||
                        ( etextStadium.getText().toString().trim().equals("")) )
                {
                    Toast.makeText(getApplicationContext(), "One of your fields is empty! Try Again!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Insert values into database
                    ContentValues values = new ContentValues();
                    values.put(TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_TEAMNAME, teamname);
                    values.put(TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_CITY, city);
                    values.put(TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_MVP, mvp);
                    values.put(TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_SPORT, category);
                    values.put(TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_STADIUM, stadium);
                    values.put(TeamDbSchema.TeamsTable.Cols.COLUMN_NAME_IMAGENAME, teamImage);

                    long result = teamDBWritable.insert(TeamDbSchema.TeamsTable.Cols.TABLE_NAME,null, values);
                    if(result == -1)
                    {
                        Toast.makeText(getBaseContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), "Successfully inserted data!", Toast.LENGTH_SHORT).show();
                        etextCity.setText("");
                        sportsCategorySpinner.setSelection(0);
                        //etextSportCategory.setText("");
                        etextTeamMVP.setText("");
                        etextTeamName.setText("");
                        etextStadium.setText("");
                        imagePreviewAdd.setImageResource(R.drawable.imgerror);
                    }
                }

            }
        });

        exitAddTeambttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                finish();
                System.exit(0);*/
                Intent teamsList = new Intent(TeamAdd.this, MainActivity.class);
                startActivity(teamsList);
                finish();
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
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK &&
                data != null)
        {
            Uri uri = data.getData();
            try
            {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
                imagePreviewAdd.setImageBitmap(imgBitmap);

            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    public void onBackPressed() { }
}
