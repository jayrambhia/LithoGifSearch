package com.fenchtose.lithogifsearch.base;

import com.facebook.litho.Component;

public interface ComponentView<V extends Component<?>> {
	V getComponent();
}
