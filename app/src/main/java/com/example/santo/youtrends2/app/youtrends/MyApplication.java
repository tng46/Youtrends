package com.example.santo.youtrends2.app.youtrends;

import android.app.Application;
import android.content.Context;

import com.example.santo.youtrends2.app.database.TrendsDatabase;

/**
 * Created by Santo on 27/04/2015.
 */
public class MyApplication extends Application {
    public static final String API_KEY_YOUTUBE = "AIzaSyBqWRwL8FMunzo6yEG9qdfCADcsQGSILNE";//"AIzaSyBRawfs5MtHb-7QWluJ6LoNyCicKwwZJPk";

    private static MyApplication sInstance;

    private static TrendsDatabase mDatabase;

    public synchronized static TrendsDatabase getWritableDatabase(){
        if (mDatabase == null) {
            mDatabase = new TrendsDatabase(getAppContext());
        }
        return mDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mDatabase = new TrendsDatabase(this);
    }

    public static MyApplication getsInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }


}
