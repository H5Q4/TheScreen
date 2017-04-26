package com.github.jupittar.commlib.recyclerview.layoutmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;


public class PileLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = PileLayoutManager.class.getSimpleName();

    public static final int MAX_SHOW_COUNT = 4;
    public static final float SCALE_GAP = 0.05f;
    public static int TRANS_GAP;

    public PileLayoutManager(Context context) {
        TRANS_GAP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, context.getResources().getDisplayMetrics());
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
        int bottomPosition;

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

            layoutDecoratedWithMargins(view, widthSpace / 2, 0,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    getDecoratedMeasuredHeight(view) - TRANS_GAP * (MAX_SHOW_COUNT - 2));

            int level = itemCount - position - 1;
            if (level > 0 /*&& level < mShowCount - 1*/) {
                //每一层都需要X方向的缩小
                view.setScaleX(1 - SCALE_GAP * level);
                //前N层，依次向下位移和Y方向的缩小
                if (level < MAX_SHOW_COUNT - 1) {
                    view.setTranslationY(TRANS_GAP * level);
                    view.setScaleY(1 - SCALE_GAP * level);
                } else {//第N层在 向下位移和Y方向的缩小的成都与 N-1层保持一致
                    view.setTranslationY(TRANS_GAP * (level - 1));
                    view.setScaleY(1 - SCALE_GAP * (level - 1));
                }
            }
        }
    }

}
