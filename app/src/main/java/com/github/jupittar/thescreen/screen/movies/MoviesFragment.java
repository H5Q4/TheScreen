package com.github.jupittar.thescreen.screen.movies;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.custom.FontChangeableTabLayout;
import com.github.jupittar.commlib.custom.SCViewPager;
import com.github.jupittar.commlib.util.CommonPagerAdapter;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.screen.base.LazyFragment;
import com.github.jupittar.thescreen.util.TypefaceUtils;

import butterknife.BindView;

public class MoviesFragment extends LazyFragment {

    @BindView(R.id.tab_layout)
    FontChangeableTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    SCViewPager mViewPager;


    public MoviesFragment() {
        // Required empty public constructor
    }

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onFirstAppear() {
        setUpViewPager();
        setUpTabLayout();
    }

    private void setUpTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTypeface(TypefaceUtils.getTypeface(TypefaceUtils.FONT_LOBSTER_REGULAR,
                getActivity()));
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager() {
        CommonPagerAdapter adapter = new CommonPagerAdapter(getChildFragmentManager());
        adapter.addPage(MoviesByTabFragment.newInstance(MovieTab.NOW_PLAYING), "Now Playing");
        adapter.addPage(MoviesByTabFragment.newInstance(MovieTab.POPULAR), "Popular");
        adapter.addPage(MoviesByTabFragment.newInstance(MovieTab.TOP_RATED), "Top Rated");
        adapter.addPage(MoviesByTabFragment.newInstance(MovieTab.UPCOMING), "Upcoming");
        mViewPager.setAdapter(adapter);
        mViewPager.setScrollEnabled(true);
        mViewPager.setOffscreenPageLimit(adapter.getCount());
    }

    @Override
    protected void injectDependencies(Context context, AppComponent appComponent) {

    }

}
