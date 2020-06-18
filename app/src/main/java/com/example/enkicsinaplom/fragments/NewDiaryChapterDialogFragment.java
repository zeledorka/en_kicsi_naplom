package com.example.enkicsinaplom.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.enkicsinaplom.R;
import com.example.enkicsinaplom.data.DiaryChapter;
import com.example.enkicsinaplom.data.DiaryImages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewDiaryChapterDialogFragment extends DialogFragment {

    private EditText titleEditText;
    private Spinner imageSpinner;
    private EditText textEditText;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public static final String TAG = "NewDiaryChapterDialogFragment";

    public interface NewDiaryChapterDialogListener {
        void onDiaryChapterCreated(DiaryChapter newItem);
    }

    private NewDiaryChapterDialogListener listener;
    private List<DiaryImages> images;

    public NewDiaryChapterDialogFragment(List<DiaryImages> images) {
        this.images = images;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof NewDiaryChapterDialogListener) {
            listener = (NewDiaryChapterDialogListener) activity;
        } else {
            throw new RuntimeException("Activity must implement the NewDiaryChapterDialogListener interface!");
        }
    }

    public FragmentManager getFragmentManager(FragmentActivity activity) {
        return activity.getSupportFragmentManager();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.new_shopping_item)
                .setView(getContentView())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isValid()) {
                            listener.onDiaryChapterCreated(getDiaryChapter());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private boolean isValid() {
        return titleEditText.getText().length() > 0 || textEditText.getText().length() > 0;
    }

    private DiaryChapter getDiaryChapter() {
        DiaryChapter diaryChapter = new DiaryChapter();
        diaryChapter.title = titleEditText.getText().toString();
        diaryChapter.text = textEditText.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis() + 3600000);
        String str = formatter.format(date);
        diaryChapter.date = str;
        diaryChapter.imgurl = images.get(imageSpinner.getSelectedItemPosition()).url;
        return diaryChapter;
    }

    private View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_diary_chapter, null);
        titleEditText = contentView.findViewById(R.id.DiaryChapterTitleEditText);
        textEditText = contentView.findViewById(R.id.DiaryTextEditText);
        imageSpinner = contentView.findViewById(R.id.DiaryChapterImageUrl);
        imageSpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.images_items)));
        return contentView;
    }
}
