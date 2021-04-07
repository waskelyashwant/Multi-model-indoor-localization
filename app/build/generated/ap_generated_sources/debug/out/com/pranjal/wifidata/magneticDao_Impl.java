package com.pranjal.wifidata;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class magneticDao_Impl implements magneticDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMagnetic;

  public magneticDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMagnetic = new EntityInsertionAdapter<Magnetic>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `magentic_field`(`id`,`magx`,`magy`,`magz`,`lat`,`lon`,`time`,`location`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Magnetic value) {
        stmt.bindLong(1, value.id);
        stmt.bindDouble(2, value.magx);
        stmt.bindDouble(3, value.magy);
        stmt.bindDouble(4, value.magz);
        stmt.bindDouble(5, value.lat);
        stmt.bindDouble(6, value.lon);
        if (value.time == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.time);
        }
        if (value.location == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.location);
        }
      }
    };
  }

  @Override
  public void insert(Magnetic m) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMagnetic.insert(m);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Magnetic> getAllMagnetic(String loc) {
    final String _sql = "SELECT * FROM magentic_field WHERE location = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (loc == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, loc);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfMagx = _cursor.getColumnIndexOrThrow("magx");
      final int _cursorIndexOfMagy = _cursor.getColumnIndexOrThrow("magy");
      final int _cursorIndexOfMagz = _cursor.getColumnIndexOrThrow("magz");
      final int _cursorIndexOfLat = _cursor.getColumnIndexOrThrow("lat");
      final int _cursorIndexOfLon = _cursor.getColumnIndexOrThrow("lon");
      final int _cursorIndexOfTime = _cursor.getColumnIndexOrThrow("time");
      final int _cursorIndexOfLocation = _cursor.getColumnIndexOrThrow("location");
      final List<Magnetic> _result = new ArrayList<Magnetic>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Magnetic _item;
        final double _tmpMagx;
        _tmpMagx = _cursor.getDouble(_cursorIndexOfMagx);
        final double _tmpMagy;
        _tmpMagy = _cursor.getDouble(_cursorIndexOfMagy);
        final double _tmpMagz;
        _tmpMagz = _cursor.getDouble(_cursorIndexOfMagz);
        final double _tmpLat;
        _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
        final double _tmpLon;
        _tmpLon = _cursor.getDouble(_cursorIndexOfLon);
        final String _tmpTime;
        _tmpTime = _cursor.getString(_cursorIndexOfTime);
        final String _tmpLocation;
        _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
        _item = new Magnetic(_tmpMagx,_tmpMagy,_tmpMagz,_tmpLat,_tmpLon,_tmpTime,_tmpLocation);
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
