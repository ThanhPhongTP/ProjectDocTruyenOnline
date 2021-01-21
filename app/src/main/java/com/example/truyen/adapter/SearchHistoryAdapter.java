package com.example.truyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyen.R;
import com.example.truyen.models.SearchHistory;
import com.example.truyen.models.Story;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolderSearch>{
    Context context;
    List<SearchHistory> list_history;

    public SearchHistoryAdapter(Context context, List<SearchHistory> list_history) {
        this.context = context;
        this.list_history = list_history;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderSearch onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chapter, parent,false);

        return new ViewHolderSearch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderSearch holder, int position) {
        SearchHistory searchHistory = list_history.get(position);
        holder.txtSearch.setText(searchHistory.getsSearch());
    }


    @Override
    public int getItemCount() {
        return list_history.size();
    }

    public class ViewHolderSearch extends RecyclerView.ViewHolder {
        public TextView txtSearch;

        public ViewHolderSearch(View view) {
            super(view);
            txtSearch = view.findViewById(R.id.txtTitleChapter);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void setFilter(ArrayList<SearchHistory> newList) {
        list_history = new ArrayList<>();
        list_history.addAll(newList);
        notifyDataSetChanged();
    }
}
