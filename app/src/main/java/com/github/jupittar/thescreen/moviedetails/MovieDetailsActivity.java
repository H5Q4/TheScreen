package com.github.jupittar.thescreen.moviedetails;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.jupittar.commlib.custom.FontChangeableTabLayout;
import com.github.jupittar.commlib.custom.SCViewPager;
import com.github.jupittar.commlib.util.CommonPagerAdapter;
import com.github.jupittar.core.data.model.Movie;
import com.github.jupittar.core.moviedetails.MovieDetailsContract;
import com.github.jupittar.core.moviedetails.MovieDetailsModule;
import com.github.jupittar.core.util.Constants;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.base.BaseActivity;
import com.github.jupittar.thescreen.moviedetails.info.MovieInfoFragment;
import com.github.jupittar.thescreen.util.TypefaceUtils;

import java.util.List;

import butterknife.BindView;

public class MovieDetailsActivity
        extends BaseActivity
        implements MovieDetailsContract.View {

    public static final String KEY_MOVIE = "movie";

    @BindView(R.id.section_parallax) View mParallaxSection;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.appbar_layout) AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.iv_poster) ImageView mPosterIv;
    @BindView(R.id.vp_backdrop) ImageView mBackdropIv;
    @BindView(R.id.tab_layout) FontChangeableTabLayout mTabLayout;
    @BindView(R.id.view_pager) SCViewPager mViewPager;
    @BindView(R.id.tv_title) TextView mTitleTv;
    @BindView(R.id.tv_release_data) TextView mReleaseDateTv;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getWindow().setStatusBarColor(ContextCompat
                .getColor(getApplicationContext(), android.R.color.transparent));
        supportPostponeEnterTransition();
        initMemberVariables();
        setUpToolbar();
        setUpViewPager();
        setUpTabLayout();
    }

    private void initMemberVariables() {
        mTitleTv.setTypeface(TypefaceUtils.getTypeface(
                TypefaceUtils.FONT_AVENIR_NEXT_LT_PRO_REGULAR,
                getApplicationContext()));
        mReleaseDateTv.setTypeface(TypefaceUtils.getTypeface(
                TypefaceUtils.FONT_AVENIR_NEXT_LT_PRO_IT,
                getApplicationContext()));
        mReleaseDateTv.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        mMovie = (Movie) getIntent().getSerializableExtra(KEY_MOVIE);
        if (mMovie != null) {
            String posterPath = String.format("%s%s%s",
                    Constants.IMAGE_BASE_URL,
                    Constants.IMAGE_SIZE_W500,
                    mMovie.getPosterPath());
            Glide.with(getApplicationContext())
                    .load(posterPath)
                    .asBitmap()
                    .centerCrop()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new BitmapImageViewTarget(mPosterIv) {
                        @Override
                        public void onResourceReady(Bitmap resource,
                                                    GlideAnimation<? super Bitmap> glideAnimation) {
                            super.onResourceReady(resource, glideAnimation);
                            supportStartPostponedEnterTransition();
                            Palette.from(resource).generate(p -> {
                                int darkMutedColor = p.getDarkMutedColor(
                                        ContextCompat.getColor(getApplicationContext(),
                                                R.color.colorPrimary));
                                mParallaxSection.setBackgroundColor(darkMutedColor);
                                int lightVibrantColor = p.getLightVibrantColor(
                                        ContextCompat.getColor(getApplicationContext(),
                                                android.R.color.white));
                                mTitleTv.setTextColor(lightVibrantColor);
                                mReleaseDateTv.setTextColor(lightVibrantColor);
                            });
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            supportStartPostponedEnterTransition();
                        }
                    });
            String backdropPath = String.format("%s%s%s",
                    Constants.IMAGE_BASE_URL,
                    Constants.IMAGE_SIZE_W780,
                    mMovie.getBackdropPath());
            Glide.with(getApplicationContext())
                    .load(backdropPath)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mBackdropIv);
            mTitleTv.setText(mMovie.getOriginalTitle());
            mReleaseDateTv.setText(mMovie.getReleaseDate());
        }
    }

    private void setUpTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTypeface(TypefaceUtils.getTypeface(TypefaceUtils.FONT_LOBSTER_REGULAR, this));
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
                    mCollapsingToolbarLayout.setTitle(mMovie.getOriginalTitle());
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
        appComponent.plus(new MovieDetailsModule(this)).inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //region Implementation of MovieDetailsContract.View
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void showImages(List<String> urls) {

    }
    //endregion
}
