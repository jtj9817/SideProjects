package com.example.josh.assignment6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakesDisplayActivity extends AppCompatActivity {
    String EQDataFetchURL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2018-01-01&minmagnitude=6.0";

    private ListView mEarthquakesList;
    private List<ListItem> earthquakeItems;
    //ArrayAdapter eqSetAdapter;
    CustomAdapter eqSetAdapter;
    ArrayList<ListItem> eqOverviewSet = new ArrayList<>();
    ArrayList<String> eqDetails = new ArrayList<>();
    ArrayList<String> eqDates = new ArrayList<>();
    Integer eqListLimit;
    TextView eqNumberParsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquakes_display);
        Intent intent = getIntent();
        //eqListLimit = Integer.parseInt(intent.getStringExtra("eqNumber"));
        eqListLimit = intent.getIntExtra("eqNumber", -1);
        mEarthquakesList = (ListView) findViewById(R.id.listViewEQs);
        eqNumberParsed = (TextView) findViewById(R.id.tViewName);
        eqNumberParsed.setText("Number of Earthquakes Parsed: " + eqListLimit);
        mEarthquakesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EarthquakeActivity.class);
                intent.putExtra("url", eqOverviewSet.get(position).getEarthquakeLink());
                startActivity(intent);
            }
        });
        loadEarthquakeData();
    }

    private void loadEarthquakeData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data");
        progressDialog.show();

        //Fetch data from the server
        StringRequest fetchEqData = new StringRequest(Request.Method.GET, EQDataFetchURL,
                new Response.Listener<String>() {
                    @Override//Retrieve the JSON data in this method
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try
                        {
                            JSONObject eqDataObject = new JSONObject(response);
                            JSONArray eqDataArray = eqDataObject.getJSONArray("features");

                            //Run a loop to retrieve data from the JSONArray
                            for(int i =0; i < eqListLimit; i++)
                            {
                                JSONObject earthquakeObj = eqDataArray.getJSONObject(i);
                                JSONObject eqFeaturesObj = earthquakeObj.getJSONObject("properties");
                                ListItem earthquakeItem = new ListItem(
                                        eqFeaturesObj.getString("title"),
                                        eqFeaturesObj.getString("time"),
                                        eqFeaturesObj.getString("url")
                                );
                                eqOverviewSet.add(earthquakeItem);
                                eqSetAdapter  = new CustomAdapter(EarthquakesDisplayActivity.this, eqOverviewSet);
                                mEarthquakesList.setAdapter(eqSetAdapter);
                                eqSetAdapter.notifyDataSetChanged();
                                mEarthquakesList.deferNotifyDataSetChanged();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );//End of fetchEQData function
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(fetchEqData);
    }
}
