package com.example.enkicsinaplom.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.lang.annotation.Target;
import java.util.List;

@Dao
public interface DiaryChapterDao {
    @Query("SELECT * FROM diarychapter")
    List<DiaryChapter> getAll();

    @Insert
    long insert(DiaryChapter diaryChapters);

    @Update
    void update(DiaryChapter diaryChapter);

    @Delete
    void deleteItem(DiaryChapter diaryChapter);

    @Query("SELECT * FROM diarychapter WHERE id = :identity")
    DiaryChapter getDiaryById(Long identity);
}
