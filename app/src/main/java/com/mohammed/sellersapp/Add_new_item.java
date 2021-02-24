package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mohammed.sellersapp.Model.categorymodel;

import java.util.HashMap;

public class Add_new_item extends AppCompatActivity {

        ImageView iv;
        EditText itemname_et,price_et,amount;
        Button add_btn;
        TextView errormsg;
        CheckBox instock;
        String path;
        Uri imageuri;
        ProgressBar progressBar;
        boolean stock;
        private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Category");
        DatabaseReference c;
        StorageReference storage = FirebaseStorage.getInstance().getReference();
        TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        stock =false;
        iv = (ImageView) findViewById(R.id.additem_addimage);
        amount = (EditText) findViewById(R.id.additem_amtstock);
        itemname_et = (EditText) findViewById(R.id.additem_itemname);
        price_et = (EditText) findViewById(R.id.additem_itemprice);
        add_btn = (Button) findViewById(R.id.additem_btn);
        errormsg = (TextView) findViewById(R.id.error_additem);
        instock = (CheckBox) findViewById(R.id.instock_checkbox);
        progressBar = (ProgressBar) findViewById(R.id.progbar_additem);
         path = getIntent().getStringExtra("categoryname");
        c = root.child(path);
        progressBar.setVisibility(View.INVISIBLE);
        onimgclick();
        addclick();
        tv = (TextView) findViewById(R.id.textView3) ;
        tv.setText("Insert Item Into "+path);


    }


    public void onimgclick(){

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallary = new Intent();
                gallary.setAction(Intent.ACTION_GET_CONTENT);
                gallary.setType("image/*");
                startActivityForResult(gallary,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data !=null){

            imageuri = data.getData();
            iv.setImageURI(imageuri);

        }
    }


    public void addclick(){

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imageuri != null){
                    if(TextUtils.isEmpty(itemname_et.getText())){
                        errormsg.setText("Please Input A Category Name!");

                    }else{
                      if(TextUtils.isEmpty(price_et.getText())) {
                          errormsg.setText("Please Input A Price!");
                      }else{
                          uploadtofirebase(imageuri);
                      }
                    }

                }else{

                    errormsg.setText("Please Select An Image!");

                }

            }
        });



    }


    public void uploadtofirebase(Uri uri){
        StorageReference fileref = storage.child(System.currentTimeMillis() + "." + getfilextention(uri));
        fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        progressBar.setVisibility(View.INVISIBLE);
                        errormsg.setText("Item Added Successfully!!");

                        HashMap<String,Object> hashmap = new HashMap<>();

                        categorymodel model = new categorymodel(uri.toString());
                        String itemname = itemname_et.getText().toString();
                        String itemprice = price_et.getText().toString();
                        hashmap.put("Uri",model);
                        hashmap.put("ItemName",itemname);
                        hashmap.put("Price",itemprice);
                        if(instock.isChecked()){
                            stock = true;
                        }else{
                            stock = false;
                        }
                        hashmap.put("InStock",stock);
                        String amountinstock = amount.getText().toString();
                        if(TextUtils.isEmpty(amount.getText())){
                            hashmap.put("Amount","0");
                        }else {
                            hashmap.put("Amount", amountinstock);
                        }

                        c.child("ItemFiles").push().setValue(hashmap);


                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                errormsg.setText("Working On It!! Give It A Second...");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                errormsg.setText("Couldnt Save The Image Due To Some Unknown Error!!");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public String getfilextention(Uri uri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(cr.getType(uri));

    }






}