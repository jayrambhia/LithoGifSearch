package com.fenchtose.lithogifsearch.events;

import com.facebook.litho.annotations.Event;
import com.fenchtose.lithogifsearch.models.GifItem;

@Event
public class GifSelectEvent {
	public GifItem gif;
}
