package com.github.jupittar.thescreen.ui.movie;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.custom.SCViewPager;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.ui.base.LazyFragment;
import com.github.jupittar.thescreen.ui.popularmovies.PopularMoviesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MoviesFragment extends LazyFragment {

  @BindView(R.id.tab_layout)
  TabLayout mTabLayout;
  @BindView(R.id.view_pager)
  SCViewPager mViewPager;


  public MoviesFragment() {
    // Required empty public constructor
  }

  public static MoviesFragment newInstance() {
    MoviesFragment fragment = new MoviesFragment();
    return fragment;
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
    mTabLayout.setupWithViewPager(mViewPager);
  }

  private void setUpViewPager() {
    MovieTabPagerAdapter adapter = new MovieTabPagerAdapter(getChildFragmentManager());
    adapter.addPager(PopularMoviesFragment.newInstance(), "NOW PLAYING");
    adapter.addPager(PopularMoviesFragment.newInstance(), "POPULAR");
    adapter.addPager(PopularMoviesFragment.newInstance(), "TOP RATED");
    adapter.addPager(PopularMoviesFragment.newInstance(), "UPCOMING");
    mViewPager.setAdapter(adapter);
    mViewPager.setScrollEnabled(true);
    mViewPager.setOffscreenPageLimit(adapter.getCount());
  }

  @Override
  protected void injectDependencies(AppComponent appComponent) {

  }

  private class MovieTabPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;

    MovieTabPagerAdapter(FragmentManager fm) {
      super(fm);
      mFragments = new ArrayList<>();
      mTitles = new ArrayList<>();
    }

    void addPager(Fragment fragment, String title) {
      mFragments.add(fragment);
      mTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
      return mFragments.get(position);
    }

    @Override
    public int getCount() {
      return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return mTitles.get(position);
    }
  }

}
