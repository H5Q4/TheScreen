package com.github.jupittar.thescreen.moviedetails;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.github.jupittar.commlib.custom.SCViewPager;
import com.github.jupittar.commlib.util.CommonPagerAdapter;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.base.BaseActivity;
import com.github.jupittar.thescreen.moviedetails.info.MovieInfoFragment;
import com.github.jupittar.thescreen.util.TypefaceUtils;

import butterknife.BindView;

public class MovieDetailsActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.appbar_layout) AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.iv_poster) ImageView mPosterIv;
    @BindView(R.id.vp_backdrop) ImageView mBackdropIv;
    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    @BindView(R.id.view_pager) SCViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        setUpToolbar();
        setUpViewPager();
        setUpTabLayout();
    }

    private void setUpTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setUpViewPager() {
        CommonPagerAdapter adapter = new CommonPagerAdapter(getSupportFragmentManager());
        adapter.addPage(MovieInfoFragment.newInstance("", ""), "Info");
        adapter.addPage(MovieInfoFragment.newInstance("", ""), "Cast");
        adapter.addPage(MovieInfoFragment.newInstance("", ""), "Reviews");
        mViewPager.setAdapter(adapter);
        mViewPager.setScrollEnabled(true);
        mViewPager.setOffscreenPageLimit(adapter.getCount());
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(
                TypefaceUtils.getTypeface(TypefaceUtils.FONT_AVENIR_NEXT_LT_PRO_REGULAR,
                        getApplicationContext()));
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this,
                android.R.color.transparent));
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mCollapsingToolbarLayout.setTitle("This is title");
                    isShow = true;
                } else if (isShow) {
                    mCollapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    protected void injectDependencies(AppComponent appComponent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
