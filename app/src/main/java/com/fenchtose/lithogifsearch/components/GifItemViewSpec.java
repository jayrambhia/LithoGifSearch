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
import com.facebook.litho.annotations.Param;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.State;
import com.facebook.litho.widget.Image;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaPositionType;
import com.fenchtose.lithogifsearch.R;
import com.fenchtose.lithogifsearch.events.GifSelectEvent;
import com.fenchtose.lithogifsearch.events.LikeChangeEvent;
import com.fenchtose.lithogifsearch.models.GifItem;

@LayoutSpec(events = { LikeChangeEvent.class, GifSelectEvent.class })
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
						.build())
				.clickHandler(GifItemView.onViewClicked(context))
				.build();
	}

	@OnUpdateState
	static void updateLikeButton(StateValue<Boolean> isLiked, @Param boolean updatedValue) {
		isLiked.set(updatedValue);
	}

	@OnEvent(ClickEvent.class)
	static void onLikeButtonClicked(ComponentContext c, @State boolean isLiked, @Prop GifItem gif) {
		GifItemView.updateLikeButtonAsync(c, !isLiked);
		GifItemView.dispatchLikeChangeEvent(GifItemView.getLikeChangeEventHandler(c), gif.getId(), !isLiked);
	}

	@OnEvent(ClickEvent.class)
	static void onViewClicked(ComponentContext c, @Prop GifItem gif) {
		GifItemView.dispatchGifSelectEvent(GifItemView.getGifSelectEventHandler(c), gif);
	}

}
