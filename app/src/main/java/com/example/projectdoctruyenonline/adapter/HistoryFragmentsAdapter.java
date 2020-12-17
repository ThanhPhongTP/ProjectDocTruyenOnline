package com.example.projectdoctruyenonline.adapter;

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

import com.example.projectdoctruyenonline.Commons;
import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.activities.StoryDetailActivity;
import com.example.projectdoctruyenonline.models.Story;
import com.example.projectdoctruyenonline.service.ItemClickListener;

import java.util.List;

class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView txtDocGandDay;
    public ImageView imgHistoryStoryW;

    private ItemClickListener itemClickListener;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        txtDocGandDay = itemView.findViewById(R.id.txtDocGandDay);
        imgHistoryStoryW = itemView.findViewById(R.id.imgHistoryStoryW);
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

public class HistoryFragmentsAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<Story> storyArrayList_Watched;

    public HistoryFragmentsAdapter(Context context, List<Story> storyArrayList_Watched) {
        this.context = context;
        this.storyArrayList_Watched = storyArrayList_Watched;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doc_gan_day, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story story = storyArrayList_Watched.get(position);
        holder.txtDocGandDay.setText(story.getName());
        int kq = story.getId() % 19;
        int id = context.getResources().getIdentifier("com.example.projectdoctruyenonline:drawable/s" + kq, null, null);
        holder.imgHistoryStoryW.setImageResource(id);

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
//        Log.d("getItemCountStoryWatched",getItemCountStoryWatched+"");
        return getItemCountStoryWatched;
    }

}