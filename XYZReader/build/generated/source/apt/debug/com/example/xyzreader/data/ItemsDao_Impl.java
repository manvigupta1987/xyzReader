package com.example.xyzreader.data;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;

public class ItemsDao_Impl implements ItemsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfItems;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfItems;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ItemsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfItems = new EntityInsertionAdapter<Items>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Items`(`_id`,`server_id`,`title`,`author`,`body`,`thumb_url`,`photo_url`,`aspect_ratio`,`published_date`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Items value) {
        stmt.bindLong(1, value._ID);
        if (value.SERVER_ID == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.SERVER_ID);
        }
        if (value.TITLE == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.TITLE);
        }
        if (value.AUTHOR == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.AUTHOR);
        }
        if (value.BODY == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.BODY);
        }
        if (value.THUMB_URL == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.THUMB_URL);
        }
        if (value.PHOTO_URL == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.PHOTO_URL);
        }
        if (value.ASPECT_RATIO == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.ASPECT_RATIO);
        }
        if (value.PUBLISHED_DATE == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.PUBLISHED_DATE);
        }
      }
    };
    this.__updateAdapterOfItems = new EntityDeletionOrUpdateAdapter<Items>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Items` SET `_id` = ?,`server_id` = ?,`title` = ?,`author` = ?,`body` = ?,`thumb_url` = ?,`photo_url` = ?,`aspect_ratio` = ?,`published_date` = ? WHERE `_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Items value) {
        stmt.bindLong(1, value._ID);
        if (value.SERVER_ID == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.SERVER_ID);
        }
        if (value.TITLE == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.TITLE);
        }
        if (value.AUTHOR == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.AUTHOR);
        }
        if (value.BODY == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.BODY);
        }
        if (value.THUMB_URL == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.THUMB_URL);
        }
        if (value.PHOTO_URL == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.PHOTO_URL);
        }
        if (value.ASPECT_RATIO == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.ASPECT_RATIO);
        }
        if (value.PUBLISHED_DATE == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.PUBLISHED_DATE);
        }
        stmt.bindLong(10, value._ID);
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Items WHERE _id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Items";
        return _query;
      }
    };
  }

  @Override
  public long insert(Items item) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfItems.insertAndReturnId(item);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long[] insertAll(Items[] items) {
    __db.beginTransaction();
    try {
      long[] _result = __insertionAdapterOfItems.insertAndReturnIdsArray(items);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int update(Items item) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfItems.handle(item);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteById(long id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, id);
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public int deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public Cursor selectAll() {
    final String _sql = "SELECT * FROM Items";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.query(_statement);
  }

  @Override
  public Cursor selectById(long id) {
    final String _sql = "SELECT * FROM Items WHERE _id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.query(_statement);
  }
}
