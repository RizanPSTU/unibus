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
import com.parse.ParseUser;
import com.parse.SaveCallback;




public class MainActivity extends AppCompatActivity {


    public void registerBtn(View view){
        Intent intent = new Intent(getApplicationContext(),Register.class);
        Log.i("riz", "Register activity a change hoitase");
        startActivity(intent);
    }

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


        Button startBtn = (Button) findViewById(R.id.startBtn);
        Button login = (Button) findViewById(R.id.submit);
        Button register = (Button) findViewById(R.id.register);


        EditText userName = (EditText) findViewById(R.id.editText);
        EditText passWord = (EditText) findViewById(R.id.editText2);

        TextView cleanandhlp = (TextView) findViewById(R.id.textView);
        cleanandhlp.setText("Please only enter the id before @diu.edu.bd at your email.");

        Log.i("riz", "Start btn a click porseee ");
        if(ParseUser.getCurrentUser() == null){
            Log.i("riz", "Current user null 1st ..... if :33 ");

            userName.setVisibility(View.VISIBLE);
            passWord.setVisibility(View.VISIBLE);


            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
            startBtn.setVisibility(View.INVISIBLE);


        }else {
            if(ParseUser.getCurrentUser().get("studentOrDriver") != null){
                Log.i("riz", "Agey session a j uuser type a  login hoiisilo shei activity nitasi --->"+ParseUser.getCurrentUser().get("studentOrDriver"));
                changeActivity();
            }else {
                Log.i("riz", "Agey kono session chiilo na kintu agey anoymous login null na");
                cleanandhlp.setText("Before login press back button to clear previous session :)");
                userName.setVisibility(View.VISIBLE);
                passWord.setVisibility(View.VISIBLE);

                login.setVisibility(View.VISIBLE);
                register.setVisibility(View.VISIBLE);
                //startBtn.setVisibility(View.INVISIBLE);
            }
        }
    }

    public  void getStarted(View view) // login button click korle ai function click hy :33
    {
        Log.i("riz", "getstart botton click korse");

        EditText userName = (EditText) findViewById(R.id.editText);
        EditText passWord = (EditText) findViewById(R.id.editText2);

        String userNameS = userName.getText().toString();
        final String passWordS = passWord.getText().toString();

        //Log.i("riz", "User type True driver or False Student-->"+String.valueOf(userTypeSwitch.isChecked()));
        String userType ="student";


        if(userNameS.length() > 0 && passWordS.length() > 0) {
            userNameS = userNameS + "@diu.edu.bd";
            ParseUser.logInInBackground(userNameS, passWordS, new LogInCallback() {
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        // Hooray! The user is logged in.
                        Log.i("riz", "User->>>>" + ParseUser.getCurrentUser().getUsername());
                        Toast.makeText(MainActivity.this, "Welcome :)", Toast.LENGTH_LONG).show();
                        changeActivity();

                    } else {
                        Toast.makeText(MainActivity.this, "Username or Password is not correct or Check your internet :)", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            Toast.makeText(MainActivity.this, "Username or Password can't be empty :(", Toast.LENGTH_LONG).show();
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
