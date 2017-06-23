package com.fenchtose.lithogifsearch.base.router;

import android.support.annotation.Nullable;

import com.facebook.litho.EventHandler;
import com.fenchtose.lithogifsearch.dagger.ActivityComponent;
import com.fenchtose.lithogifsearch.dagger.AppComponent;
import com.fenchtose.lithogifsearch.events.FavChangeEvent;
import com.fenchtose.lithogifsearch.features.full_screen.FullScreenPath;
import com.fenchtose.lithogifsearch.features.home.HomePath;
import com.fenchtose.lithogifsearch.models.GifItem;

public class Routes {

	private final AppComponent appComponent;
	private final ActivityComponent activityComponent;
	private final Router router;

	private static Routes instance;

	private Routes(AppComponent appComponent, ActivityComponent activityComponent, Router router) {
		this.appComponent = appComponent;
		this.activityComponent = activityComponent;
		this.router = router;
		instance = this;
	}

	public static Routes start(AppComponent appComponent, ActivityComponent activityComponent, Router router) {
		instance = new Routes(appComponent, activityComponent, router);
		return instance;
	}

	public static Routes get() {
		if (instance == null) {
			throw new IllegalArgumentException("Routes not initialized");
		}

		return instance;
	}

	public static void stop() {
		instance = null;
	}

	public void openHome() {
		router.go(new HomePath(appComponent, activityComponent));
	}

	public void openGif(@Nullable EventHandler<FavChangeEvent> likeHandler, GifItem gif, boolean isLiked) {
		router.go(new FullScreenPath(appComponent, activityComponent, likeHandler, gif, isLiked));
	}

}
