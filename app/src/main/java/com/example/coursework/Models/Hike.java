package com.example.coursework.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "HikeManager")
public class Hike {
    @PrimaryKey(autoGenerate = true)
    public long hike_id;
    public String name;
    public String location;
    public String date;
    public String parking;
    public String length;
    public String difficulty_level;
    public String description;

    public Hike(long hike_id, String name, String location, String date, String parking, String length, String difficulty_level, String description) {
        this.hike_id = hike_id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.parking = parking;
        this.length = length;
        this.difficulty_level = difficulty_level;
        this.description = description;
    }

    public Hike() {

    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public long getHike_id() {
        return hike_id;
    }

    public void setHike_id(long hike_id) {
        this.hike_id = hike_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(String difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
