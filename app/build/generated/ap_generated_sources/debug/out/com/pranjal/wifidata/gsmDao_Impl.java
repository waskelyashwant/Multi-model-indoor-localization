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
public final class gsmDao_Impl implements gsmDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfgsm;

  public gsmDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfgsm = new EntityInsertionAdapter<gsm>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `gsm`(`id`,`cid`,`strength`,`time`,`location`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, gsm value) {
        stmt.bindLong(1, value.id);
        stmt.bindLong(2, value.cid);
        stmt.bindLong(3, value.strength);
        if (value.time == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.time);
        }
        if (value.location == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.location);
        }
      }
    };
  }

  @Override
  public void insert(gsm g) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfgsm.insert(g);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<gsm> getAllGSM(String loc) {
    final String _sql = "SELECT * FROM gsm WHERE location = ?";
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
      final int _cursorIndexOfCid = _cursor.getColumnIndexOrThrow("cid");
      final int _cursorIndexOfStrength = _cursor.getColumnIndexOrThrow("strength");
      final int _cursorIndexOfTime = _cursor.getColumnIndexOrThrow("time");
      final int _cursorIndexOfLocation = _cursor.getColumnIndexOrThrow("location");
      final List<gsm> _result = new ArrayList<gsm>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final gsm _item;
        final int _tmpCid;
        _tmpCid = _cursor.getInt(_cursorIndexOfCid);
        final int _tmpStrength;
        _tmpStrength = _cursor.getInt(_cursorIndexOfStrength);
        final String _tmpTime;
        _tmpTime = _cursor.getString(_cursorIndexOfTime);
        final String _tmpLocation;
        _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
        _item = new gsm(_tmpCid,_tmpStrength,_tmpTime,_tmpLocation);
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
