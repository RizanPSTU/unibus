package com.back4app.quickstartexampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    public void submitBtn(View view){
        Log.i("riz", "Submit a click korse :333");
        final EditText firstNameE = (EditText) findViewById(R.id.editText1);
        final EditText lastNameE = (EditText) findViewById(R.id.editText2);
        final EditText emailIDE = (EditText) findViewById(R.id.editText3);
        final EditText passwordE = (EditText) findViewById(R.id.editText4);
        final EditText conPasswprdE= (EditText) findViewById(R.id.editText5);
        final EditText numberE = (EditText) findViewById(R.id.editText6);

        final String firstName = firstNameE.getText().toString();
        final String lastName = lastNameE.getText().toString();
        final String emailID = emailIDE.getText().toString();
        final String password = passwordE.getText().toString();
        final String conPasswprd = conPasswprdE.getText().toString();
        final String number = numberE.getText().toString();

        // Condition cheakc korati registration hoite parar
        // number lenth check
        int numLen = number.length();

        // Passward same check
        boolean passsame = false;
        passsame = password.equals(conPasswprd);


        // Edu mail cheack
        boolean diuEdu = false;
        boolean mailcontChk = false;
        final String edu ="@diu.edu.bd";
        String userEdu ="";

        //Log.i("riz", "Length ->>>>>>>"+numLen);
        for (int i =0; i< emailID.length();i++){
            char c =emailID.charAt(i);
            if(c == '@'){
                mailcontChk = true;
            }
            if(mailcontChk){
                userEdu = userEdu+c;
            }

        }
        diuEdu = edu.equals(userEdu);
        Log.i("riz", "Edu cheack ->>>>>>>"+diuEdu);


        if (numLen != 11 ){
            Toast.makeText(Register.this, "Number is not equal to 11 digit", Toast.LENGTH_LONG).show();
        }
        if (passsame == false){
            Toast.makeText(Register.this, "Password is not same!!", Toast.LENGTH_LONG).show();
        }
        if (diuEdu == false){
            Toast.makeText(Register.this, "Your emailID is not from DIU!!", Toast.LENGTH_LONG).show();
        }

        




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
    }
}
