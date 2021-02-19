package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.sellersapp.Adapters.displaycategory;
import com.mohammed.sellersapp.Model.additemmodel;
import com.mohammed.sellersapp.Model.categorymodel;

import java.util.ArrayList;

public class delete_Item extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<additemmodel> categoryname;
    ArrayList<categorymodel> categoryimage;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Category");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete__item);

        rv = (RecyclerView) findViewById(R.id.deleteitem_selectcategory_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        categoryname = new ArrayList<>();
        categoryimage = new ArrayList<>();

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                categoryimage.clear();
                categoryname.clear();
                for(DataSnapshot snap:snapshot.getChildren()){

                    additemmodel name = snap.child("CategoryFiles").getValue(additemmodel.class);
                    categorymodel image = snap.child("CategoryFiles").child("Uri").getValue(categorymodel.class);
                    categoryname.add(name);
                    categoryimage.add(image);

                }

                displaycategory adapter = new displaycategory(delete_Item.this,categoryname,categoryimage);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}