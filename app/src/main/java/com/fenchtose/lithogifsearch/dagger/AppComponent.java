package com.fenchtose.lithogifsearch.dagger;

import com.fenchtose.lithogifsearch.models.api.GifProvider;
import com.fenchtose.lithogifsearch.models.db.LikeStore;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
	LikeStore likeStore();
	GifProvider gifProvider();
}
