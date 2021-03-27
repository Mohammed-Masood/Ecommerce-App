package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.sellersapp.Adapters.orderitemsadapter;
import com.mohammed.sellersapp.Model.fbordermodel;

import java.util.ArrayList;

public class order_items extends AppCompatActivity {

    RecyclerView rv;
    String orderkey;
    DatabaseReference root;
    ArrayList<fbordermodel> items;
    TextView tp;
    int totalprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);

        create();
        db();

    }

    public void create(){

        rv = (RecyclerView) findViewById(R.id.order_items_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        orderkey = getIntent().getStringExtra("Orderkey");
        root = FirebaseDatabase.getInstance().getReference("Order").child(orderkey).child("Z Items");
        items = new ArrayList<>();
        totalprice = 0;
        tp = (TextView) findViewById(R.id.order_items_totalcost);
        tp.setText("0$");
    }


    public void db(){

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                items.clear();
                totalprice = 0;
                for(DataSnapshot snap : snapshot.getChildren()){

                    fbordermodel model = snap.getValue(fbordermodel.class);
                    items.add(model);
                    totalprice += model.getTotalPrice();



                }
                orderitemsadapter adapter = new orderitemsadapter(items,order_items.this);
                rv.setAdapter(adapter);
                tp.setText(totalprice + "$");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }

}