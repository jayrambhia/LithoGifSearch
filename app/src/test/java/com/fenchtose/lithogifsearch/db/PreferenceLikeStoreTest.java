package com.fenchtose.lithogifsearch.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.fenchtose.lithogifsearch.models.db.PreferenceLikeStore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PreferenceLikeStoreTest {

	@Mock Context context;
	@Mock SharedPreferences preferences;
	@Mock SharedPreferences.Editor editor;

	private PreferenceLikeStore likeStore;

	@SuppressLint("CommitPrefEdits")
	@Before
	public void setup() {
		when(context.getSharedPreferences(eq(PreferenceLikeStore.PREFERENCE_NAME),
				eq(Context.MODE_PRIVATE)))
				.thenReturn(preferences);
		when(preferences.edit()).thenReturn(editor);
		likeStore = new PreferenceLikeStore(context);
	}

	@Test
	public void like() {
		likeStore.setLiked("id", true);
		verify(editor, times(1)).putBoolean(eq("id"), eq(true));
		verify(editor, never()).remove(anyString());
		verify(editor, times(1)).apply();
	}

	@Test
	public void unlike() {
		likeStore.setLiked("id", false);
		verify(editor, never()).putBoolean(anyString(), eq(true));
		verify(editor, times(1)).remove(eq("id"));
		verify(editor, times(1)).apply();
	}

	@Test
	public void isLiked() {
		when(preferences.getBoolean(eq("id1"), eq(false))).thenReturn(true);
		when(preferences.getBoolean(eq("id2"), eq(false))).thenReturn(false);

		assertEquals(likeStore.isLiked("id1"), true);
		verify(preferences).getBoolean(eq("id1"), eq(false));

		assertEquals(likeStore.isLiked("id2"), false);
		verify(preferences).getBoolean(eq("id2"), eq(false));
	}
}
