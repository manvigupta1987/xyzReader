package com.example.xyzreader.data;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

@Database(entities = {Items.class}, version = 1)
public abstract class ItemsDatabase extends RoomDatabase {

    public abstract ItemsDao item();
    private static ItemsDatabase sInstance;

    public static ItemsDatabase getInstance(Context context) {
        if (sInstance == null)
        {
            synchronized(ItemsDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room
                            .databaseBuilder(context.getApplicationContext(), ItemsDatabase.class, "xyzreader.db")
                            .build();
                }
            }
        }
        return sInstance;
    }

    @VisibleForTesting
    public static void switchToInMemory(Context context) {
        sInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                ItemsDatabase.class).build();
    }


    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
