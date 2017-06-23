package com.fenchtose.lithogifsearch.api;

import com.fenchtose.lithogifsearch.models.GifItem;
import com.fenchtose.lithogifsearch.models.api.GifApi;
import com.fenchtose.lithogifsearch.models.api.GifProvider;
import com.fenchtose.lithogifsearch.models.db.LikeStore;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GifProviderTest {

	@Mock LikeStore likeStore;
	@Mock GifProvider.ResponseListener listener;

	private GifProvider gifProvider;
	private List<GifItem> gifs;

	@Captor ArgumentCaptor<List<GifItem>> responseCaptor;

	@Before
	public void setup() {
		gifs = prepareData();
		Gson gson = new Gson();
		GifApi api = prepareApi(gson, gifs);
		gifProvider = new GifProvider(api, likeStore, gson);
		for (GifItem gif: gifs) {
			when(likeStore.isLiked(gif.getId())).thenReturn(gif.isLiked());
		}
	}

	@Test
	public void success() {
		gifProvider.searchForTesting("batman", listener);
		verify(listener, never()).onFailure((Throwable) any());
		verify(listener, times(1)).onSuccess(responseCaptor.capture());

		List<GifItem> captured = responseCaptor.getValue();
		assertEquals(captured.size(), gifs.size());

		for (int i=0; i<captured.size(); i++) {
			assertEquals(captured.get(i), gifs.get(i));
		}

		for (GifItem gif: gifs) {
			verify(likeStore, times(1)).isLiked(gif.getId());
		}
	}

	@Test
	public void failure() {
		gifProvider.searchForTesting("not_batman", listener);
		verify(listener, times(1)).onFailure((Throwable) any());
		verify(listener, never()).onSuccess(anyList());
	}

	private GifApi prepareApi(Gson gson, List<GifItem> gifs) {
		JsonObject data = new JsonObject();
		data.add("data", gson.toJsonTree(gifs, new TypeToken<List<GifItem>>() {}.getType()));

		Retrofit retrofit = new Retrofit.Builder()
				.client(getOkHttpClient(data))
				.baseUrl(GifApi.ENDPOINT)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		return retrofit.create(GifApi.class);
	}

	private List<GifItem> prepareData() {
		List<GifItem> gifs = new ArrayList<>();
		gifs.add(new GifItem("1", GifItem.Images.Empty(), false));
		gifs.add(new GifItem("2", GifItem.Images.Empty(), true));
		gifs.add(new GifItem("3", GifItem.Images.Empty(), false));
		gifs.add(new GifItem("4", GifItem.Images.Empty(), true));
		return gifs;
	}

	private OkHttpClient getOkHttpClient(final JsonObject data) {
		return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
			@Override
			public okhttp3.Response intercept(Chain chain) throws IOException {
				okhttp3.Response response;
				String search = chain.request().url().queryParameter("q");
				if ("batman".equals(search)) {
					response = new okhttp3.Response.Builder()
							.code(200)
							.message(data.toString())
							.body(ResponseBody.create(MediaType.parse("application/json"), data.toString()))
							.request(chain.request())
							.addHeader("content-type", "application/json")
							.protocol(Protocol.HTTP_1_0)
							.build();
				} else {
					response = new okhttp3.Response.Builder()
							.code(400)
							.request(chain.request())
							.protocol(Protocol.HTTP_1_0)
							.message("")
							.body(ResponseBody.create(MediaType.parse("application/json"), ""))
							.build();
				}
				return response;
			}
		}).build();

	}
}
