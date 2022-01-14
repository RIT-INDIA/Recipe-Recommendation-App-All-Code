package com.example.myrecipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myrecipe.Details;
import com.example.myrecipe.R;
import com.example.myrecipe.model.SearchModel;

import java.util.ArrayList;
import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<com.example.myrecipe.adapter.SearchAdapter.ViewHolder> implements Filterable {

    public static List<SearchModel> searchList;
    public static List<SearchModel> searchListTemp;
    private Context context;
    public com.example.myrecipe.adapter.SearchAdapter.recipeDataFilter recipeDataFilter;

    public SearchAdapter(Context context, List<SearchModel> listSearch) {
        this.context = context;
        this.searchList = listSearch;
        this.searchListTemp = listSearch;
    }

    @Override
    public Filter getFilter() {
        if (recipeDataFilter == null) {
            recipeDataFilter = new com.example.myrecipe.adapter.SearchAdapter.recipeDataFilter(searchListTemp, this);
        }
        return recipeDataFilter;
    }

    private class recipeDataFilter extends Filter{

        com.example.myrecipe.adapter.SearchAdapter adapter;
        public List<SearchModel> recipeListTemp;

        public recipeDataFilter(List<SearchModel> ingredientsListTemp, com.example.myrecipe.adapter.SearchAdapter adapter)
        {
            this.adapter = adapter;
            this.recipeListTemp = ingredientsListTemp;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                List<SearchModel> arrayList1 = new ArrayList<SearchModel>();

                for (int k = 0;k<searchListTemp.size();  k++) {
                    if (searchListTemp.get(k).getName().toString().toLowerCase().contains(charSequence)) {
                        arrayList1.add(searchListTemp.get(k));
                    }
                }
                filterResults.count = arrayList1.size();
                filterResults.values = arrayList1;
            } else {
                synchronized (this) {
                    filterResults.values = searchListTemp;
                    filterResults.count = searchListTemp.size();
                }
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            searchList = (List<SearchModel>)results.values;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new SearchAdapter.ViewHolder(itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView search_text_view;
        public ImageView search_image_view;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            search_image_view = itemView.findViewById(R.id.search_image);
            search_text_view = itemView.findViewById(R.id.search_recipe);
            relativeLayout = itemView.findViewById(R.id.search_layout);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {

        if(searchList != null){
            Glide.with(context).load(searchList.get(position).getImage()).placeholder(R.drawable.vegetable).into(holder.search_image_view);
            holder.search_text_view.setText(searchList.get(position).getName());

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Checked : " + searchList.get(position).getName(), Toast.LENGTH_LONG).show();
                    String nm=searchList.get(position).getName();
                    Bundle b=new Bundle();
                    b.putString("name",nm);

                    Intent intent = new Intent(context, Details.class);
                    intent.putExtras(b);
                    context.startActivity(intent);

                }
            });
        }

    }

    @Override
    public int getItemCount() {

        return (searchList == null) ? 0 : searchList.size();
    }

}
