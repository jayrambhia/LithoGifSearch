package com.fenchtose.lithogifsearch.dagger;

import com.fenchtose.lithogifsearch.MyApplication;
import com.fenchtose.lithogifsearch.models.api.GifApi;
import com.fenchtose.lithogifsearch.models.api.GifProvider;
import com.fenchtose.lithogifsearch.models.db.LikeStore;
import com.fenchtose.lithogifsearch.models.db.PreferenceLikeStore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
	public Retrofit retrofit() {
		return new Retrofit.Builder()
				.baseUrl(GifApi.ENDPOINT)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
	}

	@Provides
	@Singleton
	public GifApi gifApi(Retrofit retrofit) {
		return retrofit.create(GifApi.class);
	}

	@Provides
	@Singleton
	public Gson gson() {
		return new GsonBuilder().create();
	}

	@Provides
	@Singleton
	public GifProvider providerGifProvider(GifApi gifApi, LikeStore store, Gson gson) {
		return new GifProvider(gifApi, store, gson);
	}
}
