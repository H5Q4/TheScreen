package com.github.jupittar.thescreen;


import com.github.jupittar.thescreen.data.local.LocalDataModule;
import com.github.jupittar.thescreen.data.remote.NetworkModule;
import com.github.jupittar.thescreen.helper.HelperModule;
import com.github.jupittar.thescreen.screen.main.MainActivity;
import com.github.jupittar.thescreen.screen.moviedetails.MovieDetailsModule;
import com.github.jupittar.thescreen.screen.moviedetails.MovieDetailsSubcomponent;
import com.github.jupittar.thescreen.screen.moviedetails.cast.MovieCastModule;
import com.github.jupittar.thescreen.screen.moviedetails.cast.MovieCastSubcomponent;
import com.github.jupittar.thescreen.screen.moviedetails.info.MovieInfoModule;
import com.github.jupittar.thescreen.screen.moviedetails.info.MovieInfoSubcomponent;
import com.github.jupittar.thescreen.screen.movies.MoviesModule;
import com.github.jupittar.thescreen.screen.movies.MoviesSubcomponent;

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

    MovieCastSubcomponent plus(MovieCastModule module);
}
