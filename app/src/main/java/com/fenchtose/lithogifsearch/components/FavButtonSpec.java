package com.fenchtose.lithogifsearch.components;

import com.facebook.litho.ClickEvent;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateInitialState;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.OnUpdateState;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.State;
import com.facebook.litho.widget.Image;
import com.facebook.yoga.YogaEdge;
import com.fenchtose.lithogifsearch.R;

@LayoutSpec
public class FavButtonSpec {

	@OnCreateInitialState
	static void createInitialState(ComponentContext c, StateValue<Boolean> isLiked, @Prop boolean initLiked) {
		isLiked.set(initLiked);
	}

	@OnCreateLayout
	static ComponentLayout onCreateLayout(ComponentContext context, @Prop String gifId, @Prop boolean propLiked, @Prop(optional = true) boolean isBig, @State boolean isLiked) {
		return Image.create(context)
				.key(gifId)
				.drawableRes(propLiked ? (isBig ? R.drawable.ic_favorite_accent_48dp : R.drawable.ic_favorite_accent_24dp)
						: (isBig ? R.drawable.ic_favorite_border_accent_48dp : R.drawable.ic_favorite_border_accent_24dp))
				.withLayout()
				.clickHandler(FavButton.onLikeButtonClicked(context))
				.widthDip(isBig ? 64 : 40)
				.heightDip(isBig ? 64 : 40)
				.paddingDip(YogaEdge.ALL, 8)
				.build();
	}

	@OnUpdateState
	static void updateLikeButton(StateValue<Boolean> isLiked) {
		isLiked.set(!isLiked.get());
	}

	@OnEvent(ClickEvent.class)
	static void onLikeButtonClicked(ComponentContext c, @State boolean isLiked, @Prop String gifId, @Prop(optional = true) Callback callback) {
		if (callback != null) {
			callback.onGifLiked(gifId, !isLiked);
		}

		FavButton.updateLikeButton(c);
	}

	public interface Callback {
		void onGifLiked(String id, boolean liked);
	}
}
