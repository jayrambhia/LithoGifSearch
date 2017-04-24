package com.fenchtose.lithogifsearch.events;

import com.facebook.litho.annotations.Event;

@Event
public class LikeChangeEvent {
	public boolean isLiked;
	public String gifId;
}
