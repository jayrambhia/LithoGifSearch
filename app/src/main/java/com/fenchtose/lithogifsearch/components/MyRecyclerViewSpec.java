package com.fenchtose.lithogifsearch.components;


import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.Row;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Recycler;
import com.facebook.litho.widget.RecyclerBinder;


@LayoutSpec
public class MyRecyclerViewSpec {

	@OnCreateLayout
	static ComponentLayout onCreateLayout(ComponentContext c, @Prop RecyclerBinder binder) {
		return Row.create(c)
				.child(Recycler.create(c)
						.binder(binder).build())
				.build();
	}

}
