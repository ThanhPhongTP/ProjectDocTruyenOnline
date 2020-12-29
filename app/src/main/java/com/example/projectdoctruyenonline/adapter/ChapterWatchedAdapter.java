package com.example.projectdoctruyenonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdoctruyenonline.Commons;
import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.activities.ReadStoryActivity;
import com.example.projectdoctruyenonline.models.Chapter;

import java.util.List;

public class ChapterWatchedAdapter extends RecyclerView.Adapter<ChapterWatchedAdapter.ViewHolder>  {
    Context context;
    List<Chapter> chapterArrayList_Wathched;

    public ChapterWatchedAdapter(Context context, List<Chapter> chapterArrayList_Wathched) {
        this.context = context;
        this.chapterArrayList_Wathched = chapterArrayList_Wathched;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter_watched,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chapter chapter = chapterArrayList_Wathched.get(position);
//        holder.txtChapterWatched.setText((chapter.getTitle()));
        holder.txtChapterWatched.setText(chapter.getTitle()+"");


    }
    @Override
    public int getItemCount() {
        return chapterArrayList_Wathched.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtChapterWatched;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtChapterWatched = itemView.findViewById(R.id.txtChapterWatched);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, ReadStoryActivity.class);
                    intent.putExtra(Commons.Chapter,chapterArrayList_Wathched.get(position));
                    context.startActivity(intent);
                }
            });
        }
    }
}

