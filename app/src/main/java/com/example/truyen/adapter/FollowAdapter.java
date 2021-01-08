package com.example.truyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyen.R;
import com.example.truyen.activities.StoryDetailActivity;
import com.example.truyen.models.Story;
import com.example.truyen.service.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView tvNameFollow;
    public ImageView imgFL;

    private ItemClickListener itemClickListener;

    public ViewHolder1(@NonNull View itemView) {
        super(itemView);
        tvNameFollow = itemView.findViewById(R.id.tv_name_follow);
        imgFL = itemView.findViewById(R.id.img_follow);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, StoryDetailActivity.class);
//                Story keyPutIntent = storyArrayList_Watched.get(getPosition());
//                intent.putExtra(Commons.KEY_STORY,keyPutIntent);
//                context.startActivity(intent);
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

public class FollowAdapter extends RecyclerView.Adapter<ViewHolder1> {
    Context context;
    List<Story> storyArrayList_Watched;

    public FollowAdapter(Context context, List<Story> storyArrayList_Watched) {
        this.context = context;
        this.storyArrayList_Watched = storyArrayList_Watched;
    }

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follow, parent, false);
        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
        Story story = storyArrayList_Watched.get(position);
        holder.tvNameFollow.setText(story.getName());
        Picasso.with(context)
                .load(story.getThumbnail_image())
                .into(holder.imgFL);


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Intent intent = new Intent(context, StoryDetailActivity.class);
                    intent.putExtra("NEWSTORY", story.getId());
                    Log.d("NEWSTORY1", story.getId() + "");
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int getItemCountStoryWatched = storyArrayList_Watched.size();
        return getItemCountStoryWatched;
    }

}