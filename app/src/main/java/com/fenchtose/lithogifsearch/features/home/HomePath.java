package com.fenchtose.lithogifsearch.features.home;

import android.support.annotation.Nullable;

import com.facebook.litho.Component;
import com.fenchtose.lithogifsearch.base.AppPath;
import com.fenchtose.lithogifsearch.base.router.Routes;
import com.fenchtose.lithogifsearch.components.GifItemView;
import com.fenchtose.lithogifsearch.dagger.ActivityComponent;
import com.fenchtose.lithogifsearch.dagger.AppComponent;
import com.fenchtose.lithogifsearch.models.GifItem;

public class HomePath extends AppPath<HomePresenter, HomeView> {

	private final AppComponent appComponent;
	private final ActivityComponent activityComponent;

	public HomePath(AppComponent appComponent, ActivityComponent activityComponent) {
		this.appComponent = appComponent;
		this.activityComponent = activityComponent;
	}

	@Override
	protected HomeView createView() {
		return new HomeView(activityComponent.context(), activityComponent.componentContext(),
				activityComponent.glide());
	}

	@Override
	protected HomePresenter createPresenter() {
		return new HomePresenter(this, appComponent.likeStore(), appComponent.gifProvider());
	}

	public void openGif(GifItem gif, @Nullable Component gifComponent, boolean isLiked) {
		Routes.get().openGif(gifComponent == null ? null : GifItemView.onFavChanged(gifComponent.getScopedContext()),
				gif, isLiked);
	}
}
