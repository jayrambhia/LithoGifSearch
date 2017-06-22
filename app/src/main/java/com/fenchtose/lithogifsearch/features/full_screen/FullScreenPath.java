package com.fenchtose.lithogifsearch.features.full_screen;

import android.content.Context;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.EventHandler;
import com.fenchtose.lithogifsearch.base.AppPath;
import com.fenchtose.lithogifsearch.events.FavChangeEvent;
import com.fenchtose.lithogifsearch.models.GifItem;
import com.fenchtose.lithogifsearch.models.db.PreferenceLikeStore;

public class FullScreenPath extends AppPath<FullScreenPresenter, FullScreenView> {

	private final Context context;
	private final ComponentContext c;
	private final EventHandler<FavChangeEvent> likeHandler;
	private final GifItem gif;
	private final RequestManager glide;
	private final boolean isLiked;

	public FullScreenPath(Context context, ComponentContext c, EventHandler<FavChangeEvent> likeHandler,
						  GifItem gif, RequestManager glide, boolean isLiked) {
		this.context = context;
		this.c = c;
		this.likeHandler = likeHandler;
		this.gif = gif;
		this.glide = glide;
		this.isLiked = isLiked;
	}

	@Override
	protected FullScreenView createView() {
		return new FullScreenView(c, likeHandler, gif, glide, isLiked);
	}

	@Override
	protected FullScreenPresenter createPresenter() {
		return new FullScreenPresenter(new PreferenceLikeStore(context));
	}
}
