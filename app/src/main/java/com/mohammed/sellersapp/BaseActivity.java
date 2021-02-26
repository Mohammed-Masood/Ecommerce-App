package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.sellersapp.Adapters.baseadapter;
import com.mohammed.sellersapp.Model.additemmodel;
import com.mohammed.sellersapp.Model.categorymodel;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<categorymodel> category_images;
    ArrayList<additemmodel>  category_name;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Category");
    ImageView profile,cart;
    boolean loggedin;
    SharedPreferences log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        create();
        onclicks();
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                category_name.clear();
                category_images.clear();
                for(DataSnapshot snap:snapshot.getChildren()){

                    additemmodel newname = snap.child("CategoryFiles").getValue(additemmodel.class);
                    categorymodel newimage = snap.child("CategoryFiles").child("Uri").getValue(categorymodel.class);

                    category_images.add(newimage);
                    category_name.add(newname);

                }

                baseadapter adapter = new baseadapter(BaseActivity.this,category_images,category_name);
                rv.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    public void create(){

        rv = (RecyclerView) findViewById(R.id.category_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        category_images = new ArrayList<>();
        category_name = new ArrayList<>();
        profile = (ImageView) findViewById(R.id.myprofile_iv);
        cart = (ImageView) findViewById(R.id.mycart_iv);
        log = getApplicationContext().getSharedPreferences("loggedin", Context.MODE_PRIVATE);
        loggedin = log.getBoolean("isloggedin",false);


    }

    public void onclicks(){

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(loggedin == true){

                Intent i = new Intent(BaseActivity.this,MyProfile.class);
                startActivity(i);

            }else{

                Intent i = new Intent(BaseActivity.this,Login_Screen.class);
                startActivity(i);

            }

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(BaseActivity.this,MyCart.class);
                startActivity(i);


            }
        });

    }







}