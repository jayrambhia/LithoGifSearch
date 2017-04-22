package com.fenchtose.lithogifsearch.components;

import android.graphics.Color;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaJustify;
import com.fenchtose.lithogifsearch.models.GifItem;

@LayoutSpec
public class GifItemViewSpec {

	@OnCreateLayout
	static ComponentLayout onCreateLayout(ComponentContext c, @Prop GifItem gif) {
		return Column.create(c)
				.backgroundColor(Color.LTGRAY)
				.alignContent(YogaAlign.CENTER)
				.justifyContent(YogaJustify.CENTER)
				.child(GlideImage.create(c)
						.width(gif.getWidth())
						.height(gif.getHeight())
						.image(gif.getSmall())
						.build())
				.build();
	}
}
