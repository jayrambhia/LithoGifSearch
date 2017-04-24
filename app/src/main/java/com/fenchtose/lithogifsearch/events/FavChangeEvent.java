package com.fenchtose.lithogifsearch.events;

import com.facebook.litho.annotations.Event;

@Event
public class FavChangeEvent {
	public boolean isLiked;
	public String gifId;

	public FavChangeEvent(boolean isLiked, String gifId) {
		this.isLiked = isLiked;
		this.gifId = gifId;
	}
}
