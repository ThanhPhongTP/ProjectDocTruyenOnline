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

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolderCategories> {
    Context context;
    List<Categories> categoriesList;

    public CategoriesAdapter(Context context, List<Categories> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public ViewHolderCategories onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_caterories,parent,false);

        return new ViewHolderCategories(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategories holder, int position) {
        Categories categories = categoriesList.get(position);
//        holder.txtIdCategories.setText(categories.getId()+"");
        holder.txtNameCategories.setText(categories.getName().substring(6,categories.getName().length()));
//        holder.tvTotal.setText(categories.getTotal() + " truyện");
        Log.d("TTTT", categories.getTotal() + "");
//        if (position %2 ==0){
//            holder.imgCategories.setImageDrawable(context.getResources().getDrawable(R.drawable.s1));
//        }else {
//            holder.imgCategories.setImageDrawable(context.getResources().getDrawable(R.drawable.s2));
//
//        }
//
//        int kq = categories.getId() %19;
//        int id = context.getResources().getIdentifier("com.example.projectdoctruyenonline:drawable/s" + kq, null, null);
//        holder.imgCategories.setImageResource(id);
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
        public TextView txtIdCategories,txtNameCategories,txtUrlCategories, tvTotal;
        public ImageView imgCategories;
        public ViewHolderCategories(@NonNull View itemView) {
            super(itemView);
//            tvTotal = itemView.findViewById(R.id.tvTotal_Story);
//            txtIdCategories = itemView.findViewById(R.id.txtIdCategories);
            txtNameCategories = itemView.findViewById(R.id.txtNameCategories);
//            txtUrlCategories = itemView.findViewById(R.id.txtUrlCategories);
//            imgCategories = itemView.findViewById(R.id.imgCategories);
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
