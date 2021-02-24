package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Filterable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.sellersapp.Adapters.base_item_adapter;
import com.mohammed.sellersapp.Model.categorymodel;
import com.mohammed.sellersapp.Model.itemmodel;

import java.util.ArrayList;

public class base_items extends AppCompatActivity {

    RecyclerView rv;
    String CategoryName;
    DatabaseReference dbrf;
    ArrayList<itemmodel> items;
    ArrayList<categorymodel> images;
    ArrayList<String> key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_items);

        create();

        dbrf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                items.clear();
                images.clear();
                key.clear();
                for(DataSnapshot snap:snapshot.getChildren()){

                    itemmodel item = snap.getValue(itemmodel.class);
                    categorymodel image = snap.child("Uri").getValue(categorymodel.class);

                    items.add(item);
                    images.add(image);
                    key.add(snap.getKey());
                }
                base_item_adapter adapter = new base_item_adapter(base_items.this,items,images,CategoryName,key);
                rv.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void create(){
        rv = (RecyclerView) findViewById(R.id.base_item_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        CategoryName = getIntent().getStringExtra("Categoryname");
        dbrf = FirebaseDatabase.getInstance().getReference("Category").child(CategoryName).child("ItemFiles");
        items = new ArrayList<>();
        images = new ArrayList<>();
        key = new ArrayList<>();


    }
}