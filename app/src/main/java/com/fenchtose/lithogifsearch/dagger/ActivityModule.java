package com.fenchtose.lithogifsearch.dagger;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.facebook.litho.ComponentContext;
import com.fenchtose.lithogifsearch.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

	private MainActivity activity;

	public ActivityModule(MainActivity activity) {
		this.activity = activity;
	}

	@Provides
	public Context getContext() {
		return activity;
	}

	@Provides
	public ComponentContext getComponentContext() {
		return new ComponentContext(activity);
	}

	@Provides
	public RequestManager provideGlide() {
		return Glide.with(activity);
	}
}
