package com.fenchtose.lithogifsearch.events;

import com.facebook.litho.annotations.Event;

@Event
public class LikeChangeEvent {
	public String gifId;
	public boolean isLiked;
}
