package com.fenchtose.lithogifsearch.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentInfo;
import com.facebook.litho.widget.GridLayoutInfo;
import com.facebook.litho.widget.RecyclerBinder;
import com.fenchtose.lithogifsearch.components.FavButtonSpec;
import com.fenchtose.lithogifsearch.components.GifItemView;
import com.fenchtose.lithogifsearch.components.GifItemViewSpec;
import com.fenchtose.lithogifsearch.models.GifItem;

import java.util.ArrayList;
import java.util.List;

public class GifListUtils {
	public static void updateContent(ComponentContext c, RecyclerBinder binder, RequestManager glide,
									 List<GifItem> gifs, @Nullable GifItemViewSpec.GifCallback gifCallback,
									 @Nullable FavButtonSpec.Callback favCallback) {

		binder.removeRangeAt(0, binder.getItemCount());

		List<ComponentInfo> components = new ArrayList<>();

		for (GifItem gif: gifs) {
			components.add(getInfo(c, glide, gif, gifCallback, favCallback));
		}

		binder.insertRangeAt(0, components);
	}

	public static void updateItem(ComponentContext c, RecyclerBinder binder, RequestManager glide,
								  GifItem gif, int position, @Nullable GifItemViewSpec.GifCallback gifCallback,
								  @Nullable FavButtonSpec.Callback favCallback) {
		binder.updateItemAt(position, getInfo(c, glide, gif, gifCallback, favCallback));
	}

	private static ComponentInfo getInfo(ComponentContext c, RequestManager glide,
										 GifItem gif, @Nullable GifItemViewSpec.GifCallback gifCallback,
										 @Nullable FavButtonSpec.Callback favCallback) {
		return ComponentInfo.create().component(
				GifItemView.create(c)
						.gif(gif)
						.glide(glide)
						.favCallback(favCallback)
						.gifCallback(gifCallback)
						.key(gif.getId())
						.build())
				.build();
	}

	public static RecyclerBinder getBinder(ComponentContext c, Context context) {
		return new RecyclerBinder(c, new GridLayoutInfo(context, 2));
	}
}
