package com.example.truyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyen.Commons;
import com.example.truyen.R;
import com.example.truyen.activities.StoryListActivity;
import com.example.truyen.models.Categories;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter1 extends RecyclerView.Adapter<CategoriesAdapter1.ViewHolderCategories> {
    Context context;
    List<Categories> categoriesList;

    public CategoriesAdapter1(Context context, List<Categories> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public ViewHolderCategories onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_the_loai,parent,false);

        return new ViewHolderCategories(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategories holder, int position) {
        Categories categories = categoriesList.get(position);
//        holder.tvNameCategories.setText(categories.getName().substring(6,categories.getName().length()));
        holder.tvNameCategories.setText(categories.getName());
        holder.img_Categories.setImageResource(categories.getImg_Categories());
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public void setFilter(ArrayList<Categories> newList) {
        categoriesList = new ArrayList<>();
        categoriesList.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolderCategories extends  RecyclerView.ViewHolder{
        public TextView tvNameCategories;
        public ImageView img_Categories;
        public ViewHolderCategories(@NonNull View itemView) {
            super(itemView);
            tvNameCategories = itemView.findViewById(R.id.tv_the_loai);
            img_Categories = itemView.findViewById(R.id.img_the_loai);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, StoryListActivity.class);
                    intent.putExtra(Commons.Categories,categoriesList.get(getPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }
    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_loadmoreCategory);
        }
    }
}
