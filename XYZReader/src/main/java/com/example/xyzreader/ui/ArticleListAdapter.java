package com.example.xyzreader.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xyzreader.R;
import com.example.xyzreader.data.Items;
import com.example.xyzreader.data.ItemsProvider;
import com.example.xyzreader.utils.Utils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by manvi on 2/5/17.
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticalListViewHolder> {
    private Cursor mCursor;
    private final Context mContext;


    public ArticleListAdapter( Context context)
    {
        mContext = context;
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(mCursor.getColumnIndex(Items.COLUMN_ID));
    }

    @Override
    public ArticalListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_article, viewGroup, false);
        final ArticalListViewHolder vh = new ArticalListViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,ArticleDetailActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(ItemsProvider.buildItemUri(getItemId(vh.getAdapterPosition())));
                intent.putExtra(Utils.TRANSITION_STRING,view.findViewById(R.id.thumbnail).getTransitionName());

                Pair<View, String> p1 = Pair.create(view.findViewById(R.id.thumbnail),view.findViewById(R.id.thumbnail).getTransitionName());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((AppCompatActivity)mContext,p1);
                mContext.startActivity(intent,options.toBundle());
            }
        });
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ArticalListViewHolder holder, int position) {
        if(mCursor.moveToPosition(position)) {
            String title = mCursor.getString(mCursor.getColumnIndex(Items.COLUMN_TITLE));
            Long date = mCursor.getLong(mCursor.getColumnIndex(Items.COLUMN_PUBLISHED_DATE));
            String author = mCursor.getString(mCursor.getColumnIndex(Items.COLUMN_AUTHOR));

            holder.titleView.setText(title);
            holder.titleView.setContentDescription(mContext.getString(R.string.a11y_book_image, holder.titleView.getText()));
            holder.subtitleView.setText(DateUtils.getRelativeTimeSpanString(date,
                    System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_ALL).toString()
                    + " by "
                    + author);
            holder.subtitleView.setContentDescription(mContext.getString(R.string.a11y_book_subtitle, holder.subtitleView.getText()));
            String urlString = mCursor.getString(mCursor.getColumnIndex(Items.COLUMN_THUMB_URL));
            Picasso.with(mContext)
                    .load(urlString)
                    .into(holder.thumbnailView);
            Timber.d("=================================Manvi Aspect======================" + mCursor.getColumnIndex(Items.COLUMN_ASPECT_RATIO));
            holder.thumbnailView.setAspectRatio(mCursor.getFloat(mCursor.getColumnIndex(Items.COLUMN_ASPECT_RATIO)));
            holder.thumbnailView.setContentDescription(mContext.getString(R.string.a11y_book_image, holder.titleView.getText()));
            ViewCompat.setTransitionName(holder.thumbnailView, mContext.getString(R.string.image_transition) + position);
        }
    }

    @Override
    public int getItemCount() {
        if(mCursor ==null)
        {
            return 0;
        }else {
            return mCursor.getCount();
        }
    }

    public void swapCursor(Cursor cursor)
    {
        mCursor = cursor;
        if(mCursor!=null) {
            notifyDataSetChanged();
        }
    }

    public class ArticalListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail) DynamicHeightNetworkImageView thumbnailView;
        @BindView(R.id.article_title) TextView titleView;
        @BindView(R.id.article_subtitle) TextView subtitleView;

        public ArticalListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
