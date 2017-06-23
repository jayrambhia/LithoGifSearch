package com.fenchtose.lithogifsearch.features.full_screen;

import android.support.annotation.Nullable;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.EventHandler;
import com.fenchtose.lithogifsearch.base.ComponentView;
import com.fenchtose.lithogifsearch.components.FullScreenComponent;
import com.fenchtose.lithogifsearch.components.FullScreenComponentSpec;
import com.fenchtose.lithogifsearch.events.FavChangeEvent;
import com.fenchtose.lithogifsearch.models.GifItem;

public class FullScreenView implements ComponentView<Component<FullScreenComponent>> {

	private final ComponentContext c;
	@Nullable
	private final EventHandler<FavChangeEvent> likeHandler;
	private final GifItem gif;
	private final RequestManager glide;
	private final boolean isLiked;

	private Callback callback;

	public FullScreenView(ComponentContext c, @Nullable EventHandler<FavChangeEvent> likeHandler, GifItem gif,
						  RequestManager glide, boolean isLiked) {
		this.c = c;
		this.likeHandler = likeHandler;
		this.gif = gif;
		this.glide = glide;
		this.isLiked = isLiked;
	}

	@Override
	public Component<FullScreenComponent> getComponent() {

		return FullScreenComponent.create(c)
				.initLiked(isLiked)
				.gif(gif)
				.likeHandler(likeHandler)
				.callback(callback)
				.key(gif.getId() + System.currentTimeMillis())
				.glide(glide)
				.build();
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public interface Callback extends FullScreenComponentSpec.Callback {}
}
