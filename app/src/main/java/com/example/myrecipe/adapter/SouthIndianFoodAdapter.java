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
import com.example.myrecipe.model.SouthFood;


import java.util.List;

public class SouthIndianFoodAdapter extends RecyclerView.Adapter<SouthIndianFoodAdapter.SouthFoodViewHolder> {
    Context context;
    List<SouthFood> southFoodList;

    public SouthIndianFoodAdapter(Context context,List<SouthFood> southFoodList)
    {
        this.context=context;
        this.southFoodList=southFoodList;
    }

    @NonNull
    @Override
    public SouthFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.south_indian_food, parent, false);
        return new SouthFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SouthFoodViewHolder holder, int position) {
        Glide.with(context).load(southFoodList.get(position).getImageUrl()).placeholder(R.drawable.ic_baseline_image_24).into(holder.foodImage);

        holder.name.setText(southFoodList.get(position).getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm=southFoodList.get(position).getName();
                Bundle b=new Bundle();
                b.putString("name",nm);

                Intent i=new Intent(context, Details.class);
                i.putExtras(b);
                context.startActivity(i);

                Toast.makeText(context,southFoodList.get(position).getName(),Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return southFoodList.size();
    }


    public static final class SouthFoodViewHolder extends RecyclerView.ViewHolder{


        ImageView foodImage;
        TextView name;

        public SouthFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);

            name = itemView.findViewById(R.id.name);



        }
    }
}
