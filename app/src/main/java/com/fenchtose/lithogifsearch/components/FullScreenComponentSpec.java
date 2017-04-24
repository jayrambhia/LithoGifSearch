package com.fenchtose.lithogifsearch.components;

import android.graphics.Color;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaJustify;
import com.fenchtose.lithogifsearch.models.GifItem;

@LayoutSpec
public class FullScreenComponentSpec {

	@OnCreateLayout
	static ComponentLayout onCreateLayout(ComponentContext context, @Prop RequestManager glide, @Prop GifItem gif,
										  @Prop boolean isLiked, @Prop FavButtonSpec.Callback callback) {
		return Column.create(context)
				.backgroundColor(Color.BLUE)
				.justifyContent(YogaJustify.SPACE_AROUND)
				.paddingDip(YogaEdge.ALL, 16)
				.heightPercent(100)
				.child(GifImageView.create(context)
						.gif(gif)
						.isFullScreen(true)
						.glide(glide)
						.withLayout()
						.alignSelf(YogaAlign.CENTER)
						.build())
				.child(FavButton.create(context)
						.initLiked(isLiked)
						.isBig(true)
						.gifId(gif.getId())
						.callback(callback)
						.withLayout()
						.alignSelf(YogaAlign.CENTER)
						.build())
				.build();
	}
}
