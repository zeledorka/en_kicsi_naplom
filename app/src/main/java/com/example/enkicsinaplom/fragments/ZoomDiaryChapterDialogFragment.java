package com.example.enkicsinaplom.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.enkicsinaplom.R;
import com.example.enkicsinaplom.data.DiaryChapter;
import com.squareup.picasso.Picasso;

public class ZoomDiaryChapterDialogFragment extends DialogFragment {

    public static final String TAG = "ZoomDiaryChapterDialogFragment";

    private ImageView imgageImageView;
    private TextView textTextView;
    private String url;
    private String text;

    public ZoomDiaryChapterDialogFragment(String url, String text){
         this.url = url;
         this.text = text;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.zoom_chapter)
                .setView(getContentView())
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_zoom_diary_chapter, null);
        imgageImageView = contentView.findViewById(R.id.DiaryChapterImageImageView);
        Picasso.with(requireContext()).load(url).into(imgageImageView);
        textTextView = contentView.findViewById(R.id.DiaryChapterTextTextView);
        textTextView.setText(text);
        return contentView;
    }
}
