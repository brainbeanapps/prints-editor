package com.brainbeanapps.rosty.printseditordemo;

import android.app.Application;

import com.brainbeanapps.rosty.printseditordemo.utile.FileManager;

/**
 * Created by rosty on 7/7/2015.
 */
public class App extends Application {

    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        
        this.app = this;

        //Create application directory on app start if it was not created earlier
        FileManager.createAppDirectory();
    }

    public static App getInstance(){
        return app;
    }
}
