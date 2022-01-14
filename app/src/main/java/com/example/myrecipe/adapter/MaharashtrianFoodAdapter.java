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
import com.example.myrecipe.model.MaharashtraFood;

import java.util.List;


public class MaharashtrianFoodAdapter extends RecyclerView.Adapter<MaharashtrianFoodAdapter.MaharashtraFoodViewHolder> {

    Context context;

    List<MaharashtraFood> maharashtraFoodList;



    public MaharashtrianFoodAdapter(Context context, List<MaharashtraFood> maharashtraFoodList) {
        this.context = context;
        this.maharashtraFoodList = maharashtraFoodList;
    }

    @NonNull
    @Override
    public MaharashtraFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.asia_food_row_item, parent, false);
        return new MaharashtraFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MaharashtraFoodViewHolder holder, int position) {

        Glide.with(context).load(maharashtraFoodList.get(position).getImageUrl()).placeholder(R.drawable.ic_baseline_image_24).into(holder.foodImage);
        holder.name.setText(maharashtraFoodList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm=maharashtraFoodList.get(position).getName();
                Bundle b=new Bundle();
                b.putString("name",nm);

                Intent i=new Intent(context, Details.class);
                i.putExtras(b);
                context.startActivity(i);

                Toast.makeText(context,maharashtraFoodList.get(position).getName(),Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return maharashtraFoodList.size();
    }


    public static final class MaharashtraFoodViewHolder extends RecyclerView.ViewHolder{


        ImageView foodImage;
        TextView  name;

        public MaharashtraFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);

            name = itemView.findViewById(R.id.name);




        }
    }

}
