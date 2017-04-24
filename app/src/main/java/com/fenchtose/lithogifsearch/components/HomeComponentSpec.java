package com.fenchtose.lithogifsearch.components;

import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.FromEvent;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.EditText;
import com.facebook.litho.widget.Recycler;
import com.facebook.litho.widget.RecyclerBinder;
import com.facebook.litho.widget.TextChangedEvent;
import com.facebook.yoga.YogaEdge;

@LayoutSpec
public class HomeComponentSpec {

	@OnCreateLayout
	static ComponentLayout onCreateLayout(ComponentContext c,
										  @Prop String hint,
										  @Prop RecyclerBinder binder) {
		return Column.create(c)
				.paddingDip(YogaEdge.ALL, 8)
				.child(getQueryComponent(c, hint))
				.child(getRecyclerComponent(c, binder))
				.build();
	}

	private static Component<EditText> getQueryComponent(ComponentContext c, String hint) {
		return EditText.create(c)
				.textSizeDip(16)
				.hint(hint)
				.textChangedEventHandler(HomeComponent.onQueryChanged(c))
				.build();
	}

	private static Component<Recycler> getRecyclerComponent(ComponentContext c, RecyclerBinder binder) {
		return Recycler.create(c)
				.binder(binder).build();
	}

	@OnEvent(TextChangedEvent.class)
	static void onQueryChanged(ComponentContext c, @Prop OnQueryUpdateListener listener, @FromEvent String text) {
		listener.onQueryUpdated(text);
	}

	public interface OnQueryUpdateListener {
		void onQueryUpdated(String query);
	}
}
