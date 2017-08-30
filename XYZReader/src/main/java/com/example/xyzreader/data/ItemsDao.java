package com.example.xyzreader.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

/**
 * Created by manvi on 29/8/17.
 */

@Dao
public interface ItemsDao {

    /**
     * Inserts a cheese into the table.
     *
     * @param Items A new item.
     * @return The row ID of the newly inserted Item.
     */
    @Insert
    long insert(Items item);

    /**
     * Inserts multiple cheeses into the database
     *
     * @param ItemsContract An array of new items.
     * @return The row IDs of the newly inserted cheeses.
     */
    @Insert
    long[] insertAll(Items[] items);

    /**
     * Select all cheeses.
     *
     * @return A {@link Cursor} of all the cheeses in the table.
     */
    @Query("SELECT * FROM " + Items.TABLE_NAME)
    Cursor selectAll();

    /**
     * Select a cheese by the ID.
     *
     * @param id The row ID.
     * @return A {@link Cursor} of the selected cheese.
     */
    @Query("SELECT * FROM " + Items.TABLE_NAME + " WHERE " +  Items.COLUMN_ID + " = :id")
    Cursor selectById(long id);

    /*
     * Delete a cheese by the ID.
     *
     * @param id The row ID.
     * @return A number of cheeses deleted. This should always be {@code 1}.
     */
    @Query("DELETE FROM " + Items.TABLE_NAME + " WHERE " +  Items.COLUMN_ID + " = :id")
    int deleteById(long id);

    @Query("DELETE FROM " + Items.TABLE_NAME)
    int deleteAll();

    @Update
    int update(Items item);
}
