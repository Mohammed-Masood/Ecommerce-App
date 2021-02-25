package com.mohammed.sellersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyProfile extends AppCompatActivity {

    TextView fullname,username,phonenumber;
    SharedPreferences sp,log;
    String fn,ln,user_name,phone_number;
    Button logout,adminview;
    boolean isadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        create();
        buttonlisteners();


    }

    public void create(){

        fullname = (TextView) findViewById(R.id.profile_fullname_tv);
        username = (TextView) findViewById(R.id.profile_username_tv);
        phonenumber = (TextView) findViewById(R.id.profile_phonenumber_tv);
        adminview = (Button) findViewById(R.id.profile_adminview_btn);
        sp = getSharedPreferences("UserDetails",MODE_PRIVATE);
        log = getSharedPreferences("loggedin", Context.MODE_PRIVATE);


        fn = sp.getString("Firstname","");
        ln = sp.getString("Lastname","");
        user_name = sp.getString("Username","");
        phone_number = sp.getString("Phonenumber","");
        isadmin = sp.getBoolean("isadmin",false);
        fullname.setText(fn+" "+ln);
        username.setText(user_name);
        phonenumber.setText(phone_number);
        logout = (Button) findViewById(R.id.profile_logout_btn);

        if(isadmin == true){
            adminview.setVisibility(View.VISIBLE);
        }else{
            adminview.setVisibility(View.GONE);
        }
    }


    public void buttonlisteners(){

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sp.edit();
                SharedPreferences.Editor logeditor = log.edit();
                editor.putString("Firstname","");
                editor.putString("Lastname","");
                editor.putString("Password","");
                editor.putString("Phonenumber","");
                editor.putString("Username","");
                editor.putBoolean("isadmin",false);
                logeditor.putBoolean("isloggedin",false);
                editor.commit();
                logeditor.commit();

                Intent i = new Intent(MyProfile.this,BaseActivity.class);
                startActivity(i);
                finish();


            }
        });

        adminview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyProfile.this,MainActivity.class);
                startActivity(i);
            }
        });


    }
}