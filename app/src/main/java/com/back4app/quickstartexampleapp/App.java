package com.back4app.quickstartexampleapp;

import com.parse.Parse;
import android.app.Application;


public class App extends Application {

    ParseServerData parseServerData = new ParseServerData(); /* Need to create class of this exact name and declare methods. */

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(parseServerData.getId())
                // if defined
                .clientKey(parseServerData.getKey())
                .server(parseServerData.getUrl())
                .build()

        );
    }
}