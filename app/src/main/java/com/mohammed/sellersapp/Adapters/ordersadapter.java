package com.mohammed.sellersapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammed.sellersapp.Model.Usermodel;
import com.mohammed.sellersapp.Model.orderdetailsmodel;
import com.mohammed.sellersapp.R;
import com.mohammed.sellersapp.order_items;

import java.util.ArrayList;

public class ordersadapter extends RecyclerView.Adapter<ordersadapter.viewholder> {

    ArrayList<String> key;
    ArrayList<String> Location;
    ArrayList<String> orderkey;
    ArrayList<String> time;

    Context context;
    DatabaseReference root;

    public ordersadapter(ArrayList<String> key, ArrayList<String> location, Context context,ArrayList<String> orderkey,ArrayList<String> time) {
        this.key = key;
        Location = location;
        this.context = context;
        this.orderkey = orderkey;
        this.time = time;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.admin_orders_rv,parent,false);

        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        root = FirebaseDatabase.getInstance().getReference("Accounts").child("Normal Users").child(key.get(position));

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usermodel model = snapshot.getValue(Usermodel.class);

                holder.number.setText(model.getPhonenumber());
                holder.location.setText(Location.get(position));
                holder.name.setText(model.getfullname());
                holder.time.setText(time.get(position));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            DatabaseReference newroot = FirebaseDatabase.getInstance().getReference("Order").child(orderkey.get(position));
            newroot.removeValue();
            }
        });

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, order_items.class);

                i.putExtra("Orderkey",orderkey.get(position));

                context.startActivity(i);


            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String parseuri = "tel:" +holder.number.getText().toString();
                intent.setData(Uri.parse(parseuri));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return key.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView name,location,number,time;
        ImageView iv,call;
        CardView cv;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.admin_orders_fullname);
            location = itemView.findViewById(R.id.admin_orders_location);
            number = itemView.findViewById(R.id.admin_orders_phonenumber);
            iv = itemView.findViewById(R.id.admin_orders_delete);
            cv = itemView.findViewById(R.id.admin_orders_cv);
            call = itemView.findViewById(R.id.admin_orders_call);
            time = itemView.findViewById(R.id.admin_orders_time);

        }
    }
}
