package com.pranjal.wifidata;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "magentic_field")
public class Magnetic {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="magx")
    public double magx;
    @ColumnInfo(name="magy")
    public double magy;
    @ColumnInfo(name="magz")
    public double magz;
    @ColumnInfo(name="lat")
    public double lat;
    @ColumnInfo(name="lon")
    public double lon;
    @ColumnInfo(name ="time")
    public String time;
    @ColumnInfo(name="location")
    public String location;
    public Magnetic(double magx,double magy,double magz, double lat, double lon, String time,String location){
        this.magx = magx;
        this.magy = magy;
        this.magz = magz;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
        this.location=location;
    }
}
