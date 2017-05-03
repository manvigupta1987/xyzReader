// Generated code from Butter Knife. Do not modify!
package com.example.xyzreader.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.xyzreader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewArticleDetailFragment_ViewBinding implements Unbinder {
  private NewArticleDetailFragment target;

  @UiThread
  public NewArticleDetailFragment_ViewBinding(NewArticleDetailFragment target, View source) {
    this.target = target;

    target.mCollapsingToolBar = Utils.findRequiredViewAsType(source, R.id.collapsing_toolbar, "field 'mCollapsingToolBar'", CollapsingToolbarLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewArticleDetailFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mCollapsingToolBar = null;
  }
}
