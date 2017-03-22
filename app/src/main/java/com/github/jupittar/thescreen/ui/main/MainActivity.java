package com.github.jupittar.thescreen.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.ui.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.drawer_layout)
  DrawerLayout mDrawerLayout;
  @BindView(R.id.nav_view)
  NavigationView mNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTheme(R.style.AppTheme_MainTheme);
    setContentView(R.layout.activity_main);

    setUpToolbar();
    setUpDrawer();
  }

  private void setUpToolbar() {
    setSupportActionBar(mToolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(false);
      getSupportActionBar().setLogo(R.drawable.logo);
    }
  }

  private void setUpDrawer() {
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
        R.string.navigation_drawer_close);
    mDrawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    mNavigationView.setNavigationItemSelectedListener(this);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    return false;
  }
}
