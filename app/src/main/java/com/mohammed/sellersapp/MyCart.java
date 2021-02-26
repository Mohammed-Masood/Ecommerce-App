package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.sellersapp.Adapters.mycartadapter;
import com.mohammed.sellersapp.Model.OrderModel;

import java.util.ArrayList;

public class MyCart extends AppCompatActivity {

    SharedPreferences userdetails;
    String Userkey;
    DatabaseReference root;

    ArrayList<OrderModel> items_in_cart;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        create();

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                items_in_cart.clear();
            for(DataSnapshot snap :snapshot.getChildren()){

                OrderModel model = snap.getValue(OrderModel.class);
                items_in_cart.add(model);

            }
            mycartadapter adapter = new mycartadapter(items_in_cart,MyCart.this);
            rv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void create(){

        userdetails = getSharedPreferences("UserDetails",MODE_PRIVATE);
        Userkey = userdetails.getString("key","");
        root = FirebaseDatabase.getInstance().getReference("Accounts").child("Normal Users").child(Userkey).child("Cart");
        rv = (RecyclerView) findViewById(R.id.my_cart_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        items_in_cart = new ArrayList<>();
    }


}