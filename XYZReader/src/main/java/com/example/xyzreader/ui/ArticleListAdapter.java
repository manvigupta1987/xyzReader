package com.example.xyzreader.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;
import com.example.xyzreader.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by manvi on 2/5/17.
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticalListViewHolder> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
    // Use default locale format
    private SimpleDateFormat outputFormat = new SimpleDateFormat();
    // Most time functions can only handle 1902 - 2037
    private GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2, 1, 1);
    private Cursor mCursor;
    private Context mContext;


    public ArticleListAdapter( Context context)
    {
        mContext = context;
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(ArticleLoader.Query._ID);
    }

    @Override
    public ArticalListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_article, viewGroup, false);
        final ArticalListViewHolder vh = new ArticalListViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        ItemsContract.Items.buildItemUri(getItemId(vh.getAdapterPosition())));
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
        mCursor.moveToPosition(position);
        holder.titleView.setText(mCursor.getString(ArticleLoader.Query.TITLE));
        holder.titleView.setContentDescription(mContext.getString(R.string.a11y_book_image,holder.titleView.getText()));
        holder.subtitleView.setText(DateUtils.getRelativeTimeSpanString(
                    mCursor.getLong(ArticleLoader.Query.PUBLISHED_DATE),
                    System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_ALL).toString()
                    + " by "
                    + mCursor.getString(ArticleLoader.Query.AUTHOR));
        holder.subtitleView.setContentDescription(mContext.getString(R.string.a11y_book_subtitle,holder.subtitleView.getText()));
        String urlString = mCursor.getString(ArticleLoader.Query.THUMB_URL);
        Picasso.with(mContext)
                .load(urlString)
                .into(holder.thumbnailView);
        holder.thumbnailView.setAspectRatio(mCursor.getFloat(ArticleLoader.Query.ASPECT_RATIO));
        holder.thumbnailView.setContentDescription(mContext.getString(R.string.a11y_book_image,holder.titleView.getText()));
        ViewCompat.setTransitionName(holder.thumbnailView,mContext.getString(R.string.image_transition) + position);
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
        notifyDataSetChanged();
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
