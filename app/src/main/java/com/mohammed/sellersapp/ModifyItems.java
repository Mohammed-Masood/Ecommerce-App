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
import com.mohammed.sellersapp.Adapters.ModifyCategoryAdapter;
import com.mohammed.sellersapp.Adapters.ModifyItemAdapter;
import com.mohammed.sellersapp.Model.additemmodel;
import com.mohammed.sellersapp.Model.categorymodel;

import java.util.ArrayList;

public class ModifyItems extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<additemmodel> models;
    ArrayList<categorymodel> model2;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Category");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_items);


        models = new ArrayList<>();
        model2 = new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.recyclerView11);
        rv.setHasFixedSize(true);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                models.clear();
                model2.clear();
                for(DataSnapshot snap:snapshot.getChildren()){


                    additemmodel newmodel = snap.child("CategoryFiles").getValue(additemmodel.class);
                    categorymodel newmodel2 = snap.child("CategoryFiles").child("Uri").getValue(categorymodel.class);
                    models.add(newmodel);
                    model2.add(newmodel2);

                }
                ModifyItemAdapter adapter = new ModifyItemAdapter(models,model2,ModifyItems.this);
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(ModifyItems.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }







}