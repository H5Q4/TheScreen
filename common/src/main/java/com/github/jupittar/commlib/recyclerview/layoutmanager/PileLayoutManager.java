package com.github.jupittar.commlib.recyclerview.layoutmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangqiao on 23/04/2017.
 */

public class PileLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = PileLayoutManager.class.getSimpleName();

    public static final int MAX_SHOW_COUNT = 4;
    public static final float SCALE_GAP = 0.05f;
    public static int TRANS_X_GAP = 0;

    public PileLayoutManager(Context context) {
        TRANS_X_GAP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics());
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.e(TAG, "onLayoutChildren() called with: recycler = [" + recycler + "], state = [" + state + "]");
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        if (itemCount < 1) {
            return;
        }
        //top-3View的position
        int bottomPosition;
        //边界处理
        if (itemCount < MAX_SHOW_COUNT) {
            bottomPosition = 0;
        } else {
            bottomPosition = itemCount - MAX_SHOW_COUNT;
        }

        for (int position = bottomPosition; position < itemCount; position++) {
            View view = recycler.getViewForPosition(position);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);

            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view));

            int level = itemCount - position - 1;
            if (level > 0) {
                view.setScaleY(1 - SCALE_GAP * level);
                if (level < MAX_SHOW_COUNT - 1) {
                    view.setTranslationY(TRANS_X_GAP * level);
                    view.setScaleY(1 - SCALE_GAP * level);
                } else {
                    view.setTranslationY(TRANS_X_GAP * (level - 1));
                    view.setScaleX(1 - SCALE_GAP * (level - 1));
                }
            }
        }
    }

}
