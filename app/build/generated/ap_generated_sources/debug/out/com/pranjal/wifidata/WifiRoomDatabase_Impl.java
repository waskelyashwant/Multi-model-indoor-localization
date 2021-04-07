package com.pranjal.wifidata;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class WifiRoomDatabase_Impl extends WifiRoomDatabase {
  private volatile wifiDao _wifiDao;

  private volatile magneticDao _magneticDao;

  private volatile gsmDao _gsmDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(6) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `wifi_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `bssid` TEXT NOT NULL, `ssid` TEXT, `frequency` INTEGER NOT NULL, `level` INTEGER NOT NULL, `time` TEXT NOT NULL, `location` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `magentic_field` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `magx` REAL NOT NULL, `magy` REAL NOT NULL, `magz` REAL NOT NULL, `lat` REAL NOT NULL, `lon` REAL NOT NULL, `time` TEXT, `location` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `gsm` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cid` INTEGER NOT NULL, `strength` INTEGER NOT NULL, `time` TEXT, `location` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"5056a8d977b610f0698d4150c8553464\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `wifi_table`");
        _db.execSQL("DROP TABLE IF EXISTS `magentic_field`");
        _db.execSQL("DROP TABLE IF EXISTS `gsm`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsWifiTable = new HashMap<String, TableInfo.Column>(7);
        _columnsWifiTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsWifiTable.put("bssid", new TableInfo.Column("bssid", "TEXT", true, 0));
        _columnsWifiTable.put("ssid", new TableInfo.Column("ssid", "TEXT", false, 0));
        _columnsWifiTable.put("frequency", new TableInfo.Column("frequency", "INTEGER", true, 0));
        _columnsWifiTable.put("level", new TableInfo.Column("level", "INTEGER", true, 0));
        _columnsWifiTable.put("time", new TableInfo.Column("time", "TEXT", true, 0));
        _columnsWifiTable.put("location", new TableInfo.Column("location", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWifiTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWifiTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWifiTable = new TableInfo("wifi_table", _columnsWifiTable, _foreignKeysWifiTable, _indicesWifiTable);
        final TableInfo _existingWifiTable = TableInfo.read(_db, "wifi_table");
        if (! _infoWifiTable.equals(_existingWifiTable)) {
          throw new IllegalStateException("Migration didn't properly handle wifi_table(com.pranjal.wifidata.wifi).\n"
                  + " Expected:\n" + _infoWifiTable + "\n"
                  + " Found:\n" + _existingWifiTable);
        }
        final HashMap<String, TableInfo.Column> _columnsMagenticField = new HashMap<String, TableInfo.Column>(8);
        _columnsMagenticField.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsMagenticField.put("magx", new TableInfo.Column("magx", "REAL", true, 0));
        _columnsMagenticField.put("magy", new TableInfo.Column("magy", "REAL", true, 0));
        _columnsMagenticField.put("magz", new TableInfo.Column("magz", "REAL", true, 0));
        _columnsMagenticField.put("lat", new TableInfo.Column("lat", "REAL", true, 0));
        _columnsMagenticField.put("lon", new TableInfo.Column("lon", "REAL", true, 0));
        _columnsMagenticField.put("time", new TableInfo.Column("time", "TEXT", false, 0));
        _columnsMagenticField.put("location", new TableInfo.Column("location", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMagenticField = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMagenticField = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMagenticField = new TableInfo("magentic_field", _columnsMagenticField, _foreignKeysMagenticField, _indicesMagenticField);
        final TableInfo _existingMagenticField = TableInfo.read(_db, "magentic_field");
        if (! _infoMagenticField.equals(_existingMagenticField)) {
          throw new IllegalStateException("Migration didn't properly handle magentic_field(com.pranjal.wifidata.Magnetic).\n"
                  + " Expected:\n" + _infoMagenticField + "\n"
                  + " Found:\n" + _existingMagenticField);
        }
        final HashMap<String, TableInfo.Column> _columnsGsm = new HashMap<String, TableInfo.Column>(5);
        _columnsGsm.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsGsm.put("cid", new TableInfo.Column("cid", "INTEGER", true, 0));
        _columnsGsm.put("strength", new TableInfo.Column("strength", "INTEGER", true, 0));
        _columnsGsm.put("time", new TableInfo.Column("time", "TEXT", false, 0));
        _columnsGsm.put("location", new TableInfo.Column("location", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGsm = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGsm = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGsm = new TableInfo("gsm", _columnsGsm, _foreignKeysGsm, _indicesGsm);
        final TableInfo _existingGsm = TableInfo.read(_db, "gsm");
        if (! _infoGsm.equals(_existingGsm)) {
          throw new IllegalStateException("Migration didn't properly handle gsm(com.pranjal.wifidata.gsm).\n"
                  + " Expected:\n" + _infoGsm + "\n"
                  + " Found:\n" + _existingGsm);
        }
      }
    }, "5056a8d977b610f0698d4150c8553464", "fe5bc2f276b754c40d936d6736d67f95");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "wifi_table","magentic_field","gsm");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `wifi_table`");
      _db.execSQL("DELETE FROM `magentic_field`");
      _db.execSQL("DELETE FROM `gsm`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public wifiDao wifiDao() {
    if (_wifiDao != null) {
      return _wifiDao;
    } else {
      synchronized(this) {
        if(_wifiDao == null) {
          _wifiDao = new wifiDao_Impl(this);
        }
        return _wifiDao;
      }
    }
  }

  @Override
  public magneticDao magneticDao() {
    if (_magneticDao != null) {
      return _magneticDao;
    } else {
      synchronized(this) {
        if(_magneticDao == null) {
          _magneticDao = new magneticDao_Impl(this);
        }
        return _magneticDao;
      }
    }
  }

  @Override
  public gsmDao gsmDao() {
    if (_gsmDao != null) {
      return _gsmDao;
    } else {
      synchronized(this) {
        if(_gsmDao == null) {
          _gsmDao = new gsmDao_Impl(this);
        }
        return _gsmDao;
      }
    }
  }
}
