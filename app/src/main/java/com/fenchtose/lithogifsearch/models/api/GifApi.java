package com.fenchtose.lithogifsearch.models.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GifApi {

	String ENDPOINT = "http://api.giphy.com/v1/gifs/";
	String API_KEY = "dc6zaTOxFJmzC";

	@GET("search")
	Call<JsonObject> search(@Query("q") String query, @Query("api_key") String key);
}
