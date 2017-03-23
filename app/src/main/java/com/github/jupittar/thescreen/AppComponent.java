package com.github.jupittar.thescreen;


import com.github.jupittar.core.data.remote.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    AppModule.class,
    NetworkModule.class
})
public interface AppComponent {

}
