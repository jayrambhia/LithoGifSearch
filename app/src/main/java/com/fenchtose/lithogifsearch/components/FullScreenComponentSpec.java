package com.fenchtose.lithogifsearch.components;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.EventHandler;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateInitialState;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.OnUpdateState;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.State;
import com.facebook.litho.widget.Image;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaJustify;
import com.fenchtose.lithogifsearch.R;
import com.fenchtose.lithogifsearch.events.FavChangeEvent;
import com.fenchtose.lithogifsearch.models.GifItem;

@LayoutSpec
public class FullScreenComponentSpec {

	private static final String TAG = "FullScreenComponent";

	@OnCreateInitialState
	static void createInitialState(ComponentContext c, StateValue<Boolean> isLiked, @Prop boolean initLiked) {
		isLiked.set(initLiked);
	}

	@OnCreateLayout
	static ComponentLayout onCreateLayout(ComponentContext context, @Prop RequestManager glide, @Prop GifItem gif, @State boolean isLiked) {
		return Column.create(context)
				.justifyContent(YogaJustify.SPACE_AROUND)
				.heightPercent(100)
				.child(GifImageView.create(context)
						.gif(gif)
						.isFullScreen(true)
						.glide(glide)
						.withLayout()
						.alignSelf(YogaAlign.CENTER)
						.build())
				.child(Image.create(context)
						.drawableRes(isLiked ? R.drawable.ic_favorite_accent_48dp : R.drawable.ic_favorite_border_accent_48dp)
						.withLayout()
						.clickHandler(FullScreenComponent.onLikeButtonClicked(context))
						.widthDip(64)
						.heightDip(64)
						.alignSelf(YogaAlign.CENTER)
						.paddingDip(YogaEdge.ALL, 8)
						.build())
				.build();
	}

	@OnUpdateState
	static void updateLikeButton(StateValue<Boolean> isLiked) {
		isLiked.set(!isLiked.get());
	}

	@OnEvent(ClickEvent.class)
	static void onLikeButtonClicked(ComponentContext c, @State boolean isLiked, @Prop GifItem gif, @Prop (optional = true) Callback callback,
									@Prop Component gifComponent) {
		if (callback != null) {
			callback.onGifLiked(gif.getId(), !isLiked);
		}

		FullScreenComponent.updateLikeButtonAsync(c);
		EventHandler<FavChangeEvent> handler = GifItemView.onFavChanged(gifComponent.getScopedContext());
		handler.dispatchEvent(new FavChangeEvent(!isLiked, gif.getId()));
	}

	public interface Callback {
		void onGifLiked(String id, boolean liked);
	}
}
