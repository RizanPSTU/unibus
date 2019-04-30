package com.back4app.quickstartexampleapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

public class DriverActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;
    String bus="";
    int position;
    String objectidS="";
    boolean lockCheck =true;

    ArrayList<String> busObjectId = new ArrayList<String>();


    public void lockFunc(View view){
        Log.i("riz", "Lock a click ");
        Button unlock = (Button) findViewById(R.id.unlock);
        Button lock = (Button) findViewById(R.id.lock);
        unlock.setVisibility(View.VISIBLE);
        lock.setVisibility(View.INVISIBLE);
        lockCheck = true;
    }

    public void unlockFunc(View view){
        Log.i("riz", "UnLock a click ");
        Button unlock = (Button) findViewById(R.id.unlock);
        Button lock = (Button) findViewById(R.id.lock);
        unlock.setVisibility(View.INVISIBLE);
        lock.setVisibility(View.VISIBLE);
        lockCheck = false;
    }

    public void driverLogout(View view) {
        //Log.i("riz", "Driver Logout hoilo");
        //ParseUser.logOutInBackground();
        //ParseUser.logOut();


        final String getObjectID = (String) ParseUser.getCurrentUser().get("objectid");

        if(getObjectID != null && getObjectID.length() >0){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Buses");
            query.getInBackground(getObjectID, new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null && object != null) {
                        //Log.i("riz", "Error null :)))");
                        object.put("username", "nai");
                        object.put("location", "Akhon o kori nai kicchu, ashole lagey nai akhono");
                        object.put("active_status", "driverOFF");
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {


                                    ParseUser.getCurrentUser().put("active","");
                                    ParseUser.getCurrentUser().put("busname","");
                                    ParseUser.getCurrentUser().put("objectid","");
                                    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                ParseUser.logOutInBackground(new LogOutCallback() {
                                                    @Override
                                                    public void done(ParseException e) {
                                                        if (e == null){
                                                            Log.i("riz", "Logout hoitase");
                                                            Toast.makeText(DriverActivity.this, "Logout successful", Toast.LENGTH_LONG).show();
                                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                            startActivity(intent);
                                                        }else {
                                                            Toast.makeText(DriverActivity.this, "Logout error", Toast.LENGTH_LONG).show();

                                                        }
                                                    }
                                                });
                                            }

                                        }
                                    });


                                }
                            }
                        });
                    }
                }
            });
        }else{
            ParseUser.getCurrentUser().put("active","");
            ParseUser.getCurrentUser().put("busname","");
            ParseUser.getCurrentUser().put("objectid","");
            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        ParseUser.logOutInBackground(new LogOutCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null){
                                    Log.i("riz", "Logout hoitase");
                                    Toast.makeText(DriverActivity.this, "Logout successful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(DriverActivity.this, "Logout error", Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }

                }
            });
        }






    }

    public void driverBtn(View view) {
        Log.i("riz", "Driver S_list btn click korse");
        Intent intent = new Intent(getApplicationContext(), BusNameActivity.class);
        startActivity(intent);
    }


    public void updateMap(Location location) {
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        //Log.i("riz", "Akhn ar lovation driver ar ->"+location);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Student Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.bussm)));
        if(lockCheck){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(lastKnownLocation != null){
                        updateMap(lastKnownLocation);
                    }
                }
            }
        }
    }

    public void onBackPressed() {
        //ParseUser.getCurrentUser().logOut();
        Log.i("riz", "Back btn press korse");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        TextView busDriving = (TextView) findViewById(R.id.busDriving);
        Button buslist = (Button) findViewById(R.id.driverBtn);
        Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();

        objectidS= (String) ParseUser.getCurrentUser().get("objectid");
        bus=(String) ParseUser.getCurrentUser().get("busname");

        Intent intent = getIntent();
        if(bus ==null){
            try {
                bus = intent.getStringExtra("BUS");
            }catch (Exception e){
                Log.i("riz", "bus e null");
            }
        }
        if(bus !=null && bus.length() == 0){
            try {
                bus = intent.getStringExtra("BUS");
            }catch (Exception e){
                Log.i("riz", "Exception ar vitore karon.. busname faka kintu null na aita abar 1st ");
            }

        }


        position = intent.getIntExtra("POS",-1);
        Log.i("riz", "Bus name  :) --->"+bus);
        Log.i("riz", "Position aise --->"+position);
        Log.i("riz", "Objectid --->"+objectidS);

        if (bus !=null && position != -1){
            busObjectId =intent.getStringArrayListExtra("ObjectID");
            Log.i("riz", "Position ar objectID --->"+busObjectId.get(position));

            busDriving.setText("You are driving "+bus);
            buslist.setVisibility(View.INVISIBLE);

            if (ParseUser.getCurrentUser() != null) {
                ParseUser.getCurrentUser().put("busname",bus);
                ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("riz", "Bus name save hoise User class a");
                        }

                    }
                });
                //Log.i("riz", "Location :"+location);
            }
        }else {
            if (bus == null){
                Log.i("riz", "Bus null");
            }else{
                Log.i("riz", "bus null ba lenght < 1 :((( prrbb ---> "+Integer.toString(bus.length()));
            }

        }

        if (bus !=null && objectidS != null && bus.length() >0 && objectidS.length() >0) {
            //busObjectId = intent.getStringArrayListExtra("ObjectID");
            //Log.i("riz", "Position ar objectID --->" + busObjectId.get(position));

            busDriving.setText("You are driving " + bus);
            buslist.setVisibility(View.INVISIBLE);
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateMap(location);
                if (ParseUser.getCurrentUser() != null) {
                    ParseUser.getCurrentUser().put("location", new ParseGeoPoint(location.getLatitude(), location.getLongitude()));
                    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //Log.i("riz", "Student ar location save hoitase User class a");
                            }

                        }
                    });
                    //Log.i("riz", "Location :"+location);
                }
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
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            //Toast.makeText(this, "SDK < 23", Toast.LENGTH_SHORT).show();
        }else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1 );
            }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (lastKnownLocation != null){
                    updateMap(lastKnownLocation);
                }

                if (lastKnownLocation != null){
                    //updateMap(lastKnownLocation);
                    Log.i("riz", "Last known location null na");
                    if (ParseUser.getCurrentUser() != null) {
                        ParseUser.getCurrentUser().put("location", new ParseGeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
                        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.i("riz", "Driver ar last location save hoitase User class a");
                                }

                            }
                        });
                        //Log.i("riz", "Location :"+location);
                    }
                }else{
                    Log.i("riz", "Last known location null :33");
                }
            }
        }








        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-30, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Driver"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); */
    }
}
