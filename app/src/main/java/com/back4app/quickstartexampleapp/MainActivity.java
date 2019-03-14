package com.back4app.quickstartexampleapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {



    public void changeActivity(){
        if(ParseUser.getCurrentUser().get("studentOrDriver").equals("student")){
            Intent intent = new Intent(getApplicationContext(),StudentActivity.class);
            Log.i("riz", "Student Map activity on hoiise");
            startActivity(intent);
        }

        if(ParseUser.getCurrentUser().get("studentOrDriver").equals("driver")){
            Intent intent = new Intent(getApplicationContext(),DriverActivity.class);
            Log.i("riz", "Driver Map activity on hoiise");
            startActivity(intent);
        }

    }

    public void mainParseCheck(View view){
        Switch userTypeSwitch =(Switch) findViewById(R.id.userTypeSwitch);
        Button startBtn = (Button) findViewById(R.id.startBtn);
        Button login = (Button) findViewById(R.id.login);
        TextView driverTxt = (TextView) findViewById(R.id.textView2);
        TextView studentTxt = (TextView) findViewById(R.id.textView3);

        Log.i("riz", "Start btn a click porseee ");
        //userTypeSwitch.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);
        startBtn.setVisibility(View.INVISIBLE);
        //studentTxt.setVisibility(View.VISIBLE);
        driverTxt.setVisibility(View.VISIBLE);

        if(ParseUser.getCurrentUser() == null){
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e == null){
                        Log.i("riz", "Anonymous login hoise student or driver posondo korbe");
                    }else{
                        Log.i("riz", "Anonymous login failed!!!!!! ");
                    }
                }
            });
        }else {
            if(ParseUser.getCurrentUser().get("studentOrDriver") != null){
                Log.i("riz", "Agey session a j uuser type a  login hoiisilo shei activity nitasi --->"+ParseUser.getCurrentUser().get("studentOrDriver"));
                changeActivity();
            }else {
                Log.i("riz", "Agey kono session chiilo na kintu agey anoymous login null na");
            }
        }
    }

    public  void getStarted(View view) // login button click korle ai function click hy :33
    {
        Log.i("riz", "getstart botton click korse");
        final Switch userTypeSwitch =(Switch) findViewById(R.id.userTypeSwitch);
        //Button startBtn = (Button) findViewById(R.id.startBtn);
        //Button login = (Button) findViewById(R.id.login);

        Log.i("riz", "User type True driver or False Student-->"+String.valueOf(userTypeSwitch.isChecked()));
        String userType ="student";

        if(true){
            userType ="driver";
        }
        if(ParseUser.getCurrentUser() !=null) {
            ParseUser.getCurrentUser().put("studentOrDriver", userType);
            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        if (userTypeSwitch.isChecked()) {
                            Log.i("riz", "Parse ar studentOrDriver a save hoilo driver ");
                        } else {
                            Log.i("riz", "Parse ar studentOrDriver a save hoiilo student ");
                        }
                        changeActivity();

                    }
                }
            });
        }else{
            Log.i("riz", "Current user null ");
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();



        // Save the current Installation to Back4App
        //ParseInstallation.getCurrentInstallation().saveInBackground();

    }

}
