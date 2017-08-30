package com.example.xyzreader.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.HashSet;

public class ItemsDatabase_Impl extends ItemsDatabase {
  private volatile ItemsDao _itemsDao;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate() {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Items` (`_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `server_id` TEXT, `title` TEXT, `author` TEXT, `body` TEXT, `thumb_url` TEXT, `photo_url` TEXT, `aspect_ratio` TEXT, `published_date` TEXT)");
        _db.execSQL("CREATE  INDEX `index_Items__id` ON `Items` (`_id`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"24b06c0a1447c37e3d11b74a47367caa\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Items`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsItems = new HashMap<String, TableInfo.Column>(9);
        _columnsItems.put("_id", new TableInfo.Column("_id", "INTEGER", true, 1));
        _columnsItems.put("server_id", new TableInfo.Column("server_id", "TEXT", false, 0));
        _columnsItems.put("title", new TableInfo.Column("title", "TEXT", false, 0));
        _columnsItems.put("author", new TableInfo.Column("author", "TEXT", false, 0));
        _columnsItems.put("body", new TableInfo.Column("body", "TEXT", false, 0));
        _columnsItems.put("thumb_url", new TableInfo.Column("thumb_url", "TEXT", false, 0));
        _columnsItems.put("photo_url", new TableInfo.Column("photo_url", "TEXT", false, 0));
        _columnsItems.put("aspect_ratio", new TableInfo.Column("aspect_ratio", "TEXT", false, 0));
        _columnsItems.put("published_date", new TableInfo.Column("published_date", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysItems = new HashSet<TableInfo.ForeignKey>(0);
        final TableInfo _infoItems = new TableInfo("Items", _columnsItems, _foreignKeysItems);
        final TableInfo _existingItems = TableInfo.read(_db, "Items");
        if (! _infoItems.equals(_existingItems)) {
          throw new IllegalStateException("Migration didn't properly handle Items(com.example.xyzreader.data.Items).\n"
                  + " Expected:\n" + _infoItems + "\n"
                  + " Found:\n" + _existingItems);
        }
      }
    }, "24b06c0a1447c37e3d11b74a47367caa");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .version(1)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "Items");
  }

  @Override
  public ItemsDao item() {
    if (_itemsDao != null) {
      return _itemsDao;
    } else {
      synchronized(this) {
        if(_itemsDao == null) {
          _itemsDao = new ItemsDao_Impl(this);
        }
        return _itemsDao;
      }
    }
  }
}
