package com.pranjal.wifidata;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface gpsDao {
    @Insert
    void insert(gps gp);
    @Query("SELECT * FROM gps_table WHERE location = :loc")
    List<gps> getAllGPS(String loc);
}
