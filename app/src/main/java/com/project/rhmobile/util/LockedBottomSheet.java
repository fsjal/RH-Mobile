package com.project.rhmobile.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class LockedBottomSheet<V extends View> extends BottomSheetBehavior<V> {

    private boolean mAllowUserDragging = true;

    public LockedBottomSheet() {
        super();
    }

    public LockedBottomSheet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        return false;
    }
}
