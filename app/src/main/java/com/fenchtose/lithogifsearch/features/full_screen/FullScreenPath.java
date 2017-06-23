package com.fenchtose.lithogifsearch.features.full_screen;

import android.support.annotation.Nullable;

import com.facebook.litho.EventHandler;
import com.fenchtose.lithogifsearch.base.AppPath;
import com.fenchtose.lithogifsearch.dagger.ActivityComponent;
import com.fenchtose.lithogifsearch.dagger.AppComponent;
import com.fenchtose.lithogifsearch.events.FavChangeEvent;
import com.fenchtose.lithogifsearch.models.GifItem;

public class FullScreenPath extends AppPath<FullScreenPresenter, FullScreenView> {

	private final AppComponent appComponent;
	private final ActivityComponent activityComponent;

	@Nullable
	private final EventHandler<FavChangeEvent> likeHandler;
	private final GifItem gif;
	private final boolean isLiked;

	public FullScreenPath(AppComponent appComponent, ActivityComponent activityComponent,
						  @Nullable EventHandler<FavChangeEvent> likeHandler,
						  GifItem gif, boolean isLiked) {
		this.appComponent = appComponent;
		this.activityComponent = activityComponent;
		this.likeHandler = likeHandler;
		this.gif = gif;
		this.isLiked = isLiked;
	}

	@Override
	protected FullScreenView createView() {
		return new FullScreenView(activityComponent.componentContext(), likeHandler, gif,
				activityComponent.glide(), isLiked);
	}

	@Override
	protected FullScreenPresenter createPresenter() {
		return new FullScreenPresenter(this, appComponent.likeStore());
	}
}
