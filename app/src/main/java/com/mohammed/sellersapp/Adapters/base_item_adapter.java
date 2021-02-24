package com.mohammed.sellersapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mohammed.sellersapp.Model.categorymodel;
import com.mohammed.sellersapp.Model.itemmodel;
import com.mohammed.sellersapp.R;
import com.mohammed.sellersapp.base_descriptive_item;

import java.util.ArrayList;

public class base_item_adapter extends RecyclerView.Adapter<base_item_adapter.viewholder> {

    Context context;
    ArrayList<itemmodel> items;
    ArrayList<categorymodel> images;
    String categoryname;
    ArrayList<String> key;

    public base_item_adapter(Context context, ArrayList<itemmodel> items, ArrayList<categorymodel> images,String categoryname,ArrayList<String> key) {
        this.context = context;
        this.items = items;
        this.images = images;
        this.categoryname = categoryname;
        this.key = key;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.base_item_layout,parent,false);


        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        if(items.get(position).isInStock()){
            holder.availability.setText("Available");
        }else{
            holder.availability.setText("Out Of Stock");
        }
        holder.name.setText(items.get(position).getItemName());
        holder.price.setText(items.get(position).getPrice() + "$");

        Glide.with(context).load(images.get(position).getImageurl()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(holder.iv);

        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, base_descriptive_item.class);
                i.putExtra("Categoryname",categoryname);
                i.putExtra("itemkey",key.get(position));
                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView availability,price,name;
        ImageView iv;
        ConstraintLayout cl;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            availability = itemView.findViewById(R.id.base_availability);
            price = itemView.findViewById(R.id.base_itemprice);
            name = itemView.findViewById(R.id.base_itemname);
            iv = itemView.findViewById(R.id.base_itemimage);
            cl = itemView.findViewById(R.id.base_items_cl);

        }

    }


}
