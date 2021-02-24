package com.mohammed.sellersapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

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
import com.mohammed.sellersapp.Model.itemmodel;

import java.util.HashMap;

public class ModifyItemPage extends AppCompatActivity {


   ImageView iv;
   EditText item_name,Item_price,amount;
   CheckBox in_stock;
   TextView errormsg;
   Button savechanges;
    String itemname,itemprice,path,key;
    Uri imageuri;
    boolean instock;
    ProgressBar progressBar;
    DatabaseReference root;
    StorageReference storage = FirebaseStorage.getInstance().getReference();
    int checker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item_page);
        checker =0;
        path = getIntent().getStringExtra("path");
        key =getIntent().getStringExtra("key");
        amount = (EditText) findViewById(R.id.modify_amtstock);
        amount.setText(getIntent().getStringExtra("amount"));
        progressBar = (ProgressBar)findViewById(R.id.progressBar23);
        progressBar.setVisibility(View.INVISIBLE);
        iv = (ImageView) findViewById(R.id.modify_item_img);
        item_name = (EditText) findViewById(R.id.modify_item_name);
        Item_price = (EditText) findViewById(R.id.modify_item_price);
        in_stock = (CheckBox) findViewById(R.id.modify_item_instock);
        errormsg = (TextView) findViewById(R.id.errr);
        savechanges = (Button) findViewById(R.id.btn_save_item);
        itemname = getIntent().getStringExtra("itemname");
        itemprice = getIntent().getStringExtra("itemprice");
        instock = getIntent().getBooleanExtra("itemstock",false);

        item_name.setText(itemname);
        Item_price.setText(itemprice);

        if(instock){
            in_stock.setChecked(true);
        }else{
            in_stock.setChecked(false);
        }
        onclickimage();
        onsavechanges();

        root = FirebaseDatabase.getInstance().getReference("Category").child(path).child("ItemFiles").child(key);


    }


    public void onclickimage(){

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

        if(requestCode == 2 && resultCode == RESULT_OK && data!=null){

            imageuri = data.getData();
            iv.setImageURI(imageuri);
            checker =1;

        }else if (requestCode == 2 && resultCode == RESULT_OK && data==null){
            checker =0;
        }

    }

    public void onsavechanges(){


        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(checker ==1) {
                    if(TextUtils.isEmpty(item_name.getText())){
                        errormsg.setText("Cannot Leave Item Name Blank!");

                    }else {

                        if(TextUtils.isEmpty(Item_price.getText())){
                            errormsg.setText("Cannot Leave Item Price Blank! If You Want It To Be 0$ Set It To 0$ .");

                        }else {
                            uploadtofirebase(imageuri);
                        }
                    }
                }else{
                    errormsg.setText("Insert An Image To Proceed!");
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

                       hashmap.put("InStock",in_stock.isChecked());
                       hashmap.put("ItemName",item_name.getText().toString());
                       hashmap.put("Price",Item_price.getText().toString());
                        String amountinstock = amount.getText().toString();
                        if(TextUtils.isEmpty(amount.getText())){
                            hashmap.put("Amount","0");
                        }else {
                            hashmap.put("Amount", amountinstock);
                        }


                        categorymodel model = new categorymodel(uri.toString());
                        hashmap.put("Uri",model);

                        root.setValue(hashmap);



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