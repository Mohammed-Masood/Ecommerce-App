package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        create();

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


    }







}