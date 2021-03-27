package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;

public class base_descriptive_item extends AppCompatActivity {

    String Categoryname;
    String Key;
    DatabaseReference root;
    itemmodel item;
    categorymodel image;
    ImageView iv,check;
    TextView itemname,itemprice,itemamount,error;
    EditText amountrequired;
    Button addtocart;
    DatabaseReference orders;
    SharedPreferences userdetails;
    SharedPreferences log;
    boolean isloggedin;
    String key;
    int totalprice,oldamnt,newamnt;
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

                Glide.with(getApplicationContext()).load(image.getImageurl()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(iv);

                itemname.setText(item.getItemName());
                itemamount.setText(item.getAmount());
                oldamnt =Integer.parseInt(item.getAmount());
                itemprice.setText(item.getPrice() + "$");
                totalprice = Integer.parseInt(item.getPrice());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        buttonlisteners();

    }

    public void create(){
        Categoryname = getIntent().getStringExtra("Categoryname");
        Key = getIntent().getStringExtra("itemkey");
        root = FirebaseDatabase.getInstance().getReference("Category").child(Categoryname).child("ItemFiles").child(Key);
        iv = (ImageView) findViewById(R.id.base_descriptive_iv);
        itemname = (TextView) findViewById(R.id.base_descriptive_itemname);
        amountrequired = (EditText) findViewById(R.id.base_descriptive_amountrequired);

        itemprice = (TextView) findViewById(R.id.base_descriptive_itemprice);
        itemamount = (TextView) findViewById(R.id.amount_in_stock);
        amountrequired.setText("1");
        addtocart = (Button) findViewById(R.id.addtocart_btn);
        userdetails = getSharedPreferences("UserDetails",MODE_PRIVATE);
        log = getSharedPreferences("loggedin", MODE_PRIVATE);
        isloggedin = log.getBoolean("isloggedin",false);
        key = userdetails.getString("key","");
        error = (TextView) findViewById(R.id.descriptive_items_error);
        check = (ImageView) findViewById(R.id.check);
        check.setVisibility(View.INVISIBLE);



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
               totalprice = temp*temp2;
           }catch (NumberFormatException e){
               itemprice.setText("0$");

           }



           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });


    }

    public void buttonlisteners(){

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isloggedin){

                    orders = FirebaseDatabase.getInstance().getReference("Accounts").child("Normal Users").child(key);

                    if(TextUtils.isEmpty(amountrequired.getText())){
                            error.setText("Please Fill In All The Fields");
                        }else{
                        int amnt = Integer.parseInt(amountrequired.getText().toString());
                        int available_amnt = Integer.parseInt(itemamount.getText().toString());
                        if(amnt > available_amnt){

                        error.setText("We Do Not Have That Many Available");
                        }else{
                            if(amnt ==0){
                                error.setText("You Cant Order 0 Items");
                            }else {

                                if(oldamnt-amnt == 0){
                                    newamnt = oldamnt-amnt;
                                    String i =String.valueOf(newamnt);
                                    root.child("Amount").setValue(i);
                                    root.child("InStock").setValue(false);

                                }else{
                                    newamnt = oldamnt-amnt;
                                    newamnt = oldamnt-amnt;
                                    String i =String.valueOf(newamnt);
                                root.child("Amount").setValue(i);
                                }

                                HashMap<String, Object> cart = new HashMap<>();
                                cart.put("ItemKey", Key);
                                cart.put("AmountRequired", amnt);
                                cart.put("TotalPrice", totalprice);
                                String Orderkey =  orders.child("Cart").push().getKey();
                                cart.put("OrderKey",Orderkey);
                                cart.put("Categoryname",Categoryname);
                                cart.put("Userid",userdetails.getString("key",""));
                                orders.child("Cart").child(Orderkey).setValue(cart);
                                error.setText("");
                                check.setVisibility(View.VISIBLE);
                                finish();


                            }
                        }


                    }

                }else{

                    Intent i = new Intent(base_descriptive_item.this,Login_Screen.class);
                    startActivity(i);

                }




            }
        });



    }
}