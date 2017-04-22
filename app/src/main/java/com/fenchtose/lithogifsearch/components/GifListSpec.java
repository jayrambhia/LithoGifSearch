package com.fenchtose.lithogifsearch.components;

import android.content.Context;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentInfo;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.Row;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.GridLayoutInfo;
import com.facebook.litho.widget.Recycler;
import com.facebook.litho.widget.RecyclerBinder;
import com.fenchtose.lithogifsearch.models.GifItem;

import java.util.ArrayList;
import java.util.List;

@LayoutSpec
public class GifListSpec {

	@OnCreateLayout
	static ComponentLayout onCreateLayout(ComponentContext c, @Prop RecyclerBinder binder) {
		return Row.create(c)
				.child(Recycler.create(c)
						.binder(binder).build()).build();
	}

	public static void updateContent(ComponentContext c, RecyclerBinder binder, List<GifItem> gifs) {

		binder.removeRangeAt(0, binder.getItemCount());

		List<ComponentInfo> components = new ArrayList<>();

		for (GifItem gifItem : gifs) {
			components.add(ComponentInfo.create().component(
					GifItemView.create(c)
							.gif(gifItem)
							.key(gifItem.getId())
							.build()
				).build()
			);

		}

		binder.insertRangeAt(0, components);
	}

	public static RecyclerBinder getBinder(ComponentContext c, Context context) {
		return new RecyclerBinder(c, new GridLayoutInfo(context, 3));
	}
}
