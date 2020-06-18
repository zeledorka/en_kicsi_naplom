package com.example.enkicsinaplom.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {DiaryChapter.class, DiaryImages.class},
        version = 1
)
public abstract class DiaryDatabase extends RoomDatabase {
    public abstract DiaryChapterDao diaryChapterDao();
    public abstract DiaryImagesDao diaryImagesDao();
}
