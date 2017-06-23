package com.fenchtose.lithogifsearch.dagger;

import com.fenchtose.lithogifsearch.MyApplication;
import com.fenchtose.lithogifsearch.models.api.GifProvider;
import com.fenchtose.lithogifsearch.models.db.LikeStore;
import com.fenchtose.lithogifsearch.models.db.PreferenceLikeStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

	private MyApplication app;

	public AppModule(MyApplication app) {
		this.app = app;
	}

	@Provides
	@Singleton
	public MyApplication provideApp() {
		return app;
	}

	@Provides
	@Singleton
	public LikeStore provideLikeStore() {
		return new PreferenceLikeStore(app);
	}

	@Provides
	@Singleton
	public GifProvider providerGifProvider(LikeStore store) {
		return new GifProvider(store);
	}
}
