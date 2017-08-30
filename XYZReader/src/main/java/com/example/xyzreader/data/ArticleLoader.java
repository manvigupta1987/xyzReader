package com.example.xyzreader.data;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;

/**
 * Helper for loading a list of articles or a single article.
 */
public class ArticleLoader extends CursorLoader {

    public static ArticleLoader newAllArticlesInstance(Context context) {
        return new ArticleLoader(context, ItemsProvider.buildDirUri());
    }

    public static ArticleLoader newInstanceForItemId(Context context, long itemId) {
        return new ArticleLoader(context, ItemsProvider.buildItemUri(itemId));
    }

    private ArticleLoader(Context context, Uri uri) {
        super(context, uri, null, null, null, Items.COLUMN_PUBLISHED_DATE + " DESC");
    }
}
