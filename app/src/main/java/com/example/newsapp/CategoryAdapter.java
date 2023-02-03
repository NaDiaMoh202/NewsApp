package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.HolderView> {
    private ArrayList<CategoryData> categoryRVModels;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryAdapter(ArrayList<CategoryData> categoryRVModels, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModels = categoryRVModels;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item, parent,false);
        return new CategoryAdapter.HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.HolderView holder, int position) {
        CategoryData categoryRVModel = categoryRVModels.get(position);
        holder.categoryTV.setText(categoryRVModel.getCategory());
        Picasso.get().load(categoryRVModel.getCategoryImageUrl()).into(holder.categoryIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModels.size();
    }
    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }

    public class HolderView extends RecyclerView.ViewHolder {
        private TextView categoryTV;
        private ImageView categoryIV;
        public HolderView(@NonNull View itemView) {
            super(itemView);
            categoryTV = itemView.findViewById(R.id.idTVCategory);
            categoryIV = itemView.findViewById(R.id.idIVCategory);
        }
    }
}
