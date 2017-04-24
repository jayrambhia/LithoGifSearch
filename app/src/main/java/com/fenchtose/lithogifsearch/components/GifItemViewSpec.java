package com.fenchtose.lithogifsearch.components;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaPositionType;
import com.fenchtose.lithogifsearch.models.GifItem;

@LayoutSpec
public class GifItemViewSpec {

	@OnCreateLayout
	static ComponentLayout onCreateLayout(ComponentContext context, @Prop GifItem gif,
										  @Prop RequestManager glide, @Prop FavButtonSpec.Callback favCallback) {
		return Column.create(context)
				.child(GifImageView.create(context)
							.gif(gif)
							.glide(glide)
							.withLayout()
							.alignSelf(YogaAlign.CENTER)
							.build())
				.child(FavButton.create(context)
						.callback(favCallback)
						.initLiked(gif.isLiked())
						.gifId(gif.getId())
						.withLayout()
						.positionType(YogaPositionType.ABSOLUTE)
						.widthDip(40)
						.heightDip(40)
						.alignSelf(YogaAlign.FLEX_END)
						.build())
				.clickHandler(GifItemView.onViewClicked(context))
				.build();
	}

	@OnEvent(ClickEvent.class)
	static void onViewClicked(ComponentContext c, @Prop GifItem gif, @Prop (optional = true) GifCallback gifCallback) {
		if (gifCallback != null) {
			gifCallback.onGifSelected(gif);
		}
	}

	public interface GifCallback {
		void onGifSelected(GifItem gif);
	}
}
