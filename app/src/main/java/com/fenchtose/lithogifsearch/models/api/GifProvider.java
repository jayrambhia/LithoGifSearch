package com.fenchtose.lithogifsearch.models.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fenchtose.lithogifsearch.models.GifItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GifProvider {

	private GifApi api;
	private final ResposneListener listener;

	public GifProvider(ResposneListener listener) {
		this.listener = listener;
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(GifApi.ENDPOINT)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		api = retrofit.create(GifApi.class);
	}

	public void search(@NonNull String query) {
		Call<JsonObject> call = api.search(query, GifApi.API_KEY);
		call.enqueue(new Callback<JsonObject>() {
			@Override
			public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
				Log.i("GifProvider", "response: " + response.body());
				JsonArray data = response.body().get("data").getAsJsonArray();
				List<GifItem> gifs = new ArrayList<>();
				for (int i=0; i<data.size(); i++) {
					JsonObject json = data.get(i).getAsJsonObject();
					gifs.add(new GifItem(json));
				}

				listener.onSuccess(gifs);
			}

			@Override
			public void onFailure(Call<JsonObject> call, Throwable t) {
				listener.onFailure(t);
			}
		});
	}

	public interface ResposneListener {
		void onSuccess(List<GifItem> gifs);
		void onFailure(Throwable t);
	}
}
