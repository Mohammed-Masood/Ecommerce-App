package com.mohammed.sellersapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.sellersapp.Model.fbordermodel;
import com.mohammed.sellersapp.Model.itemmodel;
import com.mohammed.sellersapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class orderitemsadapter extends RecyclerView.Adapter<orderitemsadapter.viewholder> {


    ArrayList<fbordermodel> items;
    Context context;

    public orderitemsadapter(ArrayList<fbordermodel> items, Context context) {
        this.items = items;
        this.context = context;

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.order_items_view,parent,false);

        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.amount.setText(items.get(position).getAmountRequired() +"");
        holder.price.setText(items.get(position).getTotalPrice() + "$");

        DatabaseReference root = FirebaseDatabase.getInstance().getReference("Category").child(items.get(position).getCategoryname()).child("ItemFiles").child(items.get(position).getItemKey());

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                itemmodel model = snapshot.getValue(itemmodel.class);
                holder.name.setText(model.getItemName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView name,amount,price;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.order_items_name);
            amount = itemView.findViewById(R.id.order_items_amount);
            price = itemView.findViewById(R.id.order_items_price);



        }


    }
}
