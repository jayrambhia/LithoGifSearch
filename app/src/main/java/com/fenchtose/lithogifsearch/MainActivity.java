package com.fenchtose.lithogifsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.RecyclerBinder;
import com.fenchtose.lithogifsearch.components.FullScreenComponent;
import com.fenchtose.lithogifsearch.components.FullScreenComponentSpec;
import com.fenchtose.lithogifsearch.components.GifItemViewSpec;
import com.fenchtose.lithogifsearch.components.HomeComponent;
import com.fenchtose.lithogifsearch.components.HomeComponentSpec;
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
	private ComponentTree componentTree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ComponentContext c = new ComponentContext(this);

		final RecyclerBinder binder = GifListUtils.getBinder(c, this);

		final RequestManager glide = Glide.with(this);

		final LikeStore likeStore = new PreferenceLikeStore(this);

		final GifItemViewSpec.GifCallback callback = new GifItemViewSpec.GifCallback() {
			@Override
			public void onGifLiked(String id, boolean liked) {
				likeStore.setLiked(id, liked);
			}

			@Override
			public void onGifSelected(GifItem gif) {
				showFullScreen(c, glide, gif, likeStore);
			}
		};

		final GifProvider.ResponseListener responseListener = new GifProvider.ResponseListener() {
			@Override
			public void onSuccess(List<GifItem> gifs) {
				GifListUtils.updateContent(c, binder, glide, gifs, callback);
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

		componentTree = ComponentTree.create(c, homeComponent)
				.build();

		root.setComponentTree(componentTree);

		setContentView(root);
	}

	private void showFullScreen(ComponentContext context, RequestManager glide, GifItem gif, final LikeStore likeStore) {
		Component component = FullScreenComponent.create(context)
				.initLiked(likeStore.isLiked(gif.getId()))
				.gif(gif)
				// Key is important. If key is not provided (or not different), state initializtion will not work
				.key(gif.getId())
				.glide(glide)
				.callback(new FullScreenComponentSpec.Callback() {
					@Override
					public void onGifLiked(String id, boolean liked) {
						likeStore.setLiked(id, liked);
					}
				})
				.build();

		componentTree.setRoot(component, true);
		isFullScreen = true;
	}

	@Override
	public void onBackPressed() {
		if (isFullScreen) {
			isFullScreen = false;
			componentTree.setRoot(homeComponent, true);
			return;
		}

		super.onBackPressed();
	}
}
