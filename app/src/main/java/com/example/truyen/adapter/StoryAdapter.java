package com.example.truyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyen.R;
import com.example.truyen.activities.StoryDetailActivity;
import com.example.truyen.models.Ratting;
import com.example.truyen.models.Story;
import com.example.truyen.service.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class StoryByCategoriesIdAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView txtNameStoryByCategoriesId, txtTotalChapter, tvAuthor, tvDate;
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
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }
}

class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView txtNewStory;
    public ImageView imgNewStory;
    private ItemClickListener itemClickListener;

    public HomeViewHolder(View itemView) {
        super(itemView);
        txtNewStory = itemView.findViewById(R.id.txtNewStory);
        imgNewStory = itemView.findViewById(R.id.imgNewStory);

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
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }
}


public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Story> storyList;
    List<Story> hotStory;

    public StoryAdapter(Context context, List<Story> storyList) {
        this.context = context;
        this.storyList = storyList;
//        this.storyList.addAll(storyList);
//        this.storyList = new ArrayList<Story>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_storybycategories, parent, false);
            return new StoryByCategoriesIdAdapterViewHolder(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.item_newstory, parent, false);
            return new HomeViewHolder(view);
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final int itemType = getItemViewType(position);
        if (itemType == 0) {
            StoryByCategoriesIdAdapterViewHolder viewHolder = (StoryByCategoriesIdAdapterViewHolder) holder;
            Story story = storyList.get(position);
            viewHolder.txtNameStoryByCategoriesId.setText(story.getName());
            viewHolder.txtTotalChapter.setText(story.getTotalChapter() + " Chương");
            viewHolder.tvAuthor.setText(story.getAuthor());
            viewHolder.tvDate.setText(story.getDate().substring(0, 10));
            Picasso.with(context)
                    .load(story.getThumbnail_image())
                    .into(viewHolder.imgStory);

            viewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (!isLongClick) {
                        Intent intent = new Intent(context, StoryDetailActivity.class);
                        intent.putExtra("NEWSTORY", story.getId());
                        context.startActivity(intent);
                    }
                }
            });
        } else  if (itemType == 1) {
            HomeViewHolder viewHolder = (HomeViewHolder) holder;
            Story story = storyList.get(position);
            viewHolder.txtNewStory.setText(story.getName());
            Picasso.with(context)
                    .load(story.getThumbnail_image())
                    .into(viewHolder.imgNewStory);
            viewHolder.setItemClickListener(new ItemClickListener() {
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


    }

    @Override
    public int getItemViewType(int position) {
        if (storyList.get(position).getViewType() == 0) {
            return 0;
        } else {
            return 1;
        }
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
