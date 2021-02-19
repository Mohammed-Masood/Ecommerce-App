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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mohammed.sellersapp.Model.additemmodel;
import com.mohammed.sellersapp.Model.categorymodel;

import java.util.HashMap;

public class ModifyCategorypage extends AppCompatActivity {

    EditText et;
    String path;
    ImageView iv;
    Button savechanges;
    DatabaseReference currentroot;
    DatabaseReference urlroot;
    Uri imageuri;
    ProgressBar progressBar;
    StorageReference storage = FirebaseStorage.getInstance().getReference();
    TextView errormsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_categorypage);
        errormsg = (TextView) findViewById(R.id.err);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
        savechanges = (Button)findViewById(R.id.savechanges_category);
        iv = (ImageView)findViewById(R.id.categoryimage_modify);
        et = (EditText) findViewById(R.id.categoryname_modify);
        path = getIntent().getStringExtra("categoryname");
        currentroot = FirebaseDatabase.getInstance().getReference("Category").child(path).child("CategoryFiles");
        urlroot = currentroot.child("Uri");
        savechangesbutton();
        buttonimageclick();
        currentroot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                    additemmodel cn = snapshot.getValue(additemmodel.class);
                    String categoryname = cn.getCategoryname();
                    et.setText(categoryname);


                urlroot.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        categorymodel ci = snapshot.getValue(categorymodel.class);
                        Glide.with(ModifyCategorypage.this).load(ci.getImageurl()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(iv);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public void buttonimageclick(){

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallary = new Intent();
                gallary.setAction(Intent.ACTION_GET_CONTENT);
                gallary.setType("image/*");
                startActivity(gallary);
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

public void savechangesbutton(){


        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(et.getText())){

                }else{

                    uploadtofirebase(imageuri);






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
                        String newname = et.getText().toString();
                        hashmap.put("CategoryName",newname);
                        categorymodel model = new categorymodel(uri.toString());
                        hashmap.put("Uri",model);
                        currentroot.setValue(hashmap);

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