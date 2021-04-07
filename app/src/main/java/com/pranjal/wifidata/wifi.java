package com.pranjal.wifidata;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wifi_table")
public class wifi {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;
    @NonNull
    @ColumnInfo(name = "bssid")
    public String bssid;
    @ColumnInfo(name = "ssid")
    public String ssid;

    @NonNull
    @ColumnInfo(name = "frequency")
    public int frequency;

    @NonNull
    @ColumnInfo(name = "level")
    public int level;

    @NonNull
    @ColumnInfo(name ="time")
    public String time;
    @ColumnInfo(name="location")
    public String location;

    public wifi (String bssid,String ssid,int frequency,int level,String time,String location){
        this.frequency=frequency;
        this.ssid=ssid;
        this.level = level;
        this.time = time;
        this.location = location;
        this.bssid = bssid;
    }

}