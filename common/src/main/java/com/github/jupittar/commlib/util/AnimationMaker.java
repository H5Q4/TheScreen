package com.github.jupittar.commlib.util;


import android.animation.Animator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

public class AnimationMaker {

    public static final int REVEAL_FROM_CENTER = 0;
    public static final int REVEAL_FROM_TOP = 1;
    public static final int REVEAL_FROM_BOTTOM = 2;
    public static final int REVEAL_FROM_LEFT = 3;
    public static final int REVEAL_FROM_RIGHT = 4;


    public static void makeReveal(View viewRoot, int from) {
        int cx;
        int cy;
        switch (from) {
            case REVEAL_FROM_CENTER:
                cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
                cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
                break;
            case REVEAL_FROM_LEFT:
                cx = viewRoot.getLeft();
                cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
                break;
            case REVEAL_FROM_TOP:
                cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
                cy = viewRoot.getTop();
                break;
            case REVEAL_FROM_RIGHT:
                cx = viewRoot.getRight();
                cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
                break;
            case REVEAL_FROM_BOTTOM:
                cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
                cy = viewRoot.getBottom();
                break;
            default:
                cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
                cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
                break;
        }
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);
        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.start();
    }
}
