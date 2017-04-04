package com.github.jupittar.commlib.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class FontChangeableTabLayout extends TabLayout {

    private Typeface mTypeface;

    public FontChangeableTabLayout(Context context) {
        super(context);
    }

    public FontChangeableTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FontChangeableTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Should be called before {@link #setupWithViewPager}
     */
    public void setTypeface(Typeface typeface) {
        mTypeface = typeface;
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        if (mTypeface != null && viewPager != null) {
            this.removeAllTabs();

            ViewGroup slidingTabStrip = (ViewGroup) getChildAt(0);

            PagerAdapter adapter = viewPager.getAdapter();

            for (int i = 0, count = adapter.getCount(); i < count; i++) {
                Tab tab = this.newTab();
                this.addTab(tab.setText(adapter.getPageTitle(i)));
                AppCompatTextView view = (AppCompatTextView) ((ViewGroup) slidingTabStrip
                        .getChildAt(i)).getChildAt(1);
                view.setTypeface(mTypeface);
            }
        }
    }
}
