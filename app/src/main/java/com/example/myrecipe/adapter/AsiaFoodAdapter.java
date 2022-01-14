package com.example.myrecipe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myrecipe.R;
import com.example.myrecipe.model.AsiaFood;
import com.example.myrecipe.model.PopularFood;

import java.util.List;


public class AsiaFoodAdapter extends RecyclerView.Adapter<AsiaFoodAdapter.AsiaFoodViewHolder> {

    Context context;

    List<AsiaFood> asiaFoodList;



    public AsiaFoodAdapter(Context context, List<AsiaFood> asiaFoodList) {
        this.context = context;
        this.asiaFoodList = asiaFoodList;
    }

    @NonNull
    @Override
    public AsiaFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.asia_food_row_item, parent, false);
        return new AsiaFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder( AsiaFoodViewHolder holder, int position) {

        Glide.with(context).load(asiaFoodList.get(position).getImageUrl()).placeholder(R.drawable.asiafood1).into(holder.foodImage);
        holder.name.setText(asiaFoodList.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return asiaFoodList.size();
    }


    public static final class AsiaFoodViewHolder extends RecyclerView.ViewHolder{


        ImageView foodImage;
        TextView  name;

        public AsiaFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);

            name = itemView.findViewById(R.id.name);




        }
    }

}
