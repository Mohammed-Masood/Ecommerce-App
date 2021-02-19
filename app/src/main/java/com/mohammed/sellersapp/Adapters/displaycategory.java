package com.mohammed.sellersapp.Adapters;

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
import com.mohammed.sellersapp.Model.additemmodel;
import com.mohammed.sellersapp.Model.categorymodel;
import com.mohammed.sellersapp.Model.itemmodel;
import com.mohammed.sellersapp.R;
import com.mohammed.sellersapp.delete_item2;

import java.util.ArrayList;

public class displaycategory extends RecyclerView.Adapter<displaycategory.viewholder> {

    Context context;
    ArrayList<additemmodel> categoryname;
    ArrayList<categorymodel> categoryimage;


    public displaycategory(Context context, ArrayList<additemmodel> categoryname, ArrayList<categorymodel> categoryimage) {
        this.context = context;
        this.categoryname = categoryname;
        this.categoryimage = categoryimage;
    }

    @NonNull
    @Override
    public displaycategory.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater infalter = LayoutInflater.from(context);
        View v = infalter.inflate(R.layout.additem_layout,parent,false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull displaycategory.viewholder holder, int position) {

        holder.tv.setText(categoryname.get(position).getCategoryname());
        Glide.with(context).load(categoryimage.get(position).getImageurl()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(holder.iv);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, delete_item2.class);
                i.putExtra("catname",categoryname.get(position).getCategoryname());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryname.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        ImageView iv;
        TextView tv;
        CardView cv;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.additem_iv);
            tv = itemView.findViewById(R.id.additem_tv);
            cv = itemView.findViewById(R.id.additem_cv);
        }

    }

}
