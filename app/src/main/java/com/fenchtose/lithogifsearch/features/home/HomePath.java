package com.fenchtose.lithogifsearch.features.home;

import android.content.Context;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.ComponentContext;
import com.fenchtose.lithogifsearch.base.AppPath;
import com.fenchtose.lithogifsearch.models.api.GifProvider;
import com.fenchtose.lithogifsearch.models.db.LikeStore;

public class HomePath extends AppPath<HomePresenter, HomeView> {

	private final ComponentContext c;
	private final RequestManager glide;
	private final Context context;
	private final LikeStore likeStore;
	private final GifProvider gifProvider;

	public HomePath(Context context, ComponentContext c, RequestManager glide, LikeStore likeStore,
					GifProvider gifProvider) {
		this.context = context;
		this.c = c;
		this.glide = glide;
		this.likeStore = likeStore;
		this.gifProvider = gifProvider;
	}

	@Override
	protected HomeView createView() {
		return new HomeView(context, c, glide);
	}

	@Override
	protected HomePresenter createPresenter() {
		return new HomePresenter(likeStore, gifProvider);
	}
}
