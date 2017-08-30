package com.example.xyzreader.data;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;

import com.example.xyzreader.remote.RemoteEndpointUtil;
import com.example.xyzreader.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import timber.log.Timber;

public class UpdaterService extends IntentService {

    public static final String BROADCAST_ACTION_STATE_CHANGE
            = "com.example.xyzreader.intent.action.STATE_CHANGE";
    public static final String EXTRA_REFRESHING
            = "com.example.xyzreader.intent.extra.REFRESHING";

    public UpdaterService() {
        super("UpdaterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        if(!Utils.isNetworkConnectionAvailable(this)){
            return;
        }
        sendBroadcast(
                new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, true));

        // Don't even inspect the intent, we only do one thing, and that's fetch content.
        ArrayList<ContentProviderOperation> cpo = new ArrayList<>();

        Uri dirUri = ItemsProvider.buildDirUri();

        // Delete all items
        cpo.add(ContentProviderOperation.newDelete(dirUri).build());

        try {
            JSONArray array = RemoteEndpointUtil.fetchJsonArray();
            if (array == null) {
                throw new JSONException("Invalid parsed item array" );
            }

            for (int i = 0; i < array.length(); i++) {
                ContentValues values = new ContentValues();
                JSONObject object = array.getJSONObject(i);
                values.put(Items.COLUMN_SERVER_ID, object.getString("id" ));
                values.put(Items.COLUMN_AUTHOR, object.getString("author" ));
                values.put(Items.COLUMN_TITLE, object.getString("title" ));
                values.put(Items.COLUMN_BODY, object.getString("body" ));
                values.put(Items.COLUMN_THUMB_URL, object.getString("thumb" ));
                values.put(Items.COLUMN_PHOTO_URL, object.getString("photo" ));
                Timber.d("=======================Here Aspect Ratio==================" + object.getString("aspect_ratio" ));
                values.put(Items.COLUMN_ASPECT_RATIO, object.getString("aspect_ratio" ));
                values.put(Items.COLUMN_PUBLISHED_DATE, object.getString("published_date"));
                cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
            }

            getContentResolver().applyBatch(ItemsProvider.AUTHORITY, cpo);

        } catch (JSONException | RemoteException | OperationApplicationException e) {
            Timber.e(e, "Error updating content.");
        }

        sendBroadcast(
                new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, false));
    }
}
