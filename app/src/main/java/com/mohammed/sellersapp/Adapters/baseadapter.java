package com.mohammed.sellersapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mohammed.sellersapp.Model.additemmodel;
import com.mohammed.sellersapp.Model.categorymodel;
import com.mohammed.sellersapp.R;
import com.mohammed.sellersapp.base_items;

import java.util.ArrayList;

public class baseadapter extends RecyclerView.Adapter<baseadapter.viewholder> {

    Context context;
    ArrayList<categorymodel> category_images;
    ArrayList<additemmodel>  category_name;


    public baseadapter(Context context, ArrayList<categorymodel> category_images, ArrayList<additemmodel> category_name) {
        this.context = context;
        this.category_images = category_images;
        this.category_name = category_name;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.baserv,parent,false);


        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.tv.setText(category_name.get(position).getCategoryname());
        Glide.with(context).load(category_images.get(position).getImageurl()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(holder.iv);
        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, base_items.class);
                i.putExtra("Categoryname",category_name.get(position).getCategoryname());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return category_name.size();
    }




    public class viewholder extends RecyclerView.ViewHolder{

        ImageView iv;
        TextView tv;
        ConstraintLayout cl;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.base_cat_image);
            tv = itemView.findViewById(R.id.base_cat_name);
            cl = itemView.findViewById(R.id.constlay);

        }

    }
}
