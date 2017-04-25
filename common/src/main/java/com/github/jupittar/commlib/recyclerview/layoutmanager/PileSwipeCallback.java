package com.github.jupittar.commlib.recyclerview.layoutmanager;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

import static com.github.jupittar.commlib.recyclerview.layoutmanager.PileLayoutManager.MAX_SHOW_COUNT;
import static com.github.jupittar.commlib.recyclerview.layoutmanager.PileLayoutManager.SCALE_GAP;
import static com.github.jupittar.commlib.recyclerview.layoutmanager.PileLayoutManager.TRANS_X_GAP;

public class PileSwipeCallback extends ItemTouchHelper.SimpleCallback {

    private RecyclerView mRecyclerView;
    private List mData;
    private RecyclerView.Adapter mAdapter;

    public PileSwipeCallback(int dragDirs, int swipeDirs, RecyclerView recyclerView, List data,
                             RecyclerView.Adapter adapter) {
        super(dragDirs, swipeDirs);
        mRecyclerView = recyclerView;
        mData = data;
        mAdapter = adapter;
    }

    public PileSwipeCallback(RecyclerView recyclerView, List data, RecyclerView.Adapter adapter) {
        this(0, ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                recyclerView, data, adapter);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getLayoutPosition();
        Object item = mData.remove(position);
        mData.add(0, item);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        double swipedValue = Math.sqrt(dX * dX + dY * dY);
        double fraction = swipedValue / getSwipeThreshold(viewHolder);
        if (fraction > 1) fraction = 1;
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            int childLevel = childCount - i - 1;
            if (childLevel > 0) {
                child.setScaleX((float) (1 - SCALE_GAP * childLevel + fraction * SCALE_GAP));

                if (childLevel < MAX_SHOW_COUNT - 1) {
                    child.setScaleY((float) (1 - SCALE_GAP * childLevel + fraction * SCALE_GAP));
                    child.setTranslationX((float) (TRANS_X_GAP * childLevel - fraction * TRANS_X_GAP));
                }
            }
        }
    }
}
