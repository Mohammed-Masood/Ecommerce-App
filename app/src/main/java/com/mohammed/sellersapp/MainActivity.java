package com.mohammed.sellersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Intent create_Category;
    Intent add_item;
    Intent Delete_Category;
    Intent Delete_Item;
    Intent Modify_Category;
    Intent Modify_Items;
    Button btn,btn2,btn3,btn4,btn5,btn6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn6 = (Button) findViewById(R.id.button5);
        btn4 = (Button) findViewById(R.id.del_it);
        btn = (Button) findViewById(R.id.button);
        create_Category = new Intent(this,Create_Category.class);
        add_item = new Intent(this,Add_Item.class);
        Delete_Category = new Intent(this,delete_Category.class);
        Modify_Items = new Intent(this,ModifyItems.class);
        Delete_Item = new Intent(MainActivity.this,delete_Item.class);
        Modify_Category = new Intent(MainActivity.this,ModifyCategory.class);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(create_Category);
            }
        });

        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(add_item);
            }
        });
        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(Delete_Category);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(Delete_Item);
            }
        });
        btn5 = (Button) findViewById(R.id.button4);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Modify_Category);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Modify_Items);
            }
        });

    }
}