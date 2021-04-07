package com.pranjal.wifidata;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface wifiDao {
    @Insert
    void insert(wifi w);
    @Query("SELECT * FROM wifi_table WHERE location = :loc")
    List<wifi> getAllWifi(String loc);

}
