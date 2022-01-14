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
import com.example.myrecipe.R;
import com.example.myrecipe.model.NorthFood;
import com.example.myrecipe.model.PopularFood;

import java.util.List;

public class NorthIndianFoodAdapter extends RecyclerView.Adapter<NorthIndianFoodAdapter.NorthFoodViewHolder> {
    Context context;
    List<NorthFood> northFoodList;

    public NorthIndianFoodAdapter(Context context,List<NorthFood> northFoodList)
    {
        this.context=context;
        this.northFoodList=northFoodList;
    }

    @NonNull
    @Override
    public NorthFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.north_indian_food, parent, false);
        return new NorthIndianFoodAdapter.NorthFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NorthFoodViewHolder holder, int position) {
        Glide.with(context).load(northFoodList.get(position).getImageUrl()).placeholder(R.drawable.ic_baseline_image_24).into(holder.foodImage);

        holder.name.setText(northFoodList.get(position).getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm=northFoodList.get(position).getName();
                Bundle b=new Bundle();
                b.putString("name",nm);

                Intent i=new Intent(context, Details.class);
                i.putExtras(b);
                context.startActivity(i);

                Toast.makeText(context,northFoodList.get(position).getName(),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return northFoodList.size();
    }

    public static final class NorthFoodViewHolder extends RecyclerView.ViewHolder{


        ImageView foodImage;
        TextView name;

        public NorthFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);

            name = itemView.findViewById(R.id.name);



        }
    }
}



