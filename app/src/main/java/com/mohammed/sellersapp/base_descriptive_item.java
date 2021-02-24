package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.sellersapp.Model.categorymodel;
import com.mohammed.sellersapp.Model.itemmodel;

import org.w3c.dom.Text;

public class base_descriptive_item extends AppCompatActivity {

    String Categoryname;
    String Key;
    DatabaseReference root;
    itemmodel item;
    categorymodel image;
    ImageView iv;
    TextView itemname,itemprice,itemamount;
    EditText amountrequired,location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_descriptive_item);

            create();
        onAmountChange();
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                item = snapshot.getValue(itemmodel.class);


                image = snapshot.child("Uri").getValue(categorymodel.class);

                Glide.with(base_descriptive_item.this).load(image.getImageurl()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(iv);

                itemname.setText(item.getItemName());
                itemamount.setText(item.getAmount());
                itemprice.setText(item.getPrice() + "$");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void create(){
        Categoryname = getIntent().getStringExtra("Categoryname");
        Key = getIntent().getStringExtra("itemkey");
        root = FirebaseDatabase.getInstance().getReference("Category").child(Categoryname).child("ItemFiles").child(Key);
        iv = (ImageView) findViewById(R.id.base_descriptive_iv);
        itemname = (TextView) findViewById(R.id.base_descriptive_itemname);
        amountrequired = (EditText) findViewById(R.id.base_descriptive_amountrequired);
        location = (EditText) findViewById(R.id.base_descriptive_location);
        itemprice = (TextView) findViewById(R.id.base_descriptive_itemprice);
        itemamount = (TextView) findViewById(R.id.amount_in_stock);
        amountrequired.setText("1");

    }

    public void onAmountChange(){

       amountrequired.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           try {
               int temp = Integer.parseInt(item.getPrice());
               int temp2 = Integer.parseInt(s.toString());
               itemprice.setText(temp * temp2 + "$");
           }catch (NumberFormatException e){
               itemprice.setText("0$");

           }



           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });


    }
}