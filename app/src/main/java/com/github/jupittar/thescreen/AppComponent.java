package com.github.jupittar.thescreen;


import com.github.jupittar.core.data.remote.NetworkModule;
import com.github.jupittar.thescreen.helper.HelperModule;
import com.github.jupittar.thescreen.ui.main.MainActivity;
import com.github.jupittar.thescreen.ui.movies.AppMoviesModule;
import com.github.jupittar.thescreen.ui.movies.MoviesSubcomponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    AppModule.class,
    HelperModule.class,
    NetworkModule.class
})
public interface AppComponent {
  void inject(MainActivity activity);

  MoviesSubcomponent plus(AppMoviesModule module);
}
