package com.fenchtose.lithogifsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import com.fenchtose.lithogifsearch.base.router.Router;
import com.fenchtose.lithogifsearch.base.router.Routes;

public class MainActivity extends AppCompatActivity {

	private Router router;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ComponentContext c = new ComponentContext(this);
		final RequestManager glide = Glide.with(this);

		LithoView root = new LithoView(this);
		setContentView(root);

		router = Router.with(root);

		Routes routes = new Routes.Builder()
				.context(this)
				.cContext(c)
				.glide(glide)
				.router(router)
				.build();

		routes.openHome();
	}

	@Override
	public void onBackPressed() {
		if (!router.onBackRequested()) {
			return;
		}

		super.onBackPressed();
	}

}
