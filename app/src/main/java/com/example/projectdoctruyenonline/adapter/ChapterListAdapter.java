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
//        holder.txtIdChapter.setText(chapter.getIdChapter()+"");
        holder.txtIdStory.setText(chapter.getIdStory() + "");
        String s[] = chapter.getTitle().split("-");
        holder.txtTitleChapter.setText(s[1]);
        holder.txtContentChapter.setText("idChapter: " + chapter.getChapter_id());
        int kq = chapter.getIdStory() % 19;
        int id = context.getResources().getIdentifier("com.example.projectdoctruyenonline:drawable/s" + kq, null, null);
        holder.imgChapterList.setImageResource(id);
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class ChapterListAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtIdChapter, txtIdStory, txtTitleChapter, txtContentChapter;
        private ImageView imgChapterList;

        public ChapterListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdChapter = itemView.findViewById(R.id.txtIdChapter);
            txtIdStory = itemView.findViewById(R.id.txtIdStory);
            txtTitleChapter = itemView.findViewById(R.id.txtTitleChapter);
            txtContentChapter = itemView.findViewById(R.id.txtContentChapter);
            imgChapterList = itemView.findViewById(R.id.imgChapterList);
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
