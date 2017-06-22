package com.fenchtose.lithogifsearch.base.router;

import android.content.Context;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.EventHandler;
import com.fenchtose.lithogifsearch.events.FavChangeEvent;
import com.fenchtose.lithogifsearch.features.full_screen.FullScreenPath;
import com.fenchtose.lithogifsearch.features.home.HomePath;
import com.fenchtose.lithogifsearch.models.GifItem;

public class Routes {

	private final Context context;
	private final ComponentContext cContext;
	private final Router router;
	private final RequestManager glide;

	private static Routes instance;

	private Routes(Builder builder) {
		context = builder.context;
		cContext = builder.cContext;
		router = builder.router;
		glide = builder.glide;
		instance = this;
	}

	public static Routes get() {
		if (instance == null) {
			throw new IllegalArgumentException("Routes not initialized");
		}

		return instance;
	}

	public void openHome() {
		router.go(new HomePath(context, cContext, glide));
	}

	public void openGif(EventHandler<FavChangeEvent> likeHandler, GifItem gif, boolean isLiked) {
		router.go(new FullScreenPath(context, cContext, likeHandler, gif, glide, isLiked));
	}

	public static final class Builder {
		private Context context;
		private ComponentContext cContext;
		private Router router;
		private RequestManager glide;

		public Builder() {
		}

		public Builder context(Context context) {
			this.context = context;
			return this;
		}

		public Builder cContext(ComponentContext cContext) {
			this.cContext = cContext;
			return this;
		}

		public Builder router(Router router) {
			this.router = router;
			return this;
		}

		public Builder glide(RequestManager glide) {
			this.glide = glide;
			return this;
		}

		public Routes build() {
			return new Routes(this);
		}
	}
}
