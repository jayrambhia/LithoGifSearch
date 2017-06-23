package com.fenchtose.lithogifsearch.presenter;

import com.fenchtose.lithogifsearch.features.home.HomePath;
import com.fenchtose.lithogifsearch.features.home.HomePresenter;
import com.fenchtose.lithogifsearch.features.home.HomeView;
import com.fenchtose.lithogifsearch.models.GifItem;
import com.fenchtose.lithogifsearch.models.api.GifProvider;
import com.fenchtose.lithogifsearch.models.db.LikeStore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest {

	@Mock LikeStore likeStore;
	@Mock GifProvider gifProvider;
	@Mock HomeView view;
	@Mock HomePath path;

	@Captor ArgumentCaptor<GifProvider.ResponseListener> gifErrorCaptor;

	private HomePresenter presenter;

	@Before
	public void setup() {
		presenter = new HomePresenter(path, likeStore, gifProvider);
		presenter.attach(view);
	}

	@Test
	public void likeStore() {
		presenter.onGifLiked("id", true);
		verify(likeStore, times(1)).setLiked("id", true);
	}

	@Test
	public void fetchGif() {

		final List<GifItem> gifs = new ArrayList<>();
		gifs.add(new GifItem("1", GifItem.Images.Empty(), false));
		gifs.add(new GifItem("2", GifItem.Images.Empty(), true));
		gifs.add(new GifItem("3", GifItem.Images.Empty(), true));

		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				((GifProvider.ResponseListener) invocation.getArguments()[1]).onSuccess(gifs);
				return null;
			}
		}).when(gifProvider).search(eq("batman"), any(GifProvider.ResponseListener.class));

		presenter.onSearchRequested("batman");
		verify(gifProvider, times(1)).search(eq("batman"), any(GifProvider.ResponseListener.class));
		verify(view, times(1)).updateContent(gifs);
	}

	@Test
	public void shortQuery() {
		presenter.onSearchRequested("short");
		verify(gifProvider, never()).search(anyString(), any(GifProvider.ResponseListener.class));
		//noinspection unchecked
		verify(view, never()).updateContent(anyList());
	}

	@Test
	public void gifProviderError() {
		presenter.onSearchRequested("batman");
		verify(gifProvider).search(eq("batman"), gifErrorCaptor.capture());
		gifErrorCaptor.getValue().onFailure(new IOException("Network unreachable in test"));
		verify(view, never()).updateContent(anyList());
	}

	@Test
	public void openGif() {
		GifItem gif = new GifItem("1", GifItem.Images.Empty(), true);
		when(likeStore.isLiked("1")).thenReturn(true);
		presenter.onGifSelected(gif, null);
		verify(path, times(1)).openGif(gif, null, true);
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
}
