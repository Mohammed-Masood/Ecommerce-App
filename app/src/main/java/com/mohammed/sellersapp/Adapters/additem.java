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
import com.mohammed.sellersapp.Add_new_item;
import com.mohammed.sellersapp.Model.additemmodel;
import com.mohammed.sellersapp.Model.categorymodel;
import com.mohammed.sellersapp.R;

import java.util.ArrayList;

public class additem extends RecyclerView.Adapter<additem.viewholder> {

    ArrayList<additemmodel> model;
    ArrayList<categorymodel> model2;
    Context context;

    public additem(ArrayList<additemmodel> model,ArrayList<categorymodel> model2, Context context) {

        this.model = model;
        this.context = context;
        this.model2 = model2;

    }

    @NonNull
    @Override
    public additem.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.additem_layout,parent,false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull additem.viewholder holder, int position) {
        Glide.with(context).load(model2.get(position).getImageurl()).into(holder.iv);
        holder.tv.setText(model.get(position).getCategoryname());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Add_new_item.class);
                i.putExtra("categoryname",model.get(position).getCategoryname());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
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
