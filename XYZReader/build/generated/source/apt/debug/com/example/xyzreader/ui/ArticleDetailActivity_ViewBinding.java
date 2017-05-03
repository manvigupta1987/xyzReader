// Generated code from Butter Knife. Do not modify!
package com.example.xyzreader.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.xyzreader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ArticleDetailActivity_ViewBinding implements Unbinder {
  private ArticleDetailActivity target;

  @UiThread
  public ArticleDetailActivity_ViewBinding(ArticleDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ArticleDetailActivity_ViewBinding(ArticleDetailActivity target, View source) {
    this.target = target;

    target.mPager = Utils.findRequiredViewAsType(source, R.id.pager, "field 'mPager'", ViewPager.class);
    target.mUpButtonContainer = Utils.findRequiredView(source, R.id.up_container, "field 'mUpButtonContainer'");
    target.mUpButton = Utils.findRequiredView(source, R.id.action_up, "field 'mUpButton'");
  }

  @Override
  @CallSuper
  public void unbind() {
    ArticleDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mPager = null;
    target.mUpButtonContainer = null;
    target.mUpButton = null;
  }
}
