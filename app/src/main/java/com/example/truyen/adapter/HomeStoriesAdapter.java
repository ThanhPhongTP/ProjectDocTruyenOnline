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
import com.example.truyen.service.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
    public TextView txtNewStory, txtID;
    public ImageView imgNewStory;
    private ItemClickListener itemClickListener;

    public MyViewHolder(View itemView) {
        super(itemView);
//            txtID = itemView.findViewById(R.id.idNewStory);
        txtNewStory = itemView.findViewById(R.id.txtNewStory);
        imgNewStory = itemView.findViewById(R.id.imgNewStory);

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

public class HomeStoriesAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    List<Ratting> hotStory;

    public HomeStoriesAdapter(Context context, List<Ratting> hotStory) {
        this.context = context;
        this.hotStory = hotStory;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_newstory, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ratting ratting = hotStory.get(position);
//        holder.txtID.setText(ratting.getStory_id());
        holder.txtNewStory.setText(ratting.getStory_title());
        Picasso.with(context)
                .load(ratting.getThumbnail_image())
                .into(holder.imgNewStory);

//        holder.imgNewStory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, StoryDetailActivity.class);
//                intent.putExtra("NEWSTORY", ratting.getStory_id());
//                Log.d("NEWSTORY1", ratting.getStory_id()+"");
//                context.startActivity(intent);
//            }
//        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick){
                    Intent intent = new Intent(context, StoryDetailActivity.class);
                    intent.putExtra("NEWSTORY", ratting.getStory_id());
                    context.startActivity(intent);
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotStory.size();
    }


}
