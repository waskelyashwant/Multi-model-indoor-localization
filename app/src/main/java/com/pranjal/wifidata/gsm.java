package com.pranjal.wifidata;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gsm")
public class gsm {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name="cid")
    public int cid;
    @ColumnInfo(name="strength")
    public int strength;
    @ColumnInfo(name ="time")
    public String time;
    @ColumnInfo(name="location")
    public String location;

    public gsm(int cid,int strength,String time,String location){
        this.cid = cid;
        this.strength = strength;
        this.time = time;
        this.location = location;
    }
}
