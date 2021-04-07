package com.pranjal.wifidata;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface magneticDao {
    @Insert
    void insert(Magnetic m);
    @Query("SELECT * FROM magentic_field WHERE location = :loc")
    List<Magnetic> getAllMagnetic(String loc);
}
