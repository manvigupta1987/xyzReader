
package com.example.xyzreader.data;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class ItemsProvider extends ContentProvider {

	public static final String AUTHORITY = "com.example.xyzreader.data";
	private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

	public static Uri buildDirUri() {
			return BASE_URI.buildUpon().appendPath(Items.TABLE_NAME).build();
	}

    public static Uri buildItemUri(long _id) {
			return BASE_URI.buildUpon().appendPath(Items.TABLE_NAME).appendPath(Long.toString(_id)).build();
		}

    public static long getItemId(Uri itemUri) {
        return Long.parseLong(itemUri.getPathSegments().get(1));
    }

	private static final int ITEMS = 0;
	private static final int ITEMS__ID = 1;

	private static final UriMatcher sUriMatcher = buildUriMatcher();

	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = AUTHORITY;
		matcher.addURI(authority, Items.TABLE_NAME, ITEMS);
		matcher.addURI(authority, Items.TABLE_NAME+ "/#", ITEMS__ID);
		return matcher;
	}


	@Override
	public boolean onCreate() {
		return true;
	}


	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
						@Nullable String[] selectionArgs, @Nullable String sortOrder) {
		final int code = sUriMatcher.match(uri);
		if (code == ITEMS || code == ITEMS__ID) {
			final Context context = getContext();
			if (context == null) {
				return null;
			}
			ItemsDao cheese = ItemsDatabase.getInstance(context).item();
			final Cursor cursor;
			if (code == ITEMS) {
				cursor = cheese.selectAll();
			} else {
				cursor = cheese.selectById(ContentUris.parseId(uri));
			}
			cursor.setNotificationUri(context.getContentResolver(), uri);
			return cursor;
		} else {
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		switch (sUriMatcher.match(uri)) {
			case ITEMS:
				return "vnd.android.cursor.dir/" + AUTHORITY + "." + Items.TABLE_NAME;
			case ITEMS__ID:
				return "vnd.android.cursor.item/" + AUTHORITY + "." + Items.TABLE_NAME;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}


	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		switch (sUriMatcher.match(uri)) {
			case ITEMS:
				final Context context = getContext();
				if (context == null) {
					return null;
				}
				final long id = ItemsDatabase.getInstance(context).item()
						.insert(Items.fromContentValues(values));
				context.getContentResolver().notifyChange(uri, null);
				return ContentUris.withAppendedId(uri, id);
			case ITEMS__ID:
				throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection,
					  @Nullable String[] selectionArgs) {
        final Context context = getContext();
        if (context == null) {
            return 0;
        }
		switch (sUriMatcher.match(uri)) {
			case ITEMS:
                final int count = ItemsDatabase.getInstance(context).item().deleteAll();
                context.getContentResolver().notifyChange(uri, null);
                return count;
			case ITEMS__ID:
			    long id = ContentUris.parseId(uri);
				final int count1 = ItemsDatabase.getInstance(context).item()
						.deleteById(id);
				context.getContentResolver().notifyChange(uri, null);
				return count1;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}


	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
					  @Nullable String[] selectionArgs) {
		switch (sUriMatcher.match(uri)) {
			case ITEMS:
				throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
			case ITEMS__ID:
				final Context context = getContext();
				if (context == null) {
					return 0;
				}
				final Items item = Items.fromContentValues(values);
				item._ID = ContentUris.parseId(uri);
				final int count = ItemsDatabase.getInstance(context).item().update(item);
				context.getContentResolver().notifyChange(uri, null);
				return count;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}


	@Override
	public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] valuesArray) {
		switch (sUriMatcher.match(uri)) {
			case ITEMS:
				final Context context = getContext();
				if (context == null) {
					return 0;
				}
				final ItemsDatabase database = ItemsDatabase.getInstance(context);
				final Items[] items = new Items[valuesArray.length];
				for (int i = 0; i < valuesArray.length; i++) {
					items[i] = Items.fromContentValues(valuesArray[i]);
				}
				return database.item().insertAll(items).length;
			case ITEMS__ID:
				throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final ItemsDatabase db = ItemsDatabase.getInstance(getContext());
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }
}
