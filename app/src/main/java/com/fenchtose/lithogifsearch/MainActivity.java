package com.fenchtose.lithogifsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.litho.LithoView;
import com.fenchtose.lithogifsearch.base.router.Router;
import com.fenchtose.lithogifsearch.base.router.Routes;
import com.fenchtose.lithogifsearch.dagger.ActivityComponent;
import com.fenchtose.lithogifsearch.dagger.ActivityModule;
import com.fenchtose.lithogifsearch.dagger.AppComponent;
import com.fenchtose.lithogifsearch.dagger.DaggerActivityComponent;

public class MainActivity extends AppCompatActivity {

	private Router router;
	private ActivityComponent component;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		component = DaggerActivityComponent.builder()
				.activityModule(new ActivityModule(this))
				.build();

		AppComponent appComponent = ((MyApplication) getApplicationContext()).getAppComponent();

		LithoView root = new LithoView(this);
		setContentView(root);

		router = Router.with(root);

		Routes.start(appComponent, component, router);
		Routes.get().openHome();
	}

	@Override
	public void onBackPressed() {
		if (!router.onBackRequested()) {
			return;
		}

		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Routes.stop();
		component = null;
	}
}
