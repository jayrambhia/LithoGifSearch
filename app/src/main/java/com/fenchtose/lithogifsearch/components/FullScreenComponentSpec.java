package com.fenchtose.lithogifsearch.components;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaJustify;
import com.fenchtose.lithogifsearch.models.GifItem;

@LayoutSpec
public class FullScreenComponentSpec {

	@OnCreateLayout
	static ComponentLayout onCreateLayout(ComponentContext context, @Prop RequestManager glide, @Prop GifItem gif) {
		return Column.create(context)
				.justifyContent(YogaJustify.CENTER)
				.child(GifImageView.create(context)
						.gif(gif)
						.isFullScreen(true)
						.glide(glide).build())
				.build();
	}
}
