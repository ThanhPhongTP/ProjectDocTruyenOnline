package com.example.projectdoctruyenonline.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.projectdoctruyenonline.models.Chapter;

import java.util.List;

public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.ChapterListAdapterViewHolder> {
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
        String s[] = chapter.getTitle().split("-");
        holder.txtTitleChapter.setText(s[1]);

    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class ChapterListAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitleChapter;

        public ChapterListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitleChapter = itemView.findViewById(R.id.txtTitleChapter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int poss = getAdapterPosition();
                    Intent intent = new Intent(context, ReadStoryActivity.class);
                    intent.putExtra(Commons.Chapter, chapterList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
