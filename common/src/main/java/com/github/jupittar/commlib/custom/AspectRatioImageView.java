package com.github.jupittar.commlib.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class AspectRatioImageView extends AppCompatImageView {

    public static final double SQUARE = 1.0D;
    public static final double PHI = 2 / (Math.sqrt(5) + 1);

    private double mAspectRatio;

    public AspectRatioImageView(Context context) {
        super(context);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public double getAspectRatio() {
        return mAspectRatio;
    }

    public void setAspectRatio(double ratio) {
        if (ratio != mAspectRatio) {
            mAspectRatio = ratio;
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mAspectRatio > 0.0D) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            if (width != 0 && height == 0) {
                height = (int) (width * mAspectRatio);
            } else if (width == 0 && height != 0) {
                width = (int) (height / mAspectRatio);
            }
            super.onMeasure(
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            );
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
