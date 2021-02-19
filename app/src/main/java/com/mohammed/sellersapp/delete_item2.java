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
import com.mohammed.sellersapp.Adapters.displayitems;
import com.mohammed.sellersapp.Model.categorymodel;
import com.mohammed.sellersapp.Model.itemmodel;

import java.util.ArrayList;

public class delete_item2 extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<itemmodel> itemname;
    ArrayList<categorymodel> itemimage;
    ArrayList<String> key;
    DatabaseReference root;
    String path;
    CardView cv;
    TextView enter;
    Button yes,no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item2);
        cv = (CardView) findViewById(R.id.cv_delete_item) ;
        cv.setVisibility(View.INVISIBLE);
        enter = (TextView) findViewById(R.id.display_item_delete);
        yes = (Button) findViewById(R.id.yes_item);
        no = (Button) findViewById(R.id.no_item);
        onclick();
        path = getIntent().getStringExtra("catname");
        root = FirebaseDatabase.getInstance().getReference("Category").child(path).child("ItemFiles");
        rv = (RecyclerView) findViewById(R.id.display_item_rv2);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        itemname = new ArrayList<>();
        itemimage = new ArrayList<>();
        key = new ArrayList<>();

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                itemname.clear();
                itemimage.clear();
                key.clear();
                for(DataSnapshot snap:snapshot.getChildren()){


                    itemmodel info = snap.getValue(itemmodel.class);
                    categorymodel url = snap.child("Uri").getValue(categorymodel.class);
                    itemname.add(info);
                    itemimage.add(url);
                    key.add(snap.getKey());


                }

                displayitems adapter = new displayitems(delete_item2.this,itemname,itemimage,cv,enter,yes,no,root,key,path);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    public void onclick(){

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.setVisibility(View.INVISIBLE);
            }
        });

    }
}