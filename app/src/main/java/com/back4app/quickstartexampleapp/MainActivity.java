package com.back4app.quickstartexampleapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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



    public  void getStarted(View view) // login button click korle ai function click hy :33
    {

    }


    @Override
    public void onBackPressed() {
        ParseUser.getCurrentUser().logOut();
        Log.i("riz", "Back btn press korse");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ParseUser.getCurrentUser().logOut();



        // Save the current Installation to Back4App
        //ParseInstallation.getCurrentInstallation().saveInBackground();

    }

}
