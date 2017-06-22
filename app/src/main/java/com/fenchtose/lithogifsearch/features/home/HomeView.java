package com.fenchtose.lithogifsearch.features.home;

import android.content.Context;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.widget.RecyclerBinder;
import com.fenchtose.lithogifsearch.base.ComponentView;
import com.fenchtose.lithogifsearch.components.GifItemViewSpec;
import com.fenchtose.lithogifsearch.components.HomeComponent;
import com.fenchtose.lithogifsearch.components.HomeComponentSpec;
import com.fenchtose.lithogifsearch.models.GifItem;
import com.fenchtose.lithogifsearch.utils.GifListUtils;

import java.util.List;

public class HomeView implements ComponentView<Component<HomeComponent>> {

	private final ComponentContext c;
	private final RecyclerBinder binder;
	private final RequestManager glide;

	private Callback callback;

	public HomeView(Context context, ComponentContext c, RequestManager glide) {
		this.c = c;
		this.glide = glide;
		binder = GifListUtils.getBinder(c, context);
	}

	@Override
	public Component<HomeComponent> getComponent() {

		final HomeComponentSpec.OnQueryUpdateListener queryListener = new HomeComponentSpec.OnQueryUpdateListener() {
			@Override
			public void onQueryUpdated(String query) {
				if (callback != null) {
					callback.onSearchRequested(query);
				}
			}
		};

		return HomeComponent.create(c)
				.hint("Search Gif")
				.binder(binder)
				.listener(queryListener)
				.build();
	}

	public void updateContent(List<GifItem> gifs) {
		GifListUtils.updateContent(c, binder, glide, gifs, callback);
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public interface Callback extends GifItemViewSpec.GifCallback {
		void onSearchRequested(String query);
	}
}
