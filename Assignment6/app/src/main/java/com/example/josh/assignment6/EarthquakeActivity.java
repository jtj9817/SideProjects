package com.example.josh.assignment6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class EarthquakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        WebView viewEarthquakeWeb = (WebView) findViewById(R.id.webViewEarthquake);

        viewEarthquakeWeb.getSettings().setJavaScriptEnabled(true);
        viewEarthquakeWeb.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        //viewEarthquakeWeb.loadData(intent.getStringExtra("url"), "text/html", "UTF-8");
        //viewEarthquakeWeb.loadData(intent.getStringExtra("url"), "text/html", "UTF-8");
        // viewEarthquakeWeb.loadData(intent.getStringExtra("url"), "text/html", "UTF-8");
        viewEarthquakeWeb.loadUrl(intent.getStringExtra("url"));


    }
}
