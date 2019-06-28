package com.back4app.quickstartexampleapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.back4app.quickstartexampleapp.App.CHANNEL_ID;

public class DistanceService extends Service {

    String driverUsername = "";
    Location userLocationl = null;
    boolean stopcheck = false;
    boolean shownoti = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);

        driverUsername = intent.getStringExtra("d");
        userLocationl = intent.getParcelableExtra("ul");

        Log.i("hudai", "Intent e---> "+driverUsername);
        Log.i("hudai", "Intent ul---> "+userLocationl);


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Bus & Student")
                .setContentText("Bus location reminder id ON!")
                .setSmallIcon(R.drawable.ic_android_riz)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        startTimer();


        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();
    }


    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 3 second
        timer.schedule(timerTask, 3000, 5000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //Log.i("hudai", "in timer ++++  "+ (oldTime++));
                if(stopcheck == false){
                    //Log.i("hudai", "parse ar vitore");
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("username", driverUsername);
                    query.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                            //Log.i("hudai", "done ar vitore");
                            if (objects.size() == 1) {
                                //Log.i("hudai", "obj size 1 ar thika boro te");
                                for (ParseObject object : objects) {
                                    final ParseGeoPoint requestGeoPoint = (ParseGeoPoint) object.get("location");
                                    if (requestGeoPoint != null) {
                                        final ParseGeoPoint userLocation = new ParseGeoPoint(userLocationl.getLatitude(), userLocationl.getLongitude());
                                        Double distanceInKM = userLocation.distanceInKilometersTo(requestGeoPoint);
                                        Double distanceOneDP = (double) Math.round(distanceInKM * 10) / 10;
                                        //Log.i("hudai", "double distance per hoise");
                                        if (distanceOneDP < 2.001) {
                                            Log.i("hudai", "Bus aia porse near 2km");
                                            shownoti = true;
                                            //stopcheck =false;
                                        } else {
                                            Log.i("hudai", "dure akhono km-->"+distanceOneDP);
                                        }
                                    }
                                }
                            }
                        }
                    });
                }

                if(shownoti){
                    shownotifivcation();
                    shownoti =false;
                }

            }
        };
    }

    public void shownotifivcation(){
        Intent notificationIntent2 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, notificationIntent2, 0);
        final Notification notification2 = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Bus & Student")
                .setContentText("Bus is near to your location")
                .setSmallIcon(R.drawable.ic_android_riz)
                .setContentIntent(pendingIntent2)
                .build();
        startForeground(1, notification2);
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
