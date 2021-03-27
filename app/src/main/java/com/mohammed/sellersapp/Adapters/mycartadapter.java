package com.mohammed.sellersapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.sellersapp.Model.OrderModel;
import com.mohammed.sellersapp.Model.categorymodel;
import com.mohammed.sellersapp.Model.itemmodel;
import com.mohammed.sellersapp.R;

import java.util.ArrayList;

public class mycartadapter extends RecyclerView.Adapter<mycartadapter.viewholder> {

    ArrayList<OrderModel> orders;
    Context context;
    DatabaseReference newroot;

    public mycartadapter(ArrayList<OrderModel> orders, Context context) {
        this.orders = orders;
        this.context = context;

        newroot = FirebaseDatabase.getInstance().getReference("Accounts").child("Normal Users");

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.my_cart_rv_items,parent,false);


        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {




        holder.totalprice.setText(String.valueOf(orders.get(position).getTotalPrice()) + "$");
        holder.amount.setText(String.valueOf(orders.get(position).getAmountRequired()));
        DatabaseReference root = FirebaseDatabase.getInstance().getReference("Category").child(orders.get(position).getCategoryname()).child("ItemFiles").child(orders.get(position).getItemKey());

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemmodel item = snapshot.getValue(itemmodel.class);
                holder.itemname.setText(item.getItemName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference newref = FirebaseDatabase.getInstance().getReference("Category").child(orders.get(position).getCategoryname()).child("ItemFiles").child(orders.get(position).getItemKey()).child("Uri");

        newref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                categorymodel imageurl = snapshot.getValue(categorymodel.class);
                Glide.with(context).load(imageurl.getImageurl()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontTransform().into(holder.iv);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference newnewroot = newroot.child(orders.get(position).getUserid()).child("Cart").child(orders.get(position).getOrderKey());
                newnewroot.removeValue();


            }
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView amount,itemname,totalprice;
        ImageView iv,delete;

        public viewholder(@NonNull View itemView) {

            super(itemView);
            delete = itemView.findViewById(R.id.delete_mycartitem);
            amount = itemView.findViewById(R.id.mycart_itemamount);
            itemname = itemView.findViewById(R.id.mycart_itemname);
            totalprice = itemView.findViewById(R.id.mycart_pricetag);
            iv = itemView.findViewById(R.id.mycart_itemiv);



        }

    }

}
