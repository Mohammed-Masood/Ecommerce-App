package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.sellersapp.Adapters.mycartadapter;
import com.mohammed.sellersapp.Model.OrderModel;
import com.mohammed.sellersapp.Model.fbordermodel;
import com.mohammed.sellersapp.Model.retreiveordermodel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MyCart extends AppCompatActivity {

    SharedPreferences userdetails;
    String Userkey;
    DatabaseReference root;
    SharedPreferences price;
    ArrayList<OrderModel> items_in_cart;
    RecyclerView rv;
    TextView totalpriceincart,success;
    int totalprice;
    Button buynow,proceed;
    retreiveordermodel Order;
    ArrayList<fbordermodel> Order_Details;
    String User_key;
    EditText location;
    CardView card;
    ImageView close,checkcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        create();


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                items_in_cart.clear();
                totalprice = 0;
            for(DataSnapshot snap :snapshot.getChildren()){

                OrderModel model = snap.getValue(OrderModel.class);
                items_in_cart.add(model);


            }
            mycartadapter adapter = new mycartadapter(items_in_cart,MyCart.this);
            rv.setAdapter(adapter);

            for(OrderModel model:items_in_cart){
                totalprice += model.getTotalPrice();

            }
                totalpriceincart.setText(totalprice + "$");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        buttonclicklisterners();

    }

    public void create(){

        userdetails = getSharedPreferences("UserDetails",MODE_PRIVATE);
        price = getSharedPreferences("TotalPrice",MODE_PRIVATE);
        Userkey = userdetails.getString("key","");
        root = FirebaseDatabase.getInstance().getReference("Accounts").child("Normal Users").child(Userkey).child("Cart");
        rv = (RecyclerView) findViewById(R.id.my_cart_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        items_in_cart = new ArrayList<>();
        totalpriceincart = (TextView) findViewById(R.id.totalpriceincart);
        buynow = (Button) findViewById(R.id.buy_now);
        Order_Details = new ArrayList<>();
        success = (TextView) findViewById(R.id.success);
        success.setVisibility(View.INVISIBLE);
        card = (CardView) findViewById(R.id.cart_proceedview);
        location = (EditText) findViewById(R.id.cart_location);
        proceed = (Button) findViewById(R.id.cart_proceed);
        card.setVisibility(View.INVISIBLE);
        close = (ImageView) findViewById(R.id.cart_close);
        checkcart = (ImageView) findViewById(R.id.check_cart);
        checkcart.setVisibility(View.INVISIBLE);
    }

    public void buttonclicklisterners(){

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(items_in_cart.isEmpty()) {

                }else{
                    card.setVisibility(View.VISIBLE);
                }


            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(location.getText())){
                    Toast.makeText(MyCart.this, "Enter A Location To Proceed", Toast.LENGTH_SHORT).show();
                }else {


                    DatabaseReference dbrf = FirebaseDatabase.getInstance().getReference("Order");
                    String newkey = dbrf.push().getKey();
                    HashMap<String,Object> hashMap = new HashMap<>();
                    String loc = location.getText().toString();
                    hashMap.put("Location",loc);

                    for (OrderModel model : items_in_cart) {
                        fbordermodel mod = new fbordermodel(model.getAmountRequired(), model.getItemKey(), model.getTotalPrice(),model.getCategoryname());
                        Order_Details.add(mod);
                        User_key = model.getUserid();
                    }

                    hashMap.put("UserKey",User_key);
                    hashMap.put("OrderKey",newkey);

                    String time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                    hashMap.put("Time",time);

                    dbrf.child(newkey).setValue(hashMap);


                    DatabaseReference newdb = dbrf.child(newkey).child("Z Items");

                    for(fbordermodel i:Order_Details){

                        String ItemKey = i.getItemKey();
                        int AmountRequired = i.getAmountRequired();
                        int TotalPrice = i.getTotalPrice();
                        HashMap<String,Object> ha = new HashMap<>();
                        ha.put("ItemKey",ItemKey);
                        ha.put("AmountRequired",AmountRequired);
                        ha.put("TotalPrice",TotalPrice);
                        ha.put("Categoryname",i.getCategoryname());
                        newdb.push().setValue(ha);
                        checkcart.setVisibility(View.VISIBLE);

                    }

                    success.setVisibility(View.VISIBLE);
                    root.removeValue();
                    card.setVisibility(View.INVISIBLE);







                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.setVisibility(View.INVISIBLE);
            }
        });


    }


}