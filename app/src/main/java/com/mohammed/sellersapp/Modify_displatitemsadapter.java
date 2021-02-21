package com.mohammed.sellersapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DatabaseReference;
import com.mohammed.sellersapp.Adapters.displayitems;
import com.mohammed.sellersapp.Model.categorymodel;
import com.mohammed.sellersapp.Model.itemmodel;

import java.util.ArrayList;

public class Modify_displatitemsadapter extends RecyclerView.Adapter<Modify_displatitemsadapter.viewholder> {

    Context context;
    ArrayList<itemmodel> iteminformation;
    ArrayList<categorymodel> itemimage;
    ArrayList<String> key;
    DatabaseReference root;
    String path;


    public Modify_displatitemsadapter(Context context, ArrayList<itemmodel> iteminformation, ArrayList<categorymodel> itemimage, DatabaseReference root, ArrayList<String> key, String path) {
        this.context = context;
        this.iteminformation = iteminformation;
        this.itemimage = itemimage;
        this.root = root;
        this.key = key;
        this.path = path;
    }

    @NonNull
    @Override
    public Modify_displatitemsadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_view,parent,false);

        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Modify_displatitemsadapter.viewholder holder, int position) {

        if(iteminformation.get(position).isInStock()){
            holder.available.setText("Available");
        }else{
            holder.available.setText("Out Of Stock");
        }
        holder.itemprice.setText(iteminformation.get(position).getPrice());
        holder.itemname.setText(iteminformation.get(position).getItemName());

        Glide.with(context).load(itemimage.get(position).getImageurl()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(holder.iv);

        holder.item_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context,ModifyItemPage.class);
                i.putExtra("itemname",iteminformation.get(position).getItemName());
                i.putExtra("itemprice",iteminformation.get(position).getPrice());
                i.putExtra("itemstock",iteminformation.get(position).isInStock());
                i.putExtra("path",path);
                i.putExtra("key",key.get(position));

                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return itemimage.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView itemname,itemprice,available;
        ImageView iv;
        CardView item_cv;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            item_cv = itemView.findViewById(R.id.item_cv);
            iv = itemView.findViewById(R.id.itemview_display);
            itemname = itemView.findViewById(R.id.itemname_display);
            itemprice = itemView.findViewById(R.id.itemprice_display);
            available = itemView.findViewById(R.id.itemview_instock);

        }
    }














}
