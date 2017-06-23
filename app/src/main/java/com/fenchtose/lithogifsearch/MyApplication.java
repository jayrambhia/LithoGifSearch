package com.fenchtose.lithogifsearch;

import android.app.Application;

import com.facebook.soloader.SoLoader;
import com.fenchtose.lithogifsearch.dagger.AppComponent;
import com.fenchtose.lithogifsearch.dagger.AppModule;
import com.fenchtose.lithogifsearch.dagger.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;


public class MyApplication extends Application {

	private AppComponent appComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		if (LeakCanary.isInAnalyzerProcess(this)) {
			// This process is dedicated to LeakCanary for heap analysis.
			// You should not init your app in this process.
			return;
		}

		LeakCanary.install(this);
		SoLoader.init(this, false);

		appComponent = DaggerAppComponent.builder()
				.appModule(new AppModule(this))
				.build();

	}

	public AppComponent getAppComponent() {
		return appComponent;
	}
}
