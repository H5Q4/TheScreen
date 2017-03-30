package com.github.jupittar.thescreen.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.github.jupittar.commlib.custom.SCViewPager;
import com.github.jupittar.commlib.util.ToastUtils;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.base.BaseActivity;
import com.github.jupittar.thescreen.movies.MoviesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.support.v4.widget.DrawerLayout.STATE_SETTLING;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private static final String TAG = MainActivity.class.getCanonicalName();
  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.drawer_layout)
  DrawerLayout mDrawerLayout;
  @BindView(R.id.nav_view)
  NavigationView mNavigationView;
  @BindView(R.id.view_pager)
  SCViewPager mViewPager;

  private boolean mExit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTheme(R.style.AppTheme);
    setContentView(R.layout.activity_main);

    setUpToolbar();
    setUpDrawer();
    setUpViewPager();
  }

  @Override
  protected void injectDependencies(AppComponent appComponent) {
    appComponent.inject(this);
  }

  private void setUpViewPager() {
    List<Fragment> fragments = new ArrayList<>();
    fragments.add(0, MoviesFragment.newInstance());
    fragments.add(1, MoviesFragment.newInstance());
    ContentFragmentPageAdapter adapter = new ContentFragmentPageAdapter(
        getSupportFragmentManager(),
        fragments
    );
    mViewPager.setAdapter(adapter);
    mViewPager.setScrollEnabled(false);
    mViewPager.setOffscreenPageLimit(fragments.size());
  }


  private void setUpToolbar() {
    setSupportActionBar(mToolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setLogo(R.drawable.logo);
    }
  }

  private void setUpDrawer() {
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
        R.string.navigation_drawer_close);
    mDrawerLayout.addDrawerListener(toggle);
    mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
      @Override
      public void onDrawerStateChanged(int newState) {
        super.onDrawerStateChanged(newState);
        if (newState == STATE_SETTLING) {
          if (!mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            getWindow().setStatusBarColor(ContextCompat
                .getColor(MainActivity.this, android.R.color.transparent));
          } else {
            getWindow().setStatusBarColor(ContextCompat
                .getColor(MainActivity.this, R.color.colorPrimaryDark));
          }
        }
      }
    });
    toggle.syncState();
    mNavigationView.setNavigationItemSelectedListener(this);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    int position = 0;
    switch (id) {
      case R.id.nav_movie:
        position = 0;
        break;
      case R.id.nav_tv:
        position = 1;
        break;
    }
    mViewPager.setCurrentItem(position);
    mDrawerLayout.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  public void onBackPressed() {
    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
      mDrawerLayout.closeDrawer(GravityCompat.START);
    } else {
      if (mExit) {
        finish();
      } else {
        mExit = true;
        ToastUtils.showShort(MainActivity.this, "再按一次退出应用");
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            mExit = false;
          }
        }, 2000);
      }

    }
  }

  private class ContentFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    ContentFragmentPageAdapter(FragmentManager fm, @NonNull List<Fragment> fragments) {
      super(fm);
      this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
      return mFragments.get(position);
    }

    @Override
    public int getCount() {
      return mFragments.size();
    }
  }
}
