package com.github.jupittar.thescreen.moviedetails;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.github.jupittar.commlib.custom.SCViewPager;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.base.BaseActivity;

import butterknife.BindView;

public class MovieDetailsActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.iv_poster) ImageView mPosterIv;
    @BindView(R.id.vp_backdrop) ImageView mBackdropIv;
    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    @BindView(R.id.view_pager) SCViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
    }

    @Override
    protected void injectDependencies(AppComponent appComponent) {

    }
}
