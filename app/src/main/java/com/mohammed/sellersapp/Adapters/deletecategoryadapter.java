package com.mohammed.sellersapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mohammed.sellersapp.Model.additemmodel;
import com.mohammed.sellersapp.Model.categorymodel;
import com.mohammed.sellersapp.R;
import com.mohammed.sellersapp.delete_Category;

import java.util.ArrayList;

public class deletecategoryadapter extends RecyclerView.Adapter<deletecategoryadapter.viewholder> {

    ArrayList<categorymodel> url;
    ArrayList<additemmodel> categoryname;
    Context context;
    Button yes,no;
    CardView cardView;
    TextView displayname;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Category");

    public deletecategoryadapter(ArrayList<categorymodel> url, ArrayList<additemmodel> categoryname, Context context, Button yes, Button no, CardView cardView, TextView displayname) {
        this.url = url;
        this.categoryname = categoryname;
        this.context = context;
        this.yes = yes;
        this.no = no;
        this.cardView = cardView;
        this.displayname = displayname;
    }

    @NonNull
    @Override
    public deletecategoryadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.additem_layout,parent,false);

        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull deletecategoryadapter.viewholder holder, int position) {

    holder.name.setText(categoryname.get(position).getCategoryname());
    Glide.with(context).load(url.get(position).getImageurl()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(holder.categoryimage);

    holder.cv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        cardView.setVisibility(View.VISIBLE);
        displayname.setText(holder.name.getText().toString());
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            root.child(holder.name.getText().toString()).removeValue();
                cardView.setVisibility(View.INVISIBLE);

            }
        });

        }
    });


    }

    @Override
    public int getItemCount() {
        return url.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        ImageView categoryimage;
        TextView name;
        CardView cv;
        public viewholder(@NonNull View itemView) {
            super(itemView);


            categoryimage = itemView.findViewById(R.id.additem_iv);
            name = itemView.findViewById(R.id.additem_tv);
            cv = itemView.findViewById(R.id.additem_cv);

        }


    }

}
