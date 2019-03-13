package com.back4app.quickstartexampleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class BusNameActivity extends AppCompatActivity {

    ListView BusListView;
    ArrayList<String> buses = new ArrayList<String>();
    ArrayList<String> busObjectId = new ArrayList<String>();

    ArrayAdapter arrayAdapter;

    Handler handler = new Handler();

    private Runnable listUpdater = new Runnable() {
        @Override
        public void run() {
            //Log.i("riz", "Bus ar list update hoitase :3 jodio bus notun add  hy nai :#3");
            handler.postDelayed(this,10000);

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Buses");
            query.setLimit(15);
            query.whereEqualTo("active_status","driverOFF");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e == null){
                        buses.clear();
                        if(objects.size() > 0){
                            for(ParseObject object:objects){
                                String busnames = (String) object.get("busname");
                                String objectId =  object.getObjectId();
                                if(busnames != null) {
                                    //Double distanceInKM = geoPointLocation.distanceInKilometersTo(requestGeoPoint);
                                    //Double distanceOneDP = (double) Math.round(distanceInKM * 10) / 10;
                                    buses.add(busnames);
                                    busObjectId.add(objectId);
                                    //requestLatitude.add(requestGeoPoint.getLatitude());
                                    //dequestLongitude.add(requestGeoPoint.getLongitude());
                                    //driverUsername.add((String) object.get("username"));
                                    //Log.i("riz", "Bus names->"+object.get("busname"));
                                    //Log.i("riz", "Bus onject id :33 ->"+object.getObjectId());

                                }
                            }

                        }else{
                            buses.add("There is no active bus you can drive");
                        }

                        arrayAdapter.notifyDataSetChanged();

                    }
                }
            });


        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_name);

        setTitle("Buses List");
        BusListView = (ListView) findViewById(R.id.busNameListView);
        buses.clear();
        buses.add("Getting Bus List");
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,buses);
        BusListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        BusListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //Log.i("riz", "Bus list ar on click pos->"+position);
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Buses");
                String objectId =busObjectId.get(position);
                final String userName = ParseUser.getCurrentUser().getUsername();
                Log.i("riz", objectId);
                // Retrieve the object by id
                query.getInBackground(objectId, new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if(object ==null){
                            Log.i("riz", "object null");
                        }else{
                            Log.i("riz", "object null na-->"+object.get("busname"));
                        }
                        if(e==null && object != null){
                            //Log.i("riz", "Error null :)))");
                            object.put("username",userName);
                            object.put("location", "Akhon o kori nai kicchu, ashole lagey nai akhono");
                            object.put("active_status", "driverON");
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e == null){
                                        Intent intent = new Intent(getApplicationContext(), DriverActivity.class);
                                        intent.putExtra("BUS",buses.get(position));
                                        intent.putExtra("ObjectID",busObjectId);
                                        intent.putExtra("POS",position);
                                        startActivity(intent);
                                        Log.i("riz", "Intent a ja pathaitasi bus :33 -->)"+buses.get(position));
                                        Log.i("riz", "Intent a ja pathaitasi postion :33 -->)"+position);
                                        Log.i("riz", "Intent a ja pathaitasi obejct id :33 -->)"+busObjectId);

                                    }
                                }
                            });
                        }else {
                            Log.i("riz", "Error othoba current onject null)");
                        }

                    }
                });


            }
        });



        listUpdater.run();



        // Hide nevigation ber
        /*
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        */
    }
}
