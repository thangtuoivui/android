package com.example.coursework.Databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.coursework.Models.Hike;
import com.example.coursework.dao.HikeDao;


@Database(entities = {Hike.class}, version = 2)
public abstract class HikeDatabase extends RoomDatabase {
    public abstract HikeDao hikeDao();


}
