package com.example.enkicsinaplom.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.Date;

@Entity(tableName = "diarychapter")
public class DiaryChapter {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "title")
    public String title;

    public static class DateConverter {

        @TypeConverter
        public static Date toDate(Long dateLong){
            return dateLong == null ? null: new Date(dateLong);
        }

        @TypeConverter
        public static Long fromDate(Date date){
            return date == null ? null : date.getTime();
        }
    }

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo (name = "imgurl")
    public String imgurl;

    @ColumnInfo(name = "text")
    public String text;
}
