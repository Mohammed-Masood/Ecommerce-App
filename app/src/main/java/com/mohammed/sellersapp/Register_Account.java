package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class Register_Account extends AppCompatActivity {

    EditText username_et,password_et,fn_et,ln_et,phonenumber_et;
    Button register_btn;
    TextView tv;
    boolean invalid_username;
    ArrayList<String> all_usernames;

    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Accounts");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__account);

        create();
        buttonclick();
        databaselistener();

    }
    public void create(){

        username_et = (EditText) findViewById(R.id.register_username_et);
        password_et = (EditText) findViewById(R.id.register_password_et);
        fn_et = (EditText) findViewById(R.id.register_firstname_et);
        ln_et = (EditText) findViewById(R.id.register_lastname_et);
        phonenumber_et = (EditText) findViewById(R.id.register_phonenumber_et);
        register_btn = (Button) findViewById(R.id.register_account_btn);
        tv = (TextView) findViewById(R.id.register_error_et);
        all_usernames = new ArrayList<>();
        invalid_username = false;

    }

    public void buttonclick(){

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(username_et.getText()) || TextUtils.isEmpty(password_et.getText()) || TextUtils.isEmpty(fn_et.getText()) || TextUtils.isEmpty(ln_et.getText()) || TextUtils.isEmpty(phonenumber_et.getText())){

                    tv.setText("Please Fill In All Of The Fields");

                }else{

                    registeraccount();

                }



            }
        });



    }

    public void databaselistener(){
        DatabaseReference newdbrf = root.child("Normal Users");
        newdbrf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            all_usernames.clear();
            for(DataSnapshot snap:snapshot.getChildren()){

                Usermodel user = snap.getValue(Usermodel.class);
               all_usernames.add(user.getUsername());

            }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void registeraccount(){

        invalid_username = false;
        for(String name:all_usernames){
            String username = username_et.getText().toString();
            if(name.equalsIgnoreCase(username)){
                invalid_username = true;
            }

        }

        if(invalid_username == false){

            HashMap<String,Object> hashMap = new HashMap<>();

            hashMap.put("Username",username_et.getText().toString());
            hashMap.put("Password",password_et.getText().toString());
            hashMap.put("Firstname",fn_et.getText().toString());
            hashMap.put("Lastname",ln_et.getText().toString());
            hashMap.put("Phonenumber",phonenumber_et.getText().toString());
            hashMap.put("isadmin",false);
           String sr= root.child("Normal Users").push().getKey();
           hashMap.put("key",sr);
            root.child("Normal Users").child(sr).setValue(hashMap);
            tv.setText("Account Created");

            Intent i = new Intent(Register_Account.this,Login_Screen.class);
            startActivity(i);
            finish();

        }else{
            tv.setText("Username Already Taken!");
        }




    }
}