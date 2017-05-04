package com.github.jupittar.thescreen.screen.moviedetails.info;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.jupittar.commlib.custom.AspectRatioImageView;
import com.github.jupittar.commlib.recyclerview.CommonViewHolder;
import com.github.jupittar.commlib.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.data.remote.response.Movie;
import com.github.jupittar.thescreen.screen.base.LazyFragment;
import com.github.jupittar.thescreen.util.Constants;
import com.github.jupittar.thescreen.util.TypefaceUtils;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;


public class MovieInfoFragment
        extends LazyFragment
        implements MovieInfoContract.View {

    //region Constants
    private static final String ARG_PARAM_MOVIE_ID = "movie_id";
    //endregion

    //region Member Variables
    @BindView(R.id.content_movie_info) View mContentView;
    @BindView(R.id.tv_rating) TextView mRatingTv;
    @BindView(R.id.tv_overview) TextView mOverviewTv;
    @BindView(R.id.tv_runtime) TextView mRuntimeTv;
    @BindView(R.id.tv_genres) TextView mGenresTv;
    @BindView(R.id.tv_director) TextView mDirectorTv;
    @BindView(R.id.tv_company) TextView mCompanyTv;
    @BindView(R.id.tv_language) TextView mLanguageTv;
    @BindView(R.id.tv_budget) TextView mBudgetTv;
    @BindView(R.id.tv_revenue) TextView mRevenueTv;
    @BindView(R.id.pb_loading) ContentLoadingProgressBar mProgressBar;
    @BindView(R.id.rv_similar_movies) RecyclerView mSimilarMoviesRv;
    @BindView(R.id.tv_header_similar_movies) TextView mSimilarMoviesHeaderTv;

    private long mMovieId;
    @Inject MovieInfoContract.Presenter<MovieInfoContract.View> mPresenter;
    //endregion

    //region Constructors
    public MovieInfoFragment() {
        // Required empty public constructor
    }
    //endregion

    //region Factory Methods
    public static MovieInfoFragment newInstance(long movieId) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM_MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovieId = getArguments().getLong(ARG_PARAM_MOVIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_info, container, false);
    }

    @Override
    public void onFirstAppear() {
        initMemberVariables();
        mPresenter.showMovieInfo(mMovieId);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detach();
        mPresenter = null;
        super.onDestroyView();
    }
    //endregion

    //region Set Up Methods
    private void initMemberVariables() {
        Typeface exo2RegularFont = TypefaceUtils
                .getTypeface(TypefaceUtils.FONT_EXO2_REGULAR, getActivity());
        mRatingTv.setTypeface(exo2RegularFont);
        mRuntimeTv.setTypeface(exo2RegularFont);
        mGenresTv.setTypeface(exo2RegularFont);
        mOverviewTv.setTypeface(exo2RegularFont);
        mDirectorTv.setTypeface(exo2RegularFont);
        mCompanyTv.setTypeface(exo2RegularFont);
        mLanguageTv.setTypeface(exo2RegularFont);
        mBudgetTv.setTypeface(exo2RegularFont);
        mRevenueTv.setTypeface(exo2RegularFont);
        mSimilarMoviesHeaderTv.setTypeface(exo2RegularFont);
    }

    private CharSequence setInfoWithSpannableString(CharSequence source, int start, int end) {
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.grey_500)),
                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
    //endregion

    @Override
    protected void injectDependencies(Context context, AppComponent appComponent) {
        appComponent.plus(new MovieInfoModule(this)).inject(this);
    }

    //region Implementation of MovieInfoContract.View
    @Override
    public void showOverviewText(String overview) {
        mOverviewTv.setText(overview);
    }

    @Override
    public void showRevenueText(int revenue) {
        if (revenue <= 0) return;
        String label = "Budget: ";
        String source = label + "$" + revenue;
        CharSequence s = setInfoWithSpannableString(source, label.length(), source.length());
        mRevenueTv.setText(s);
    }

    @Override
    public void showBudgetText(int budget) {
        if (budget <= 0) return;
        String label = "Budget: ";
        String source = label + "$" + budget;
        CharSequence s = setInfoWithSpannableString(source, label.length(), source.length());
        mBudgetTv.setText(s);
    }

    @Override
    public void showLanguageText(String languages) {
        String label = "Spoken Languages: ";
        String source = label + languages;
        CharSequence s = setInfoWithSpannableString(source, label.length(), source.length());
        mLanguageTv.setText(s);
    }

    @Override
    public void showCompanyText(String companies) {
        String label = "Production Companies: ";
        String source = label + companies;
        CharSequence s = setInfoWithSpannableString(source, label.length(), source.length());
        mCompanyTv.setText(s);
    }

    @Override
    public void showDirectorText(String director) {
        String label = "Directed By: ";
        String source = label + director;
        CharSequence s = setInfoWithSpannableString(source, label.length(), source.length());
        mDirectorTv.setText(s);
    }

    @Override
    public void showSimilarMovies(List<Movie> similarMovies) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        mSimilarMoviesRv.setLayoutManager(layoutManager);
        mSimilarMoviesRv.setHasFixedSize(true);
        SimilarMoviesAdapter adapter = new SimilarMoviesAdapter(getContext(),
                R.layout.item_similar_movie);
        adapter.addAll(similarMovies);
        mSimilarMoviesRv.setAdapter(adapter);
    }

    @Override
    public void showRating(double voteAverage) {
        mRatingTv.setText(String.valueOf(voteAverage));
    }

    @Override
    public void showDuration(int runtime) {
        if (runtime > 0) {
            int hours = runtime / 60;
            int minutes = runtime % 60;
            if (hours > 0) {
                if (minutes > 0) {
                    mRuntimeTv.setText(String.format(Locale.ENGLISH, "%dh %dm", hours, minutes));
                } else {
                    mRuntimeTv.setText(String.format(Locale.ENGLISH, "%dh", hours));
                }
            } else {
                mRuntimeTv.setText(String.format(Locale.ENGLISH, "%dm", minutes));
            }
        } else {
            mRuntimeTv.setText("N/A");
        }
    }

    @Override
    public void showGenreText(String s) {
        mGenresTv.setText(s);
    }

    @Override
    public void showContentView() {
        mContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        mProgressBar.show();
    }

    @Override
    public void hideLoading() {
        mProgressBar.hide();
    }

    @Override
    public void showErrorMessage(Throwable throwable) {
        Toasty.error(getActivity(), throwable.getMessage()).show();
    }
    //endregion

    //region Adapters
    private class SimilarMoviesAdapter extends CommonViewAdapter<Movie> {

        public SimilarMoviesAdapter(Context context, @LayoutRes int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void convertView(CommonViewHolder holder, Movie item) {
            AspectRatioImageView posterIv = holder.getView(R.id.iv_movie_poster);
            posterIv.setAspectRatio(4.0D / 3.0D);
            TextView releaseYearTv = holder.getView(R.id.tv_release_year);
            releaseYearTv.setTypeface(TypefaceUtils.getTypeface(TypefaceUtils.FONT_EXO2_REGULAR, mContext));
            releaseYearTv.setText(item.getReleaseDate().substring(0, 4));
            String posterPath = String.format("%s%s%s",
                    Constants.IMAGE_BASE_URL,
                    Constants.IMAGE_SIZE_W500,
                    item.getPosterPath());
            Glide.with(mContext)
                    .load(posterPath)
                    .asBitmap()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new BitmapImageViewTarget(posterIv) {
                        @Override
                        public void onResourceReady(Bitmap resource,
                                                    GlideAnimation<? super Bitmap> glideAnimation) {
                            super.onResourceReady(resource, glideAnimation);
                            Palette.from(resource).generate(p -> {
                                int darkMutedColor = p.getDarkMutedColor(
                                        ContextCompat.getColor(mContext,
                                                R.color.colorPrimary));
                                releaseYearTv.setBackgroundColor(darkMutedColor);
                                int lightVibrantColor = p.getLightVibrantColor(
                                        ContextCompat.getColor(mContext,
                                                android.R.color.white));
                                releaseYearTv.setTextColor(lightVibrantColor);
                            });
                        }
                    });
        }
    }
    //endregion
}
