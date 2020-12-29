package com.example.projectdoctruyenonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdoctruyenonline.Commons;
import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.activities.StoryDetailActivity;
import com.example.projectdoctruyenonline.models.Story;
import com.example.projectdoctruyenonline.service.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class StoryByCategoriesIdAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView txtStoryByCategoriesId, txtNameStoryByCategoriesId, txtTotalChapter, tvAuthor, tvDate;
    public ImageView imgStory;

    private ItemClickListener itemClickListener;

    public StoryByCategoriesIdAdapterViewHolder(View itemView) {
        super(itemView);
        txtNameStoryByCategoriesId = itemView.findViewById(R.id.txtNameStoryByCategoriesId);
        txtTotalChapter = itemView.findViewById(R.id.txtTotalChapter);
        imgStory = itemView.findViewById(R.id.imgChapterList);
        tvAuthor = itemView.findViewById(R.id.tvAuthor);
        tvDate = itemView.findViewById(R.id.tvDate);

//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, StoryDetailActivity.class);
//                intent.putExtra(Commons.Story, storyList.get(getPosition()));
//                context.startActivity(intent);
//
//                Log.d("Test1", String.valueOf(storyList.get(getPosition())));
//
//            }
//        });

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}


public class StoryAdapter extends RecyclerView.Adapter<StoryByCategoriesIdAdapterViewHolder> {
    Context context;
    List<Story> storyList;
    List<Story> userList;

    public StoryAdapter(Context context, List<Story> storyList) {
        this.context = context;
        this.storyList = storyList;
//        this.storyList.addAll(storyList);
//        this.storyList = new ArrayList<Story>();
    }

    @NonNull
    @Override
    public StoryByCategoriesIdAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_storybycategories, parent, false);
        return new StoryByCategoriesIdAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryByCategoriesIdAdapterViewHolder holder, int position) {
        Story story = storyList.get(position);
        holder.txtNameStoryByCategoriesId.setText(story.getName());
        holder.txtTotalChapter.setText(story.getTotalChapter() + " Chương");
        holder.tvAuthor.setText(story.getAuthor());
        holder.tvDate.setText(story.getDate().substring(0,10));
//        int kq = story.getId() % 19;
//        int id = context.getResources().getIdentifier("com.example.projectdoctruyenonline:drawable/s" + kq, null, null);
//        holder.imgStory.setImageResource(id);

        Picasso.with(context)
                .load(story.getThumbnail_image())
                .into(holder.imgStory);


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick){
                    Intent intent = new Intent(context, StoryDetailActivity.class);
                    intent.putExtra("NEWSTORY", story.getId());
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
//        int sizeStoryList   =  storyList.size();
//        Log.d("sizeStoryList",sizeStoryList+"");

        return storyList.size();
    }


    public void setFilter(ArrayList<Story> newList) {
        storyList = new ArrayList<>();
        storyList.addAll(newList);
        notifyDataSetChanged();
    }
}
