package com.fenchtose.lithogifsearch.presenter;

import com.fenchtose.lithogifsearch.features.full_screen.FullScreenPath;
import com.fenchtose.lithogifsearch.features.full_screen.FullScreenPresenter;
import com.fenchtose.lithogifsearch.features.full_screen.FullScreenView;
import com.fenchtose.lithogifsearch.models.db.LikeStore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FullScreenPresenterTest {

	@Mock LikeStore likeStore;
	@Mock FullScreenView view;
	@Mock FullScreenPath path;

	private FullScreenPresenter presenter;

	@Before
	public void setup() {
		presenter = new FullScreenPresenter(path, likeStore);
		presenter.attach(view);
	}

	@Test
	public void attach() {
		// Already attached
		verify(view).setCallback(presenter);
		assertNotNull(presenter.getViewForTest());
	}

	@Test
	public void detach() {
		verify(view).setCallback(presenter);
		presenter.detach(view);
		verify(view).setCallback(null);
		assertNull(presenter.getViewForTest());
	}

	@Test
	public void likeStore() {
		presenter.onGifLiked("id", true);
		verify(likeStore, times(1)).setLiked("id", true);
	}
}
