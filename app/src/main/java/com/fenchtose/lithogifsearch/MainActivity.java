package com.fenchtose.lithogifsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.RecyclerBinder;
import com.fenchtose.lithogifsearch.components.HomeComponent;
import com.fenchtose.lithogifsearch.components.HomeComponentSpec;
import com.fenchtose.lithogifsearch.models.GifItem;
import com.fenchtose.lithogifsearch.models.api.GifProvider;
import com.fenchtose.lithogifsearch.utils.GifListUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ComponentContext c = new ComponentContext(this);

		final RecyclerBinder binder = GifListUtils.getBinder(c, this);

		final RequestManager glide = Glide.with(this);

		final GifProvider gifProvider = new GifProvider(new GifProvider.ResposneListener() {
			@Override
			public void onSuccess(List<GifItem> gifs) {
				GifListUtils.updateContent(c, binder, glide, gifs);
			}

			@Override
			public void onFailure(Throwable t) {
				t.printStackTrace();
			}
		});

		final Component component = HomeComponent.create(c)
				.hint("Search Gif")
				.binder(binder)
				.listener(new HomeComponentSpec.OnQueryUpdateListener() {
					@Override
					public void onQueryUpdated(String query) {
						if (query.length() >= 6) {
							gifProvider.search(query);
						}
					}
				})
				.build();

		final LithoView view = LithoView.create(this, component);
		setContentView(view);
	}

}
