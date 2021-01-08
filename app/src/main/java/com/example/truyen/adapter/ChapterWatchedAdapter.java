package com.example.truyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyen.Commons;
import com.example.truyen.R;
import com.example.truyen.activities.ReadStoryActivity;
import com.example.truyen.models.Chapter;
import com.example.truyen.service.ItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

class ChapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
    public TextView txtChapterWatched;

    private ItemClickListener itemClickListener;

    public ChapterViewHolder(View itemView) {
        super(itemView);
        txtChapterWatched = itemView.findViewById(R.id.txtChapterWatched);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}


public class ChapterWatchedAdapter extends RecyclerView.Adapter<ChapterViewHolder>  {
    Context context;
    List<Chapter> chapterArrayList_Wathched;

    public ChapterWatchedAdapter(Context context, List<Chapter> chapterArrayList_Wathched) {
        this.context = context;
        this.chapterArrayList_Wathched = chapterArrayList_Wathched;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chapter_watched,parent,false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChapterViewHolder holder, int position) {
        Chapter chapter = chapterArrayList_Wathched.get(position);
//        holder.txtChapterWatched.setText((chapter.getTitle()));
        holder.txtChapterWatched.setText(chapter.getTitle()+"");
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Intent intent = new Intent(context, ReadStoryActivity.class);
                    intent.putExtra(Commons.Chapter, chapter.getIdStory());  //chapterList.get(position)
                    intent.putExtra("CHAPTER_ID", chapter.getChapter_id());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterArrayList_Wathched.size();
    }
}

