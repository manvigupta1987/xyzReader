package com.example.xyzreader.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;

@Entity(tableName = Items.TABLE_NAME)
public final class Items {

    /**
     * The name of the Items table.
     */
    public static final String TABLE_NAME = "Items";

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_SERVER_ID = "server_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_THUMB_URL = "thumb_url";
    public static final String COLUMN_PHOTO_URL = "photo_url";
    public static final String COLUMN_ASPECT_RATIO = "aspect_ratio";
    public static final String COLUMN_PUBLISHED_DATE = "published_date";

    /**
     * Type: INTEGER PRIMARY KEY AUTOINCREMENT
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long _ID;
    /**
     * Type: TEXT
     */
    @ColumnInfo(name = COLUMN_SERVER_ID)
    public String SERVER_ID;
    /**
     * Type: TEXT NOT NULL
     */
    @ColumnInfo(name = COLUMN_TITLE)
    public String TITLE;
    /**
     * Type: TEXT NOT NULL
     */
    @ColumnInfo(name = COLUMN_AUTHOR)
    public String AUTHOR;
    /**
     * Type: TEXT NOT NULL
     */
    @ColumnInfo(name = COLUMN_BODY)
    public String BODY;
    /**
     * Type: TEXT NOT NULL
     */
    @ColumnInfo(name = COLUMN_THUMB_URL)
    public String THUMB_URL;
    /**
     * Type: TEXT NOT NULL
     */
    @ColumnInfo(name = COLUMN_PHOTO_URL)
    public String PHOTO_URL;
    /**
     * Type: REAL NOT NULL DEFAULT 1.5
     */
    @ColumnInfo(name = COLUMN_ASPECT_RATIO)
    public String ASPECT_RATIO;
    /**
     * Type: INTEGER NOT NULL DEFAULT 0
     */
    @ColumnInfo(name = COLUMN_PUBLISHED_DATE)
    public String PUBLISHED_DATE;

    public static Items fromContentValues(ContentValues values) {
        final Items item = new Items();
        if (values.containsKey(COLUMN_SERVER_ID)) {
            item.SERVER_ID = values.getAsString(COLUMN_SERVER_ID);
        }
        if (values.containsKey(COLUMN_TITLE)) {
            item.TITLE = values.getAsString(COLUMN_TITLE);
        }
        if (values.containsKey(COLUMN_AUTHOR)) {
            item.AUTHOR = values.getAsString(COLUMN_AUTHOR);
        }
        if (values.containsKey(COLUMN_BODY)) {
            item.BODY = values.getAsString(COLUMN_BODY);
        }
        if (values.containsKey(COLUMN_THUMB_URL)) {
            item.THUMB_URL = values.getAsString(COLUMN_THUMB_URL);
        }
        if (values.containsKey(COLUMN_PHOTO_URL)) {
            item.PHOTO_URL = values.getAsString(COLUMN_PHOTO_URL);
        }
        if (values.containsKey(COLUMN_ASPECT_RATIO)) {
            item.ASPECT_RATIO = values.getAsString(COLUMN_ASPECT_RATIO);
        }
        if (values.containsKey(COLUMN_PUBLISHED_DATE)) {
            item.PUBLISHED_DATE = values.getAsString(COLUMN_PUBLISHED_DATE);
        }
        return item;
    }
}