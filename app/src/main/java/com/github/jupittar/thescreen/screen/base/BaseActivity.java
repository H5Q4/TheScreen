package com.github.jupittar.thescreen.screen.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.github.jupittar.commlib.rxbus.RxBus;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.TheScreenApp;
import com.github.jupittar.thescreen.util.Constants;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(TheScreenApp.getAppComponent());
        RxBus.getDefault().subscribe(Constants.EVENT_TAG_REGION_CHANGED, this, busEvent -> {
            recreate();
        });
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        RxBus.getDefault().unregister(this);
        mUnbinder.unbind();
        super.onDestroy();
    }

    protected abstract void injectDependencies(AppComponent appComponent);
}
