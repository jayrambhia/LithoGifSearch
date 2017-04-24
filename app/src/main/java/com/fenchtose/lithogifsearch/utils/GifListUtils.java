package com.fenchtose.lithogifsearch.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentInfo;
import com.facebook.litho.widget.GridLayoutInfo;
import com.facebook.litho.widget.RecyclerBinder;
import com.fenchtose.lithogifsearch.components.GifItemView;
import com.fenchtose.lithogifsearch.components.GifItemViewSpec;
import com.fenchtose.lithogifsearch.models.GifItem;

import java.util.ArrayList;
import java.util.List;

public class GifListUtils {
	public static void updateContent(ComponentContext c, RecyclerBinder binder, RequestManager glide,
									 List<GifItem> gifs, @Nullable GifItemViewSpec.GifCallback callback) {

		binder.removeRangeAt(0, binder.getItemCount());

		List<ComponentInfo> components = new ArrayList<>();

		for (GifItem gif: gifs) {
			components.add(ComponentInfo.create().component(
					GifItemView.create(c)
							.gif(gif)
							.glide(glide)
							.initLiked(gif.isLiked())
							.callback(callback)
							.key(gif.getId())
							.build()
				).build()
			);

		}

		binder.insertRangeAt(0, components);
	}

	public static RecyclerBinder getBinder(ComponentContext c, Context context) {
		return new RecyclerBinder(c, new GridLayoutInfo(context, 2));
	}
}
