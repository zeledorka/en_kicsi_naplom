package com.example.enkicsinaplom;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.enkicsinaplom.adapter.DiaryAdapter;
import com.example.enkicsinaplom.data.DiaryChapter;
import com.example.enkicsinaplom.data.DiaryDatabase;
import com.example.enkicsinaplom.data.DiaryImages;
import com.example.enkicsinaplom.fragments.NewDiaryChapterDialogFragment;
import com.example.enkicsinaplom.fragments.ZoomDiaryChapterDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NewDiaryChapterDialogFragment.NewDiaryChapterDialogListener, DiaryAdapter.DiaryChapterClickListener{

    private RecyclerView recyclerView;
    private DiaryAdapter adapter;

    public DiaryDatabase database;

    private List<String> images;
    private List<DiaryImages> imgs;

    private Activity ma;
    private static boolean clicks = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ma = this;
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        images = new ArrayList<>();
        images.add("https://i.pinimg.com/originals/55/0b/ca/550bca83b45bf12ea34a45d67f4fb315.jpg");
        images.add("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/womanyellingcat-1573233850.jpg?resize=480:*");
        images.add("https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/hpq7fk7rloryin5g1far.jpg");
        images.add("https://images.immediate.co.uk/production/volatile/sites/4/2018/08/iStock_13967830_XLARGE-90f249d.jpg?quality=90&resize=960%2C408");
        images.add("https://static.designboom.com/wp-content/uploads/2019/03/realistic-cat-heads-designboom-1.jpg");
        images.add("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/why-cats-are-best-pets-1559241235.jpg?crop=0.586xw:0.880xh;0.0684xw,0.0611xh&resize=640:*");
        images.add("https://pbs.twimg.com/profile_images/1080545769034108928/CEzHCTpI_400x400.jpg");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewDiaryChapterDialogFragment(imgs).show(getSupportFragmentManager(), NewDiaryChapterDialogFragment.TAG);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.themeFab);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicks = !clicks;
                Utils.changeToTheme(ma, clicks);
            }
        });

        database = Room.databaseBuilder(
                getApplicationContext(),
                DiaryDatabase.class,
                "diarychapter"
        ).build();

        for (int i = 0; i < images.size(); i++){
            DiaryImages img = new DiaryImages();
            img.url = images.get(i);
            onDiaryImagesCreated(img);
        }

        getAllImages();

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.MainRecyclerView);
        adapter = new DiaryAdapter(this);
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<DiaryChapter>>() {

            @Override
            protected List<DiaryChapter> doInBackground(Void... voids) {
                return database.diaryChapterDao().getAll();
            }

            @Override
            protected void onPostExecute(List<DiaryChapter> diaryChapters) {
                adapter.update(diaryChapters);
            }
        }.execute();
    }

    private void getAllImages() {
        new AsyncTask<List<DiaryImages>, Void, List<DiaryImages>>() {

            @Override
            protected final List<DiaryImages> doInBackground(List<DiaryImages>... lists) {
                return database.diaryImagesDao().getAll();
            }

            @Override
            protected void onPostExecute(final List<DiaryImages> diaryImages) {
                imgs = diaryImages;
            }
        }.execute();
    }

    public void onItemChanged(final DiaryChapter item) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                database.diaryChapterDao().update(item);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("MainActivity", "DiaryChapter update was successful");
            }
        }.execute();
    }

    public void onDiaryChapterCreated(final DiaryChapter newItem) {
        new AsyncTask<Void, Void, DiaryChapter>() {

            @Override
            protected DiaryChapter doInBackground(Void... voids) {
                newItem.id = database.diaryChapterDao().insert(newItem);
                return newItem;
            }

            @Override
            protected void onPostExecute(DiaryChapter diaryChapter) {
                Log.d("MainActivity", "Diary chapter insert was successful");
                adapter.addItem(diaryChapter);
            }
        }.execute();
    }

    public void onDiaryImagesCreated(final DiaryImages newItem) {
        new AsyncTask<Void, Void, DiaryImages>() {

            @Override
            protected DiaryImages doInBackground(Void... voids) {
                newItem.id = database.diaryImagesDao().insert(newItem);
                return newItem;
            }

            @Override
            protected void onPostExecute(DiaryImages diaryImages) {
                Log.d("MainActivity", "Diary image insert was successful");
            }
        }.execute();
    }

    public void onItemRemoved(final DiaryChapter item) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                database.diaryChapterDao().deleteItem(item);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("MainActivity", "Diary chapter removal was successful");
                adapter.RemoveItem(item);
            }
        }.execute();

    }

    @Override
    public void onZoomDiaryChapter(final Long id) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                DiaryChapter diaryChapter = database.diaryChapterDao().getDiaryById(id);
                new ZoomDiaryChapterDialogFragment(diaryChapter.imgurl, diaryChapter.text).show(getSupportFragmentManager(), ZoomDiaryChapterDialogFragment.TAG);
                return null;
            }

            /*@Override
            protected void onPostExecute(Void... voids) {
                Log.d("MainActivity", "Diary chapter zoom was successful");
            }*/
        }.execute();
    }

    public DiaryDatabase getDatabase(){
        return database;
    }
}
