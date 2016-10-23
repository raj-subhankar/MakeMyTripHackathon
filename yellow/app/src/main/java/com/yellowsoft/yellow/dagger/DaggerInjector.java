package com.yellowsoft.yellow.dagger;

import com.yellowsoft.yellow.dagger.components.AppComponent;
import com.yellowsoft.yellow.dagger.components.DaggerAppComponent;
import com.yellowsoft.yellow.dagger.modules.AppModule;

/**
 * Created by subhankar on 10/11/2016.
 */

public class DaggerInjector {
    private static AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();

    public static AppComponent get() {
        return appComponent;
    }
}
