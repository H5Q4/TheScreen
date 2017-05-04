package com.github.jupittar.thescreen.screen.moviedetails.info;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jupittar.commlib.custom.CustomTypefaceSpan;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.data.remote.response.Movie;
import com.github.jupittar.thescreen.screen.base.LazyFragment;
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
        Typeface avenirNextRegularFont = TypefaceUtils
                .getTypeface(TypefaceUtils.FONT_AVENIR_NEXT_LT_PRO_REGULAR, getActivity());
        Typeface avenirNextItFont = TypefaceUtils
                .getTypeface(TypefaceUtils.FONT_AVENIR_NEXT_LT_PRO_IT, getActivity());
        mRatingTv.setTypeface(avenirNextRegularFont);
        mRuntimeTv.setTypeface(avenirNextItFont);
        mGenresTv.setTypeface(avenirNextItFont);
        mOverviewTv.setTypeface(avenirNextRegularFont);
        mDirectorTv.setTypeface(avenirNextRegularFont);
        mCompanyTv.setTypeface(avenirNextRegularFont);
        mLanguageTv.setTypeface(avenirNextRegularFont);
        mBudgetTv.setTypeface(avenirNextRegularFont);
        mRevenueTv.setTypeface(avenirNextRegularFont);
    }

    private CharSequence setInfoWithSpannableString(CharSequence source, int start, int end) {
        SpannableString ss = new SpannableString(source);
        Typeface avenirNextItFont = TypefaceUtils
                .getTypeface(TypefaceUtils.FONT_AVENIR_NEXT_LT_PRO_IT, getActivity());
        ss.setSpan(new CustomTypefaceSpan("", avenirNextItFont), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
}
