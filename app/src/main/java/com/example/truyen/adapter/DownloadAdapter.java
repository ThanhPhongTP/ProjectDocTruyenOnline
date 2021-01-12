package com.example.truyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyen.R;
import com.example.truyen.activities.StoryOffLine;
import com.example.truyen.models.Story;
import com.example.truyen.service.DatabaseSQLite;
import com.example.truyen.service.ItemClickListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.example.truyen.service.DatabaseSQLite.database;

class DownloadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView tv_Name;
    public ImageView img_Story;

    private ItemClickListener itemClickListener;

    public DownloadViewHolder (View itemView) {
        super(itemView);
        tv_Name = itemView.findViewById(R.id.tv_name_download);
        img_Story = itemView.findViewById(R.id.img_download);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return false;
    }
}


public class DownloadAdapter extends RecyclerView.Adapter<DownloadViewHolder>{
    Context context;
    List<Story> listDownload;

    public DownloadAdapter(Context context, List<Story> listDownload) {
        this.context = context;
        this.listDownload = listDownload;
    }

    @NotNull
    @Override
    public DownloadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_download, parent, false);
        return new DownloadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadViewHolder holder, int position) {

        Story story = listDownload.get(position);
        holder.tv_Name.setText(story.getName());
        Picasso.with(context)
                .load(story.getThumbnail_image())
                .into(holder.img_Story);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick){
                    Intent intent = new Intent(context, StoryOffLine.class);
                    intent.putExtra("NEWSTORY", story.getStoryID());
                    context.startActivity(intent);
                }
                else {
                    listDownload.remove(position);
                    database.deleteStory(story.getStoryID());
//                    notifyDataSetChanged();
                    notifyItemRemoved(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDownload.size();
    }
}
