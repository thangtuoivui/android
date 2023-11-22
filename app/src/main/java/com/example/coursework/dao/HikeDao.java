package com.example.coursework.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursework.Models.Hike;

import java.util.List;

@Dao
public interface HikeDao {
    @Insert
    long insertHike(Hike hike);

    @Query("SELECT * FROM HikeManager ORDER BY name")
    List<Hike> getAllHIkes();
    @Delete
    void delete(Hike hike);

    @Query("DELETE FROM HikeManager")
    void deleteALl();

    @Update
    void updateHike(Hike hike);

    @Query("SELECT * FROM HikeManager WHERE name LIKE :name ")
    List<Hike> searchHikeName(String name);

}
