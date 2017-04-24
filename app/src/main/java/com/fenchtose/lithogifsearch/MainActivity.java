package com.fenchtose.lithogifsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.RecyclerBinder;
import com.fenchtose.lithogifsearch.components.FavButtonSpec;
import com.fenchtose.lithogifsearch.components.FullScreenComponent;
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
	private String selectedGifId = null;
	private RecyclerBinder binder;
	private List<GifItem> gifs;
	private LikeStore likeStore;
	private ComponentContext cContext;
	private RequestManager glide;

	private GifItemViewSpec.GifCallback gifCallback;
	private FavButtonSpec.Callback favCallback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		cContext = new ComponentContext(this);

		binder = GifListUtils.getBinder(cContext, this);

		glide = Glide.with(this);

		likeStore = new PreferenceLikeStore(this);

		gifCallback = new GifItemViewSpec.GifCallback() {
			@Override
			public void onGifSelected(GifItem gif) {
				showFullScreen(cContext, glide, gif, likeStore);
			}
		};

		favCallback = new FavButtonSpec.Callback() {
			@Override
			public void onGifLiked(String id, boolean liked) {
				likeStore.setLiked(id, liked);
				updateGifLikeStatus(id);
			}
		};

		final GifProvider.ResponseListener responseListener = new GifProvider.ResponseListener() {
			@Override
			public void onSuccess(List<GifItem> data) {
				gifs = data;
				GifListUtils.updateContent(cContext, binder, glide, gifs, gifCallback, favCallback);
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

		homeComponent = HomeComponent.create(cContext)
				.hint("Search Gif")
				.binder(binder)
				.listener(queryListener)
				.build();

		root = LithoView.create(this, homeComponent);

		setContentView(root);
	}

	private void showFullScreen(ComponentContext context, RequestManager glide, GifItem gif, final LikeStore likeStore) {

		selectedGifId = gif.getId();

		Component component = FullScreenComponent.create(context)
				.gif(gif)
				.isLiked(likeStore.isLiked(gif.getId()))
				// Key is important. If key is not provided (or not different), state initialization will not work
				.key(gif.getId())
				.glide(glide)
				.callback(favCallback)
				.build();

		root.setComponentAsync(component);
		isFullScreen = true;
	}

	@Override
	public void onBackPressed() {
		if (isFullScreen) {
			isFullScreen = false;

			if (selectedGifId != null) {
				updateGifLikeStatus(selectedGifId);
			}

			root.setComponentAsync(homeComponent);
			selectedGifId = null;
			return;
		}

		super.onBackPressed();
	}

	private void updateGifLikeStatus(String gifId) {
		int selectedIndex = -1;
		for (int i=0; i<gifs.size(); i++) {
			GifItem gif = gifs.get(i);
			if (gifId.equals(gif.getId())) {
				selectedIndex = i;
				break;
			}
		}

		if (selectedIndex != -1) {
			GifItem current = gifs.get(selectedIndex);
			GifItem gif = new GifItem(current, likeStore.isLiked(current.getId()));
			gifs.set(selectedIndex, gif);
			GifListUtils.updateItem(cContext, binder, glide, gif, selectedIndex, gifCallback, favCallback);
		}
	}
}
