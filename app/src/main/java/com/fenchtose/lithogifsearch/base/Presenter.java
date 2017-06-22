package com.fenchtose.lithogifsearch.base;

import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

public class Presenter<V extends ComponentView> {

	private WeakReference<V> viewRef = new WeakReference<>(null);

	@CallSuper
	public void attach(V view) {
		viewRef = new WeakReference<V>(view);
	}

	public void onAttached() {

	}

	@CallSuper
	public void detach(V view) {
		viewRef = new WeakReference<V>(null);
	}

	public void onDetached() {

	}

	@Nullable
	protected final V getView() {
		return viewRef.get();
	}

	public boolean canGoBack() {
		return true;
	}
}
