package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.sellersapp.Adapters.deletecategoryadapter;
import com.mohammed.sellersapp.Model.additemmodel;
import com.mohammed.sellersapp.Model.categorymodel;

import java.util.ArrayList;

public class delete_Category extends AppCompatActivity {

    RecyclerView rv;
    deletecategoryadapter adapter;
    ArrayList<categorymodel> url;
    ArrayList<additemmodel> categoryname;
    CardView cv;
    Button yes,no;
    TextView Displayname;

    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Category");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete__category);

        rv = (RecyclerView) findViewById(R.id.deletecategory_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        url = new ArrayList<>();
        categoryname = new ArrayList<>();
        cv = (CardView) findViewById(R.id.confirm_delete_Category);
        yes = (Button) findViewById(R.id.yes_Category);
        no = (Button) findViewById(R.id.no_Category);
        Displayname = (TextView) findViewById(R.id.display_category_delete);
        cv.setVisibility(View.INVISIBLE);
        nobutton();

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                url.clear();
                categoryname.clear();

                for(DataSnapshot snap : snapshot.getChildren()){

                    additemmodel name = snap.child("CategoryFiles").getValue(additemmodel.class);
                    categorymodel uri =  snap.child("CategoryFiles").child("Uri").getValue(categorymodel.class);
                    url.add(uri);
                    categoryname.add(name);
                }

                adapter = new deletecategoryadapter(url,categoryname,delete_Category.this,yes,no,cv,Displayname);
                rv.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









    }

    void nobutton(){
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.setVisibility(View.INVISIBLE);
            }
        });
    }
}