package com.yellowsoft.yellow.dagger.modules;

/**
 * Created by subhankar on 10/11/2016.
 */

import com.yellowsoft.yellow.model.PostsAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    PostsAPI providePostsApi() {
        return new PostsAPI();
    }

}
