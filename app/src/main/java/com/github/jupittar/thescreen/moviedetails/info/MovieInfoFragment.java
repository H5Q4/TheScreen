package com.github.jupittar.thescreen.moviedetails.info;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.base.LazyFragment;
import com.github.jupittar.thescreen.util.TypefaceUtils;

import butterknife.BindView;


public class MovieInfoFragment
        extends LazyFragment {

    //region Constants
    private static final String ARG_PARAM_MOVIE_ID = "movie_id";
    //endregion

    //region Member Variables
    @BindView(R.id.tv_rating) TextView mRatingTv;
    @BindView(R.id.tv_overview) TextView mOverviewTv;
    @BindView(R.id.tv_runtime) TextView mRuntimeTv;
    @BindView(R.id.tv_genres) TextView mGenresTv;
    @BindView(R.id.tv_country) TextView mCountryTv;
    @BindView(R.id.tv_company) TextView mCompanyTv;
    @BindView(R.id.tv_language) TextView mLanguageTv;
    @BindView(R.id.tv_budget) TextView mBudgetTv;
    @BindView(R.id.tv_revenue) TextView mRevenueTv;

    private long mMovieId;
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
        mCountryTv.setTypeface(avenirNextRegularFont);
        mCompanyTv.setTypeface(avenirNextRegularFont);
        mLanguageTv.setTypeface(avenirNextRegularFont);
        mBudgetTv.setTypeface(avenirNextRegularFont);
        mRevenueTv.setTypeface(avenirNextRegularFont);
    }

    private void setRevenueText(int revenue) {
        if (revenue <= 0) return;
        String label = "Budget: ";
        String source = label + "$" + revenue;
        String s = setInfoWithSpannableString(source, label.length(), source.length() - 1);
        mRevenueTv.setText(s);
    }

    private void setBudgetText(int budget) {
        if (budget <= 0) return;
        String label = "Budget: ";
        String source = label + "$" + budget;
        String s = setInfoWithSpannableString(source, label.length(), source.length() - 1);
        mBudgetTv.setText(s);
    }

    private void setLanguageText(String languages) {
        String label = "Spoken languages: ";
        String source = label + languages;
        String s = setInfoWithSpannableString(source, label.length(), source.length() - 1);
        mLanguageTv.setText(s);
    }

    private void setCompanyText(String companies) {
        String label = "Production Companies: ";
        String source = label + companies;
        String s = setInfoWithSpannableString(source, label.length(), source.length() - 1);
        mCompanyTv.setText(s);
    }

    private void setCountryText(String countries) {
        String label = "Production Countries: ";
        String source = label + countries;
        String s = setInfoWithSpannableString(source, label.length(), source.length() - 1);
        mCountryTv.setText(s);
    }

    private String setInfoWithSpannableString(CharSequence source, int start, int end) {
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new StyleSpan(Typeface.ITALIC), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.grey_500)),
                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss.toString();
    }
    //endregion

    @Override
    protected void injectDependencies(Context context, AppComponent appComponent) {

    }

}
