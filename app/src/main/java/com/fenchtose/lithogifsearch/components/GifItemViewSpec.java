package com.fenchtose.lithogifsearch.components;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.Column;
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
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;
import com.fenchtose.lithogifsearch.R;
import com.fenchtose.lithogifsearch.models.GifItem;

@LayoutSpec
public class GifItemViewSpec {

	@OnCreateInitialState
	static void createInitialState(ComponentContext c, StateValue<Boolean> isLiked, @Prop boolean initLiked) {
		isLiked.set(initLiked);
	}

	@OnCreateLayout
	static ComponentLayout onCreateLayout(ComponentContext context, @Prop GifItem gif,
										  @Prop RequestManager glide, @State boolean isLiked) {
		return Column.create(context)
				.child(GifImageView.create(context)
							.gif(gif)
							.glide(glide)
							.withLayout()
							.alignSelf(YogaAlign.CENTER)
							.build())
				.child(Image.create(context)
						.drawableRes(isLiked ? R.drawable.ic_favorite_accent_24dp :R.drawable.ic_favorite_border_accent_24dp)
						.withLayout()
						.clickHandler(GifItemView.onLikeButtonClicked(context))
						.positionType(YogaPositionType.ABSOLUTE)
						.widthDip(40)
						.heightDip(40)
						.paddingDip(YogaEdge.ALL, 8)
						.alignSelf(YogaAlign.FLEX_END)
						.build()
				).build();
	}

	@OnUpdateState
	static void updateLikeButton(StateValue<Boolean> isLiked) {
		isLiked.set(!isLiked.get());
	}

	@OnEvent(ClickEvent.class)
	static void onLikeButtonClicked(ComponentContext c, @State boolean isLiked, @Prop GifItem gif, @Prop (optional = true) GifCallback callback) {
		if (callback != null) {
			callback.onGifLiked(gif.getId(), !isLiked);
		}

		GifItemView.updateLikeButtonAsync(c);
	}

	public interface GifCallback {
		void onGifLiked(String id, boolean liked);
	}
}
