package com.pranjal.wifidata;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gps_table")
public class gps {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "lat")
    public double lat;
    @ColumnInfo(name = "long")
    public double lon;
    @ColumnInfo(name = "time")
    public String time;
    @ColumnInfo(name = "location")
    public String location;

    public gps(double lat, double lon, String time, String location) {
        this.lat = lat;
        this.lon = lon;
        this.time = time;
        this.location = location;
    }
}