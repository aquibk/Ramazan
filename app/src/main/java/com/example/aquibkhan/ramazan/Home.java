package com.example.aquibkhan.ramazan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aquibkhan.ramazan.Fragment.ExploreFragment;
import com.example.aquibkhan.ramazan.Fragment.HomeFragment;
import com.example.aquibkhan.ramazan.Fragment.ProfileFragment;
import com.example.aquibkhan.ramazan.Fragment.SearchFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;


public class Home extends AppCompatActivity {
    private static final String TAG = "Home";

    private TextView mTextMessage;

    private String url;

    private RequestQueue queue;

    private FusedLocationProviderClient mFusedLocationClient;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    Fragment hfragment = new HomeFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame, hfragment)
                            .commit();

                    return true;
                case R.id.navigation_Explore:

                    Fragment Efragment = new ExploreFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, Efragment).commit();

                    return true;
                case R.id.navigation_profile:

                    Fragment Pfragment = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, Pfragment).commit();

                    return true;

                case R.id.navigation_search:
                    Fragment sfragment = new SearchFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, sfragment).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Fragment hfragment = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, hfragment)
                .commit();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        Here we get the longitude and the latitude
//        Saved in a String[] variable and use it
       final String[] user_located =  getDataFromIntent();

       queue = Volley.newRequestQueue(this);



    }

    private String[] getDataFromIntent(){
//        here we are getting data from the intent create i nthe MainActivity.java
        String longitude = null;
        String latitude = null;
        Bundle extra = getIntent().getExtras();
        if(extra!=null){
            longitude = extra.getString("longitude");
            latitude = extra.getString("latitude");
        }

        String[] result = {longitude,latitude};
        Log.d(TAG, "getDataFromIntent: "+longitude);
        Log.d(TAG, "getDataFromIntent: "+latitude);
        url = "http://api.aladhan.com/v1/calendar?latitude="+latitude+"&longitude="+longitude+"&method=2&month=5&year=2018";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                Toast.makeText(Home.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
        return result;
    }
}
