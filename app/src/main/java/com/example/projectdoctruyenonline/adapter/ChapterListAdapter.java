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
import com.example.projectdoctruyenonline.activities.ReadStoryActivity;
import com.example.projectdoctruyenonline.activities.StoryDetailActivity;
import com.example.projectdoctruyenonline.models.Chapter;
import com.example.projectdoctruyenonline.service.ItemClickListener;

import java.util.List;


class ChapterListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
    public TextView txtTitleChapter;
//    public ImageView imgChapterList;

    private ItemClickListener itemClickListener;

    public ChapterListAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTitleChapter = itemView.findViewById(R.id.txtTitleChapter);
//        imgChapterList = itemView.findViewById(R.id.imgChapterList);
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

public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapterViewHolder> {
    Context context;
    List<Chapter> chapterList;

    public ChapterListAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public ChapterListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chapter, parent, false);
        return new ChapterListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterListAdapterViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
//        holder.txtIdChapter.setText(chapter.getIdChapter()+"");
        String s[] = chapter.getTitle().split("-");
        holder.txtTitleChapter.setText(s[1]);
//        holder.txtContentChapter.setText("idChapter: " + chapter.getChapter_id());
//        int kq = chapter.getIdStory() % 19;
//        int id = context.getResources().getIdentifier("com.example.projectdoctruyenonline:drawable/s" + kq, null, null);
//        holder.imgChapterList.setImageResource(id);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Intent intent = new Intent(context, ReadStoryActivity.class);
                    intent.putExtra(Commons.Chapter, chapterList.get(position));  //chapterList.get(position)
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
