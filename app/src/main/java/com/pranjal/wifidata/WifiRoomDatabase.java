package com.pranjal.wifidata;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {wifi.class,Magnetic.class,gsm.class}, version = 6, exportSchema = false)
public abstract class WifiRoomDatabase extends RoomDatabase {
    public abstract wifiDao wifiDao();
    public abstract magneticDao magneticDao();
    public abstract  gsmDao gsmDao();
    //public abstract gpsDao gpsDao();
    private static WifiRoomDatabase INSTANCE;

    static WifiRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WifiRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WifiRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}