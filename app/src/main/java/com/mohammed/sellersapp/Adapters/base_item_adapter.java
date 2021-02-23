package com.mohammed.sellersapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mohammed.sellersapp.Model.categorymodel;
import com.mohammed.sellersapp.Model.itemmodel;
import com.mohammed.sellersapp.R;

import java.util.ArrayList;

public class base_item_adapter extends RecyclerView.Adapter<base_item_adapter.viewholder> {

    Context context;
    ArrayList<itemmodel> items;
    ArrayList<categorymodel> images;

    public base_item_adapter(Context context, ArrayList<itemmodel> items, ArrayList<categorymodel> images) {
        this.context = context;
        this.items = items;
        this.images = images;

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
        holder.price.setText(items.get(position).getPrice());

        Glide.with(context).load(images.get(position).getImageurl()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(holder.iv);




    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView availability,price,name;
        ImageView iv;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            availability = itemView.findViewById(R.id.base_availability);
            price = itemView.findViewById(R.id.base_itemprice);
            name = itemView.findViewById(R.id.base_itemname);
            iv = itemView.findViewById(R.id.base_itemimage);

        }

    }


}
