package com.github.jupittar.thescreen.screen.moviedetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.jupittar.commlib.custom.FontChangeableTabLayout;
import com.github.jupittar.commlib.custom.SCViewPager;
import com.github.jupittar.commlib.recyclerview.CommonViewHolder;
import com.github.jupittar.commlib.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.commlib.recyclerview.layoutmanager.PileLayoutManager;
import com.github.jupittar.commlib.recyclerview.layoutmanager.PileSwipeCallback;
import com.github.jupittar.commlib.util.CommonPagerAdapter;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.data.model.Movie;
import com.github.jupittar.thescreen.screen.base.BaseActivity;
import com.github.jupittar.thescreen.screen.moviedetails.info.MovieInfoFragment;
import com.github.jupittar.thescreen.util.Constants;
import com.github.jupittar.thescreen.util.TypefaceUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class MovieDetailsActivity
        extends BaseActivity
        implements MovieDetailsContract.View {

    //region Constants
    public static final String KEY_MOVIE = "movie";
    //endregion

    //region Member Variables
    @BindView(R.id.section_parallax) View mParallaxSection;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.appbar_layout) AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.iv_poster) ImageView mPosterIv;
    @BindView(R.id.rv_backdrop) RecyclerView mBackdropRv;
    @BindView(R.id.tab_layout) FontChangeableTabLayout mTabLayout;
    @BindView(R.id.view_pager) SCViewPager mViewPager;
    @BindView(R.id.tv_title) TextView mTitleTv;
    @BindView(R.id.tv_release_data) TextView mReleaseDateTv;
    @BindView(R.id.pb_backdrop) ProgressBar mBackdropPb;

    @Inject MovieDetailsContract.Presenter<MovieDetailsContract.View> mPresenter;

    private Movie mMovie;
    private BackdropViewAdapter mBackdropViewAdapter;
    //endregion

    //region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getWindow().setStatusBarColor(ContextCompat
                .getColor(getApplicationContext(), android.R.color.transparent));
        supportPostponeEnterTransition();
        initMemberVariables();
        setUpToolbar();
        setUpBackdropRecyclerView();
        setUpViewPager();
        setUpTabLayout();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
        mPresenter = null;
    }
    //endregion

    //region Set Up Methods
    private void setUpBackdropRecyclerView() {
        PileLayoutManager layoutManager = new PileLayoutManager(this);
        mBackdropRv.setLayoutManager(layoutManager);
        mBackdropViewAdapter = new BackdropViewAdapter(this, R.layout.item_movie_backdrop);
        mBackdropRv.setHasFixedSize(true);
        mBackdropRv.setAdapter(mBackdropViewAdapter);
        PileSwipeCallback pileSwipeCallback = new PileSwipeCallback(mBackdropRv,
                mBackdropViewAdapter.getItemList(),
                mBackdropViewAdapter);
        new ItemTouchHelper(pileSwipeCallback).attachToRecyclerView(mBackdropRv);

        if (mMovie != null) {
            mPresenter.showImages(mMovie.getId());
        }
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
        adapter.addPage(MovieInfoFragment.newInstance(mMovie.getId()), "Info");
        adapter.addPage(MovieInfoFragment.newInstance(mMovie.getId()), "Cast");
        adapter.addPage(MovieInfoFragment.newInstance(mMovie.getId()), "Reviews");
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
    //endregion

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
        mBackdropPb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mBackdropPb.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage(Throwable throwable) {
        Toasty.error(this, throwable.getMessage()).show();
    }

    @Override
    public void showImages(List<String> urls) {
        mBackdropViewAdapter.addAll(urls);
    }
    //endregion

    //region Adapters
    private class BackdropViewAdapter extends CommonViewAdapter<String> {

        public BackdropViewAdapter(Context context, @LayoutRes int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void convertView(CommonViewHolder holder, String url) {
            ImageView backdropIv = holder.getView(R.id.iv_movie_backdrop);
            Glide.with(mContext)
                    .load(url)
                    .centerCrop()
                    .into(backdropIv);
        }
    }
    //endregion
}
