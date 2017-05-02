// Generated code from Butter Knife. Do not modify!
package com.example.xyzreader.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.xyzreader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ArticleListActivity_ViewBinding implements Unbinder {
  private ArticleListActivity target;

  @UiThread
  public ArticleListActivity_ViewBinding(ArticleListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ArticleListActivity_ViewBinding(ArticleListActivity target, View source) {
    this.target = target;

    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipe_refresh_layout, "field 'mSwipeRefreshLayout'", SwipeRefreshLayout.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.recycler_view, "field 'mRecyclerView'", RecyclerView.class);
    target.mEmptyView = Utils.findRequiredViewAsType(source, R.id.empty_view, "field 'mEmptyView'", TextView.class);
    target.mCordinatorLayout = Utils.findRequiredViewAsType(source, R.id.col, "field 'mCordinatorLayout'", CoordinatorLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ArticleListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mToolbar = null;
    target.mSwipeRefreshLayout = null;
    target.mRecyclerView = null;
    target.mEmptyView = null;
    target.mCordinatorLayout = null;
  }
}
