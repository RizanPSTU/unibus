package com.back4app.quickstartexampleapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        Log.i("riz", "getstart botton click korse");

        EditText userName = (EditText) findViewById(R.id.editText1);
        EditText passWord = (EditText) findViewById(R.id.editText2);

        final String userNameS = userName.getText().toString();
        final String passWordS = passWord.getText().toString();

        //Log.i("riz", "User type True driver or False Student-->"+String.valueOf(userTypeSwitch.isChecked()));
        String userType ="driver";


        if(userNameS.length() > 0 && passWordS.length() > 0) {
            ParseUser.logInInBackground(userNameS, passWordS, new LogInCallback() {
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        // Hooray! The user is logged in.
                        Log.i("riz", "User->>>>" + ParseUser.getCurrentUser().getUsername());
                        Toast.makeText(MainActivity.this, "Welcome :)", Toast.LENGTH_LONG).show();
                        changeActivity();

                    } else {
                        Toast.makeText(MainActivity.this, "Driver number or Password is not correct or Check your internet :)", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            Toast.makeText(MainActivity.this, "Driver number or Password can't be empty :(", Toast.LENGTH_LONG).show();
        }

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
