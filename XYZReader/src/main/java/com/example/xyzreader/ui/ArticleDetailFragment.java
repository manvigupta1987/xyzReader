package com.example.xyzreader.ui;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Callback;
import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.utils.Utils;
import com.squareup.picasso.Picasso;


import timber.log.Timber;

/**
 * Created by manvi on 3/5/17.
 */

public class ArticleDetailFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String ARG_ITEM_ID = "item_id";
    private long mItemId;
    private String mTransitionName;
    private View mRootView;
    private ImageView mPhotoView;
    private Cursor mCursor;
    private CollapsingToolbarLayout mCollapsingToolBar;
    private TextView titleView;
    private FloatingActionButton mFab;
    private boolean landscape_layout = false;

    public ArticleDetailFragment() {
    }

    public static ArticleDetailFragment newInstance(long itemId, String transitionName) {
        Bundle arguments = new Bundle();
        arguments.putLong(ARG_ITEM_ID, itemId);
        arguments.putString(Utils.TRANSITION_STRING,transitionName);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItemId = getArguments().getLong(ARG_ITEM_ID);
        }
        if (getArguments().containsKey(Utils.TRANSITION_STRING)) {
            mTransitionName = getArguments().getString(Utils.TRANSITION_STRING);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // In support library r8, calling initLoader for a fragment in a FragmentPagerAdapter in
        // the fragment's onCreate may cause the same LoaderManager to be dealt to multiple
        // fragments because their mIndex is -1 (haven't been added to the activity yet). Thus,
        // we do this in onActivityCreated.
        getLoaderManager().initLoader(0, null, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_article_detail, container, false);
        landscape_layout = mRootView.getResources().getBoolean(R.bool.landscape);
        //mUpButtonContainer = (View) mRootView.findViewById(R.id.up_container);
        // mUpButton = (ImageButton)mRootView.findViewById(R.id.action_up);
        mPhotoView = (ImageView) mRootView.findViewById(R.id.photo);
        if(!landscape_layout){
            mCollapsingToolBar = (CollapsingToolbarLayout) mRootView.findViewById(R.id.collapsing_toolbar);
        }else {
             titleView= (TextView) mRootView.findViewById(R.id.article_title);
        }

        if(!landscape_layout) {
            final Toolbar toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            if(((AppCompatActivity) getActivity()).getSupportActionBar()!=null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //((AppCompatActivity) getActivity()).supportFinishAfterTransition();
                    ((AppCompatActivity) getActivity()).onSupportNavigateUp();
                }
            });
        }

        mFab = (FloatingActionButton) mRootView.findViewById(R.id.share_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                toggleFabMenu();
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText("Some sample text")
                        .getIntent(), getString(R.string.action_share)));
            }
        });
        bindViews();
        return mRootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void toggleFabMenu(){

        // get the center for the clipping circle
        int cx = mFab.getWidth()/2;
        int cy = mFab.getHeight()/2;

        // get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

        // create the animation (the final radius is zero)
        Animator animator = ViewAnimationUtils.createCircularReveal(mFab, cx, cy, initialRadius, 0).setDuration(1000);
        animator.start();
    }


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void bindViews() {
        if (mRootView == null) {
            return;
        }

        TextView bylineView = (TextView) mRootView.findViewById(R.id.article_byline);
        bylineView.setMovementMethod(new LinkMovementMethod());
        TextView bodyView = (TextView) mRootView.findViewById(R.id.article_body);
        bodyView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Rosario-Regular.ttf"));

        if (mCursor != null) {
            mRootView.setAlpha(0);
            mRootView.setVisibility(View.VISIBLE);
            mRootView.animate().alpha(1);
            String text = mCursor.getString(ArticleLoader.Query.TITLE);
            if(!landscape_layout) {
                mCollapsingToolBar.setTitleEnabled(true);
                mCollapsingToolBar.setTitle(text);
            }else {

                titleView.setText(text);
                titleView.setContentDescription(getString(R.string.a11y_book_image,titleView.getText()));
            }
            bylineView.setText(DateUtils.getRelativeTimeSpanString(
                    mCursor.getLong(ArticleLoader.Query.PUBLISHED_DATE),
                    System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_ALL).toString()
                    + " by "
                    + mCursor.getString(ArticleLoader.Query.AUTHOR));
            bodyView.setText(Html.fromHtml(mCursor.getString(ArticleLoader.Query.BODY),Html.FROM_HTML_MODE_COMPACT));

            String imageUrl = mCursor.getString(ArticleLoader.Query.PHOTO_URL);
            Picasso.with(getActivity()).load(imageUrl)
                   .config(Bitmap.Config.RGB_565).into(mPhotoView, PicassoPalette.with(imageUrl,mPhotoView)
                    .use(PicassoPalette.Profile.MUTED_DARK)
                    .intoCallBack(new PicassoPalette.CallBack() {
                        @Override
                        public void onPaletteLoaded(Palette palette) {
                            createPalette(palette);
                        }
                    }));
            ViewCompat.setTransitionName(mPhotoView, mTransitionName);
            mPhotoView.setContentDescription(getString(R.string.a11y_book_image,text));
        } else {
            mRootView.setVisibility(View.GONE);
            if(landscape_layout) {
                titleView.setText("N/A");
            }
            bylineView.setText("N/A" );
            bodyView.setText("N/A");
        }
    }

    private void createPalette(Palette palette){
                int primaryDark = getResources().getColor(R.color.primary_dark);
                int primary = getResources().getColor(R.color.primary_light);
                if(!landscape_layout) {
                    mCollapsingToolBar.setContentScrimColor(palette.getMutedColor(primary));
                    mCollapsingToolBar.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
                }
                setStatusBarColor(palette.getDarkMutedColor(primaryDark));
                mRootView.findViewById(R.id.meta_bar).setBackgroundColor(palette.getDarkMutedColor(0xFF333333));
        }

    private void setStatusBarColor(int darkMutedColor){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(darkMutedColor);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newInstanceForItemId(getActivity(), mItemId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(!isAdded()){
            if (cursor != null) {
                cursor.close();
            }
            return;
        }
        mCursor = cursor;
        if(mCursor!=null && !mCursor.moveToFirst()){
            Timber.e("Error reading item detail cursor");
            mCursor.close();
            mCursor = null;
        }
        bindViews();
        mRootView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        mRootView.getViewTreeObserver().removeOnPreDrawListener(this);
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                            AppCompatActivity activity = (AppCompatActivity) getActivity();
                            activity.supportStartPostponedEnterTransition();
                        }
                        return true;
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursor = null;
        bindViews();
    }
}
