package com.mohammed.sellersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Intent create_Category;
    Intent add_item;
    Intent Delete_Category;
    Intent Delete_Item;
    Intent Modify_Category;
    Intent Modify_Items;
    Intent Admin_Order;
    ImageView AddCategory,AddItem,DeleteCategory,Deleteitem,Modifycategory,Modifyitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddCategory = (ImageView) findViewById(R.id.addcategory_iv) ;
        create_Category = new Intent(this,Create_Category.class);
        add_item = new Intent(this,Add_Item.class);
        Delete_Category = new Intent(this,delete_Category.class);
        Modify_Items = new Intent(this,ModifyItems.class);
        Delete_Item = new Intent(MainActivity.this,delete_Item.class);
        Modify_Category = new Intent(MainActivity.this,ModifyCategory.class);
        Admin_Order = new Intent(MainActivity.this, com.mohammed.sellersapp.Admin_Order.class);
        AddItem = (ImageView) findViewById(R.id.additemtoDb_iv) ;
        DeleteCategory = (ImageView) findViewById(R.id.deletecategoryfromdb) ;
        Deleteitem = (ImageView) findViewById(R.id.deleteitemfromdb) ;
        Modifycategory = (ImageView) findViewById(R.id.modifycategoryfromdb) ;
        Modifyitem = (ImageView) findViewById(R.id.modifyitemfromdb) ;


        CreateListeners();



    }

    public void CreateListeners(){

        AddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(create_Category);
            }
        });

        AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(add_item);
            }
        });

        DeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Delete_Category);
            }
        });

        Deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Delete_Item);
            }
        });

        Modifycategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Modify_Category);
            }
        });


       Modifyitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Modify_Items);
            }
        });





    }
}