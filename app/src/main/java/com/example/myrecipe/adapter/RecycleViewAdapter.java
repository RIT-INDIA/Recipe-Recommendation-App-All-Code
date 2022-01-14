package com.example.myrecipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chaquo.python.PyObject;
import com.example.myrecipe.Details;
import com.example.myrecipe.R;
import com.example.myrecipe.ShowRecepieActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter  extends RecyclerView.Adapter<RecycleViewAdapter.MyviewHolder> {

    List<PyObject> pyObjectList;
    Context context1;

    public RecycleViewAdapter(Context context, List<PyObject> pyObjectList1) {
        pyObjectList =pyObjectList1;
        context1 = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_layout,parent,false);
        MyviewHolder myviewHolder = new MyviewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyviewHolder holder, int position) {

        holder.textView.setText(pyObjectList.get(position).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemstr = pyObjectList.get(position).toString();
                Bundle bundle = new Bundle();
                bundle.putString("name", itemstr);
                Intent intent = new Intent(context1, Details.class);
                intent.putExtras(bundle);
                context1.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pyObjectList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CardView cardView;
        ImageView imageView;

        public MyviewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.recepiename);
            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.recepieimage);

        }

    }
}
