package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.sellersapp.Model.Usermodel;

import java.util.ArrayList;

public class Login_Screen extends AppCompatActivity {

    EditText username_et,password_et;
    Button login_btn;
    TextView register_tv,error_log;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Accounts").child("Normal Users");
    ArrayList<Usermodel> all_users;
    boolean account_exists;
    Usermodel loggedinuser;
    SharedPreferences sp;
    SharedPreferences log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__screen);

        create();
        databaselistener();
        textlistener();
        buttonlisteners();


    }

    public void create(){

        username_et = (EditText) findViewById(R.id.login_username_et);
        password_et = (EditText) findViewById(R.id.login_password_et);
        login_btn = (Button) findViewById(R.id.login_login_btn);
        register_tv = (TextView) findViewById(R.id.login_register_tv);
        account_exists = false;
        error_log = (TextView) findViewById(R.id.login_errorbox_tv);
        all_users = new ArrayList<>();
        sp = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        log = getSharedPreferences("loggedin",Context.MODE_PRIVATE);

    }

    public void databaselistener(){

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                all_users.clear();
                for(DataSnapshot snap:snapshot.getChildren()){

                    Usermodel user = snap.getValue(Usermodel.class);
                    all_users.add(user);


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void textlistener(){

        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_Screen.this,Register_Account.class);
                startActivity(i);
            }
        });

    }

    public void buttonlisteners(){


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                account_exists = false;

                for(Usermodel user:all_users){
                    String Username = username_et.getText().toString();
                    String Password = password_et.getText().toString();
                    if(user.getUsername().equalsIgnoreCase(Username) && user.getPassword().contentEquals(Password)){
                        account_exists = true;
                        loggedinuser = user;
                    }

                }

                SharedPreferences.Editor logeditor = log.edit();
                if(account_exists == true){



                    error_log.setText("Logged In!");
                    Intent i = new Intent(Login_Screen.this,BaseActivity.class);
                    i.putExtra("loggedin",true);

                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString("Firstname", loggedinuser.getFirstname());
                    editor.putString("Lastname", loggedinuser.getLastname());
                    editor.putString("Password", loggedinuser.getPassword());
                    editor.putString("Phonenumber", loggedinuser.getPhonenumber());
                    editor.putString("Username", loggedinuser.getUsername());
                    editor.putBoolean("isadmin", loggedinuser.isIsadmin());
                    editor.putString("key", loggedinuser.getKey());
                    logeditor.putBoolean("isloggedin",true);
                    editor.commit();
                    logeditor.commit();


                    startActivity(i);
                    finish();

                }else{

                    error_log.setText("Invalid Username Or Password!");


                }


            }
        });


    }

}