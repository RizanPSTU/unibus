package com.back4app.quickstartexampleapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.O;

public class ViewDriverActivity extends AppCompatActivity {

    ListView driverListView;
    ArrayList<String> drivers = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    LocationManager locationManager;
    LocationListener locationListener;

    ArrayList<Double> requestLatitude = new ArrayList<Double>();
    ArrayList<Double> requestLongitude = new ArrayList<Double>();
    ArrayList<String> driverUsername = new ArrayList<String>();

    Handler handler = new Handler();

    public  void listUpdater (){
        arrayAdapter.notifyDataSetChanged();
    }

    public void updateListView(Location location){
        if(location != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listUpdater();
                }
            },1000);
            final ParseGeoPoint geoPointLocation = new ParseGeoPoint(location.getLatitude(),location.getLongitude());
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.setLimit(15);
            query.whereEqualTo("studentOrDriver","driver");
            query.whereNear("location",geoPointLocation);
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(e == null){
                        drivers.clear();
                        requestLatitude.clear();
                        requestLongitude.clear();
                        if(objects.size() > 0){
                            for(ParseObject object:objects){
                                ParseGeoPoint requestGeoPoint = (ParseGeoPoint) object.get("location");
                                if(requestGeoPoint != null) {
                                    Double distanceInKM = geoPointLocation.distanceInKilometersTo(requestGeoPoint);
                                    Double distanceOneDP = (double) Math.round(distanceInKM * 10) / 10;
                                    drivers.add(distanceOneDP.toString() + " km away");
                                    requestLatitude.add(requestGeoPoint.getLatitude());
                                    requestLongitude.add(requestGeoPoint.getLongitude());
                                    driverUsername.add((String) object.get("username"));
                                    //Log.i("riz", "Driver username->"+object.get("username"));

                                }
                            }

                        }else{
                            drivers.add("There is no active driver");
                        }

                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    updateListView(lastKnownLocation);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_driver);

        setTitle("Bus Driver List");
        driverListView = (ListView) findViewById(R.id.driverListView);
        drivers.clear();
        drivers.add("Getting Bus Drivers");
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,drivers);
        driverListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        driverListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(ContextCompat.checkSelfPermission(ViewDriverActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                   //Log.i("riz", "ListView ar on click pos->"+position);

                    if(requestLatitude.size() > position && requestLongitude.size() > position && lastKnownLocation != null){
                        Intent intent = new Intent(getApplicationContext(),StudentDriverActivity.class);
                        intent.putExtra("requestLatitude",requestLatitude.get(position));
                        intent.putExtra("requestLongitude",requestLongitude.get(position));
                        intent.putExtra("studentLatitude",lastKnownLocation.getLatitude());
                        intent.putExtra("studentLongitude",lastKnownLocation.getLongitude());
                        intent.putExtra("driverUsername",driverUsername.get(position));
                        startActivity(intent);
                    }else{
                        Log.i("riz", "3ta shorte problem");
                    }

                }else{
                    Log.i("riz", "Permission nai");
                }

            }
        });




        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateListView(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Toast.makeText(this, "SDK < 23", Toast.LENGTH_SHORT).show();
        }else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1 );
            }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (lastKnownLocation != null){
                    updateListView(lastKnownLocation);
                }
            }

        }




    }
}
