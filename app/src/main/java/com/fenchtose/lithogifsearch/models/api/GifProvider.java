package com.fenchtose.lithogifsearch.models.api;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.fenchtose.lithogifsearch.models.GifItem;
import com.fenchtose.lithogifsearch.models.db.LikeStore;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GifProvider {

	private final GifApi api;
	private final LikeStore likeStore;
	private final Gson gson;

	public GifProvider(GifApi api, LikeStore store, Gson gson) {
		this.likeStore = store;
		this.api = api;
		this.gson = gson;
	}

	public void search(@NonNull String query, final ResponseListener listener) {
		Call<JsonObject> call = api.search(query, GifApi.API_KEY);
		call.enqueue(new Callback<JsonObject>() {
			@Override
			public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
				try {
					listener.onSuccess(parseResponse(response));
				} catch (Exception t) {
					listener.onFailure(t);
				}
			}

			@Override
			public void onFailure(Call<JsonObject> call, Throwable t) {
				listener.onFailure(t);
			}
		});
	}

	private List<GifItem> parseResponse(Response<JsonObject> response) throws Exception {
		JsonArray data = response.body().get("data").getAsJsonArray();
		List<GifItem> gifs = new ArrayList<>();
		for (int i=0; i<data.size(); i++) {
			JsonObject json = data.get(i).getAsJsonObject();
			json.addProperty("isLiked", likeStore.isLiked(json.get("id").getAsString()));
			gifs.add(gson.fromJson(json, GifItem.class));
		}

		return gifs;
	}

	@VisibleForTesting
	public void searchForTesting(@NonNull String query, final ResponseListener listener) {
		Call<JsonObject> call = api.search(query, GifApi.API_KEY);
		try {
			Response<JsonObject> response = call.execute();
			listener.onSuccess(parseResponse(response));
		} catch (Exception e) {
			listener.onFailure(e);
		}
	}

	public interface ResponseListener {
		void onSuccess(List<GifItem> gifs);
		void onFailure(Throwable t);
	}
}
