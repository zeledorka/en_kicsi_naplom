package com.example.enkicsinaplom.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DiaryImagesDao {
    @Query("SELECT * FROM diaryimages")
    List<DiaryImages> getAll();

    @Insert
    long insert(DiaryImages diaryImages);

    @Update
    void update(DiaryImages diaryImages);

    @Delete
    void deleteItem(DiaryImages diaryImages);

    @Query("SELECT * FROM diaryimages WHERE id = :identity")
    DiaryImages getDiaryById(Long identity);
}