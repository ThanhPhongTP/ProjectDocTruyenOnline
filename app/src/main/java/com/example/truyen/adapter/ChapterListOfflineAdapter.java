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
import com.example.truyen.activities.ReadStoryActivityOffline;
import com.example.truyen.models.Chapter;
import com.example.truyen.service.ItemClickListener;

import java.util.List;


class ChapterListOfflineAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
    public TextView txtTitleChapter;

    private ItemClickListener itemClickListener;

    public ChapterListOfflineAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTitleChapter = itemView.findViewById(R.id.txtTitleChapter);
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

public class ChapterListOfflineAdapter extends RecyclerView.Adapter<ChapterListOfflineAdapterViewHolder> {
    Context context;
    List<Chapter> chapterList;

    public ChapterListOfflineAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public ChapterListOfflineAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chapter, parent, false);
        return new ChapterListOfflineAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterListOfflineAdapterViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
        String s[] = chapter.getTitle().split("-");
        holder.txtTitleChapter.setText(s[1]);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Intent intent = new Intent(context, ReadStoryActivityOffline.class);
                    intent.putExtra(Commons.Chapter, chapter.getIdStory());  //chapterList.get(position)
                    intent.putExtra("CHAPTER_ID", chapter.getChapter_id());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }


}
