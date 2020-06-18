package com.example.enkicsinaplom.adapter;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enkicsinaplom.R;
import com.example.enkicsinaplom.data.DiaryChapter;
import com.example.enkicsinaplom.fragments.ZoomDiaryChapterDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private final List<DiaryChapter> items;

    private DiaryChapterClickListener listener;

    private DiaryAdapter adapter;

    public DiaryAdapter(DiaryChapterClickListener listener) {
        this.listener = listener;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.diary_chapter_list, parent, false);
        return new DiaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        DiaryChapter item = items.get(position);
        holder.titleTextView.setText(item.title);
        holder.dateTextView.setText(item.date);
        holder.iconImageView.setImageResource(R.drawable.book);
        holder.zoomButton.setImageResource(R.drawable.zoom);
        holder.removeButton.setImageResource(R.drawable.bin);

        holder.item = item;
    }

    public void addItem(DiaryChapter item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void RemoveItem(DiaryChapter item) {
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).equals(item))
            {
                notifyItemRemoved(i);
            }
        }
    }

    public void update(List<DiaryChapter> diaryChapters) {
        items.clear();
        items.addAll(diaryChapters);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface DiaryChapterClickListener{
        void onItemChanged(DiaryChapter item);
        void onItemRemoved(DiaryChapter item);
        void onZoomDiaryChapter(final Long id);
    }

    class DiaryViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImageView;
        TextView titleTextView;
        TextView dateTextView;
        ImageButton zoomButton;
        ImageButton removeButton;

        DiaryChapter item;

        DiaryViewHolder(View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.DiaryChapterIconImageView);
            titleTextView = itemView.findViewById(R.id.DiaryChapterTitleTextView);
            dateTextView = itemView.findViewById(R.id.DiaryChapterDateTextView);
            zoomButton = itemView.findViewById(R.id.DiaryChapterZoomButton);
            removeButton = itemView.findViewById(R.id.DiaryChapterRemoveButton);

            removeButton.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                if (item != null) {
                    items.remove(item);
                    listener.onItemRemoved(item);
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), items.size());
                }
                }
            });

            zoomButton.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onZoomDiaryChapter(item.id);
                }
            });
        }
    }
}
