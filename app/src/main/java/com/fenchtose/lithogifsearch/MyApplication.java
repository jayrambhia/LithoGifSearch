package com.fenchtose.lithogifsearch;

import android.app.Application;

import com.facebook.soloader.SoLoader;


public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		SoLoader.init(this, false);
	}
}
