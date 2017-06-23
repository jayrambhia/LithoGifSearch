package com.fenchtose.lithogifsearch.models.db;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceLikeStore implements LikeStore {

	private final SharedPreferences preferences;

	public static final String PREFERENCE_NAME = "like_store";

	public PreferenceLikeStore(Context context) {
		preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	@Override
	public void setLiked(String id, boolean liked) {
		SharedPreferences.Editor editor = preferences.edit();

		if (liked) {
			editor.putBoolean(id, true);
		} else {
			editor.remove(id);
		}

		editor.apply();
	}

	@Override
	public boolean isLiked(String id) {
		return preferences.getBoolean(id, false);
	}

}
