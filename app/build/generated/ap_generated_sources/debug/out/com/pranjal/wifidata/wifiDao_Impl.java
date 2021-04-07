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
public final class wifiDao_Impl implements wifiDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfwifi;

  public wifiDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfwifi = new EntityInsertionAdapter<wifi>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `wifi_table`(`id`,`bssid`,`ssid`,`frequency`,`level`,`time`,`location`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, wifi value) {
        stmt.bindLong(1, value.id);
        if (value.bssid == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.bssid);
        }
        if (value.ssid == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.ssid);
        }
        stmt.bindLong(4, value.frequency);
        stmt.bindLong(5, value.level);
        if (value.time == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.time);
        }
        if (value.location == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.location);
        }
      }
    };
  }

  @Override
  public void insert(wifi w) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfwifi.insert(w);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<wifi> getAllWifi(String loc) {
    final String _sql = "SELECT * FROM wifi_table WHERE location = ?";
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
      final int _cursorIndexOfBssid = _cursor.getColumnIndexOrThrow("bssid");
      final int _cursorIndexOfSsid = _cursor.getColumnIndexOrThrow("ssid");
      final int _cursorIndexOfFrequency = _cursor.getColumnIndexOrThrow("frequency");
      final int _cursorIndexOfLevel = _cursor.getColumnIndexOrThrow("level");
      final int _cursorIndexOfTime = _cursor.getColumnIndexOrThrow("time");
      final int _cursorIndexOfLocation = _cursor.getColumnIndexOrThrow("location");
      final List<wifi> _result = new ArrayList<wifi>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final wifi _item;
        final String _tmpBssid;
        _tmpBssid = _cursor.getString(_cursorIndexOfBssid);
        final String _tmpSsid;
        _tmpSsid = _cursor.getString(_cursorIndexOfSsid);
        final int _tmpFrequency;
        _tmpFrequency = _cursor.getInt(_cursorIndexOfFrequency);
        final int _tmpLevel;
        _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
        final String _tmpTime;
        _tmpTime = _cursor.getString(_cursorIndexOfTime);
        final String _tmpLocation;
        _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
        _item = new wifi(_tmpBssid,_tmpSsid,_tmpFrequency,_tmpLevel,_tmpTime,_tmpLocation);
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
