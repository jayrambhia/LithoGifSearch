package com.fenchtose.lithogifsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.EventDispatcher;
import com.facebook.litho.EventHandler;
import com.facebook.litho.HasEventDispatcher;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.RecyclerBinder;
import com.fenchtose.lithogifsearch.components.FullScreenComponent;
import com.fenchtose.lithogifsearch.components.GifItemViewSpec;
import com.fenchtose.lithogifsearch.components.HomeComponent;
import com.fenchtose.lithogifsearch.components.HomeComponentSpec;
import com.fenchtose.lithogifsearch.events.LikeChangeEvent;
import com.fenchtose.lithogifsearch.models.GifItem;
import com.fenchtose.lithogifsearch.models.api.GifProvider;
import com.fenchtose.lithogifsearch.models.db.LikeStore;
import com.fenchtose.lithogifsearch.models.db.PreferenceLikeStore;
import com.fenchtose.lithogifsearch.utils.GifListUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

	private Component homeComponent;

	private LithoView root;
	private boolean isFullScreen;

	private static final int LIKE_EVENT_ID = 11;

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ComponentContext c = new ComponentContext(this);

		final RecyclerBinder binder = GifListUtils.getBinder(c, this);

		final RequestManager glide = Glide.with(this);

		final LikeStore likeStore = new PreferenceLikeStore(this);

		final EventHandler likeChangeHandler = new EventHandler(new HasEventDispatcher() {
			@Override
			public EventDispatcher getEventDispatcher() {
				return new EventDispatcher() {
					@Override
					public Object dispatchOnEvent(EventHandler eventHandler, Object eventState) {
						LikeChangeEvent event = (LikeChangeEvent) eventState;
						likeStore.setLiked(event.gifId, event.isLiked);
						return null;
					}
				};
			}
		}, LIKE_EVENT_ID, null);

		final GifItemViewSpec.GifCallback callback = new GifItemViewSpec.GifCallback() {
			@Override
			public void onGifSelected(GifItem gif, Component gifComponent) {
				showFullScreen(c, glide, gif, likeStore, likeChangeHandler, gifComponent);
			}
		};

		final GifProvider.ResponseListener responseListener = new GifProvider.ResponseListener() {
			@Override
			public void onSuccess(List<GifItem> gifs) {
				GifListUtils.updateContent(c, binder, glide, gifs, callback, likeChangeHandler);
			}

			@Override
			public void onFailure(Throwable t) {
				t.printStackTrace();
			}
		};

		final GifProvider gifProvider = new GifProvider(responseListener, likeStore);

		final HomeComponentSpec.OnQueryUpdateListener queryListener = new HomeComponentSpec.OnQueryUpdateListener() {
			@Override
			public void onQueryUpdated(String query) {
				if (query.length() >= 6) {
					gifProvider.search(query);
				}
			}
		};

		homeComponent = HomeComponent.create(c)
				.hint("Search Gif")
				.binder(binder)
				.listener(queryListener)
				.build();

		root = LithoView.create(this, homeComponent);
		setContentView(root);
	}

	private void showFullScreen(ComponentContext context, RequestManager glide, GifItem gif, final LikeStore likeStore,
								EventHandler likeChangeHandler, Component gifComponent) {

		Component fullScreenComponent = FullScreenComponent.create(context)
				.initLiked(likeStore.isLiked(gif.getId()))
				.gif(gif)
				.gifComponent(gifComponent)
				// Key is important. If key is not provided (or not different), state initializtion will not work
				// Adding current time to key to get new component. If the key is gif id, for the same gif
				// we get same component and its @OnCreateInitialState is not called.
				// So if we have already opened a gif before and we update like state and open the gif again
				// the state is not updated.
				.key(gif.getId() + System.currentTimeMillis())
				.glide(glide)
				.likeChangeEventHandler(likeChangeHandler)
				.build();

		root.setComponentAsync(fullScreenComponent);

		isFullScreen = true;
	}

	@Override
	public void onBackPressed() {
		if (isFullScreen) {
			isFullScreen = false;
			root.setComponentAsync(homeComponent);
			return;
		}

		super.onBackPressed();
	}
}
