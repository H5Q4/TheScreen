package com.github.jupittar.thescreen.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.github.jupittar.commlib.custom.CustomTypefaceSpan;
import com.github.jupittar.commlib.custom.SCViewPager;
import com.github.jupittar.commlib.util.CommonPagerAdapter;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.base.BaseActivity;
import com.github.jupittar.thescreen.movies.MoviesFragment;
import com.github.jupittar.thescreen.util.TypefaceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

import static android.support.v4.widget.DrawerLayout.STATE_SETTLING;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) NavigationView mNavigationView;
    @BindView(R.id.view_pager) SCViewPager mViewPager;

    private boolean mExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        setUpToolbar();
        setUpDrawer();
        setUpNavigationView();
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
        CommonPagerAdapter adapter = new CommonPagerAdapter(
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
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setLogo(R.drawable.logo);

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
    }

    private void setUpNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        Menu m = mNavigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for applying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            applyFontToMenuItem(mi);
        }
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
                Toasty.info(MainActivity.this, "再按一次退出应用").show();
                new Handler().postDelayed(() -> mExit = false, 2000);
            }

        }
    }

    private void applyFontToMenuItem(MenuItem item) {
        Typeface font = TypefaceUtils.getTypeface(TypefaceUtils.FONT_AVENIR_NEXT_LT_PRO_REGULAR, this);
        SpannableString mNewTitle = new SpannableString(item.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        item.setTitle(mNewTitle);
    }

}
