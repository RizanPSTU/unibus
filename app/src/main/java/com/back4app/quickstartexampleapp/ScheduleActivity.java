package com.back4app.quickstartexampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    ListView BusListView;
    ArrayList<String> schedule = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    Handler handler = new Handler();

    private Runnable listUpdater = new Runnable() {
        @Override
        public void run() {
            Log.i("riz", "Bus ar list update hoitase :3 schedule ar :3333");
            handler.postDelayed(this,5000);

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Schedule");
            query.setLimit(15);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e == null){
                        schedule.clear();
                        if(objects.size() > 0){
                            for(ParseObject object:objects){
                                String time = (String) object.get("time");
                                if(time != null) {
                                    //Double distanceInKM = geoPointLocation.distanceInKilometersTo(requestGeoPoint);
                                    //Double distanceOneDP = (double) Math.round(distanceInKM * 10) / 10;
                                    schedule.add(time);
                                    //requestLatitude.add(requestGeoPoint.getLatitude());
                                    //dequestLongitude.add(requestGeoPoint.getLongitude());
                                    //driverUsername.add((String) object.get("username"));
                                    //Log.i("riz", "Bus names->"+object.get("busname"));
                                    //Log.i("riz", "Bus onject id :33 ->"+object.getObjectId());

                                }
                            }

                        }else{
                            schedule.add("There is no active schedule or your internet is not working");
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
        setContentView(R.layout.activity_schedule);

        setTitle("Bus Schedule");
        BusListView = (ListView) findViewById(R.id.scheduleListView);
        schedule.clear();
        schedule.add("Getting schedule...");
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,schedule);
        BusListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        listUpdater.run();

    }
}
