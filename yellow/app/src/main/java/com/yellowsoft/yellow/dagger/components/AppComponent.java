package com.yellowsoft.yellow.dagger.components;

import com.yellowsoft.yellow.dagger.modules.AppModule;
import com.yellowsoft.yellow.ui.activities.PostsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by subhankar on 10/11/2016.
 */

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(PostsActivity activity);
}
