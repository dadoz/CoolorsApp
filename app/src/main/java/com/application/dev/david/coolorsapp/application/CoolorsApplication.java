package com.application.dev.david.coolorsapp.application;

import android.app.Application;

import io.realm.Realm;

public class CoolorsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
