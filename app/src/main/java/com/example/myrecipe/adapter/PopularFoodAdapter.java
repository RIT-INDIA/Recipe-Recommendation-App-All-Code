package com.example.myrecipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.myrecipe.Details;
import com.example.myrecipe.Home;
import com.example.myrecipe.R;
import com.example.myrecipe.model.PopularFood;

import java.util.List;


public class PopularFoodAdapter extends RecyclerView.Adapter<PopularFoodAdapter.PopularFoodViewHolder> {

    Context context;
    List<PopularFood> popularFoodList;

    public PopularFoodAdapter(Context context, List<PopularFood> popularFoodList) {
        this.context = context;
        this.popularFoodList = popularFoodList;
    }

    @NonNull
    @Override
    public PopularFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.popular_food_row_item, parent, false);
        return new PopularFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularFoodViewHolder holder, int position) {

        Glide.with(context).load(popularFoodList.get(position).getImageUrl()).placeholder(R.drawable.ic_baseline_image_24).into(holder.foodImage);
        holder.name.setText(popularFoodList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm=popularFoodList.get(position).getName();
                Bundle b=new Bundle();
                b.putString("name",nm);

                Intent i=new Intent(context, Details.class);
                i.putExtras(b);
                context.startActivity(i);

                Toast.makeText(context,popularFoodList.get(position).getName(),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return popularFoodList.size();
    }


    public static final class PopularFoodViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView  name;

        public PopularFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);
            name = itemView.findViewById(R.id.name);

        }
    }

}
