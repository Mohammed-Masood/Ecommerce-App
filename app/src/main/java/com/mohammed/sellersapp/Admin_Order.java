package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.sellersapp.Adapters.ordersadapter;
import com.mohammed.sellersapp.Model.Usermodel;
import com.mohammed.sellersapp.Model.fbordermodel;
import com.mohammed.sellersapp.Model.orderdetailsmodel;
import com.mohammed.sellersapp.Model.retreiveordermodel;

import java.util.ArrayList;

public class Admin_Order extends AppCompatActivity {

    RecyclerView rv;
    DatabaseReference root;
    ArrayList<String> key;
    ArrayList<String> location;
    ArrayList<String> time;
    ArrayList<String> orderkey;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__order);

        create();
        db();




    }

    public void create(){

        rv = (RecyclerView) findViewById(R.id.admin_orders_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        root = FirebaseDatabase.getInstance().getReference("Order");
        key = new ArrayList<>();
        location = new ArrayList<>();
        tv = (TextView) findViewById(R.id.textView37);
        orderkey = new ArrayList<>();
        time = new ArrayList<>();
    }


    public void db(){


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                key.clear();
                location.clear();
                orderkey.clear();
                time.clear();
            for(DataSnapshot snap :snapshot.getChildren()){


            orderdetailsmodel model = snap.getValue(orderdetailsmodel.class);
            key.add(model.getUserKey());
            location.add(model.getLocation());
            orderkey.add(model.getOrderKey());
            time.add(model.getTime());



            }
            ordersadapter adapter = new ordersadapter(key,location,Admin_Order.this,orderkey,time);
            rv.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });











    }
}