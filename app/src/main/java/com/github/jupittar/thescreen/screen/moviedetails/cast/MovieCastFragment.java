package com.github.jupittar.thescreen.screen.moviedetails.cast;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.screen.base.LazyFragment;


public class MovieCastFragment extends LazyFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_cast, container, false);
    }

    @Override
    protected void injectDependencies(Context context, AppComponent appComponent) {

    }

    @Override
    public void onFirstAppear() {

    }
}
