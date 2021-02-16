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

public class Create_Category extends AppCompatActivity {

    EditText category_name;
    ImageView image;
    Button add_btn;
    TextView errormsg;
    ProgressBar progressBar;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Category");
    private StorageReference storage = FirebaseStorage.getInstance().getReference();
    private Uri imageuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__category);

        category_name = (EditText) findViewById(R.id.categoryname_et);
        image = (ImageView) findViewById(R.id.category_image);
        add_btn = (Button) findViewById(R.id.addcategory_btn);
        errormsg = (TextView) findViewById(R.id.errorcategory_txt);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_category);


        progressBar.setVisibility(View.INVISIBLE);

            imageonclick();
            UploadData();

    }



public void imageonclick(){

        image.setOnClickListener(new View.OnClickListener() {
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

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){

            imageuri = data.getData();
            image.setImageURI(imageuri);


        }


    }


    public void UploadData(){

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imageuri != null){
                    if(TextUtils.isEmpty(category_name.getText())){
                        errormsg.setText("Please Input A Category Name!");

                    }else{

                        uploadtofirebase(imageuri);
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
                        errormsg.setText("Success!!!");

                        HashMap<String,String> hashmap = new HashMap<>();

                        categorymodel model = new categorymodel(uri.toString());
                        String modelid = root.child(category_name.getText().toString()).getKey();
                        hashmap.put("Uri",model.getImageurl());
                        hashmap.put("CategoryName",modelid);
                        root.child(modelid).child("CategoryFiles").setValue(hashmap);


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