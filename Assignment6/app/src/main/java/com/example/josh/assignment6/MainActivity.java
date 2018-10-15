package com.example.josh.assignment6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    Integer earthquakeNumber = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submitBttn = (Button) findViewById(R.id.bttnSubmit);
        final EditText eqValue = (EditText) findViewById(R.id.eTxtEQNumber);

        submitBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!eqValue.getText().toString().isEmpty())
                {
                    earthquakeNumber = Integer.parseInt(eqValue.getText().toString());
                }
                Intent intent = new Intent(MainActivity.this, EarthquakesDisplayActivity.class);
                intent.putExtra("eqNumber", earthquakeNumber);
                //intent.putExtra("eqNumber", Integer.parseInt(eqValue.getText().toString()));
                startActivity(intent);
            }
        });
    }
}
