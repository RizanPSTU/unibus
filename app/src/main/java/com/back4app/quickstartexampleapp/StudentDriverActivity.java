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
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class StudentDriverActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;
    String driverUsername;
    Handler handler = new Handler();
    TextView infoTextView;

    public void updateMap(Location location,ParseGeoPoint requestLocation){
        LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng driverLatLng = new LatLng(requestLocation.getLatitude(), requestLocation.getLongitude());
        Log.i("riz", "student ar location->"+userLatLng);
        Log.i("riz", "driver ar location ->"+driverLatLng);

        final ParseGeoPoint userLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
        //final ParseGeoPoint driverLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

        Double distanceInKM = userLocation.distanceInMilesTo(requestLocation);
        Double distanceOneDP = (double) Math.round(distanceInKM * 10) / 10;
        if(distanceOneDP < 0.001){
            infoTextView.setText("Bus is almost here!!");
        }else {
            infoTextView.setText("Distance " + distanceOneDP+"km");
        }
        ArrayList<Marker> markers = new ArrayList<>();

        mMap.clear();

        markers.add(mMap.addMarker(new MarkerOptions().position(driverLatLng).title("Bus Driver Location")));
        markers.add(mMap.addMarker(new MarkerOptions().position(userLatLng).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();


        int padding = 60; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        mMap.animateCamera(cu);

        /*
        Double distanceInKM = userLatLng.distanceInKilometersTo(requestGeoPoint);
        Double distanceOneDP = (double) Math.round(distanceInKM * 10) / 10;
        drivers.add(distanceOneDP.toString() + " km away");*/


        /*
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Student Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,8));*/

    }

    public void stnLogout(View view){
        ParseUser.logOut();
        //ParseUser.logOutInBackground();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    //updateMap(lastKnownLocation);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_driver);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        infoTextView = (TextView) findViewById(R.id.infoTextView);
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

        Intent intent =getIntent();

        //LatLng studentlocation = new LatLng(intent.getDoubleExtra("studentLatitude",0), intent.getDoubleExtra("studentLongitude",0));
        //LatLng driverlocation = new LatLng(intent.getDoubleExtra("requestLatitude",0), intent.getDoubleExtra("requestLongitude",0));
        driverUsername =intent.getStringExtra("driverUsername");
        //Log.i("riz", "Driver ar username->"+driverUsername);
        //Log.i("riz", "Student ar->"+studentlocation);
       // Log.i("riz", "Driver ar->"+driverlocation);
        //mMap.addMarker(new MarkerOptions().position(driverlocation).title("Student"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(driverlocation,8));


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                //updateMap(location);
                if(ParseUser.getCurrentUser() != null){
                    ParseUser.getCurrentUser().put("location", new ParseGeoPoint(location.getLatitude(),location.getLongitude()));
                    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                // No error
                            }

                        }
                    });
                }
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("username",driverUsername);
                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        if(objects.size() == 1) {
                            for (ParseObject object : objects) {
                                final ParseGeoPoint requestGeoPoint = (ParseGeoPoint) object.get("location");
                                if(requestGeoPoint != null) {
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            updateMap(location,requestGeoPoint);
                                        }
                                    },1000);
                                }
                            }
                        }
                    }
                });
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
                    //updateMap(lastKnownLocation);
                }
            }


        }

    }
}
