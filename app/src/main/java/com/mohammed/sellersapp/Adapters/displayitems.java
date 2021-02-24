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
import com.mohammed.sellersapp.Model.additemmodel;
import com.mohammed.sellersapp.Model.categorymodel;
import com.mohammed.sellersapp.Model.itemmodel;
import com.mohammed.sellersapp.R;
import com.mohammed.sellersapp.delete_Category;
import com.mohammed.sellersapp.delete_item2;

import java.util.ArrayList;

public class displayitems extends RecyclerView.Adapter<displayitems.viewholder> {

    Context context;
    ArrayList<itemmodel> iteminformation;
    ArrayList<categorymodel> itemimage;
    ArrayList<String> key;
    CardView cv;
    TextView enter;
    Button yes,no;
    DatabaseReference root;
    String path;
    public displayitems(Context context, ArrayList<itemmodel> iteminformation, ArrayList<categorymodel> itemimage, CardView cv, TextView enter, Button yes, Button no,DatabaseReference root,ArrayList<String> key,String path) {
        this.context = context;
        this.iteminformation = iteminformation;
        this.itemimage = itemimage;
        this.cv = cv;
        this.enter = enter;
        this.yes = yes;
        this.no = no;
        this.root = root;
        this.key = key;
        this.path = path;
    }

    @NonNull
    @Override
    public displayitems.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_view,parent,false);

        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull displayitems.viewholder holder, int position) {

        if(iteminformation.get(position).isInStock()){
            holder.available.setText("Available");
        }else{
            holder.available.setText("Out Of Stock");
        }
        holder.itemprice.setText(iteminformation.get(position).getPrice() + "$");
        holder.itemname.setText(iteminformation.get(position).getItemName());

        Glide.with(context).load(itemimage.get(position).getImageurl()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(holder.iv);

        holder.item_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cv.setVisibility(View.VISIBLE);
                enter.setText(iteminformation.get(position).getItemName());

               yes.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       root.child(key.get(position)).removeValue();
                       cv.setVisibility(View.INVISIBLE);

                   }
               });


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
