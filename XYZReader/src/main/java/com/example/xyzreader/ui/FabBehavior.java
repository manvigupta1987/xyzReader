package com.example.xyzreader.ui;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.example.xyzreader.R;

/**
 * Created by manvi on 8/5/17.
 */

public class FabBehavior extends CoordinatorLayout.Behavior<FloatingActionButton>{
    public FabBehavior(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
    }

    //When you define your own behaviour, two methods are important to over-ride.
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    //The Android documentation indicates that by setting up this dependency, you will receive calls to
    // onDependentViewChanged when a dependent view changes its size or position.

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        if(child == (View)parent.findViewById(R.id.share_fab) && dependency == (View)parent.findViewById(R.id.appbar)){
            child.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }
}
