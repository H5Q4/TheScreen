package com.github.jupittar.thescreen;


import com.github.jupittar.core.data.remote.NetworkModule;
import com.github.jupittar.core.moviedetails.MovieDetailsModule;
import com.github.jupittar.core.moviedetails.info.MovieInfoModule;
import com.github.jupittar.core.movies.MoviesModule;
import com.github.jupittar.thescreen.data.LocalDataModule;
import com.github.jupittar.thescreen.helper.HelperModule;
import com.github.jupittar.thescreen.main.MainActivity;
import com.github.jupittar.thescreen.moviedetails.MovieDetailsSubcomponent;
import com.github.jupittar.thescreen.moviedetails.info.MovieInfoSubcomponent;
import com.github.jupittar.thescreen.movies.MoviesSubcomponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        HelperModule.class,
        NetworkModule.class,
        LocalDataModule.class
})
public interface AppComponent {
    void inject(MainActivity activity);

    MoviesSubcomponent plus(MoviesModule module);

    MovieDetailsSubcomponent plus(MovieDetailsModule module);

    MovieInfoSubcomponent plus(MovieInfoModule module);
}
