package com.fenchtose.lithogifsearch.base;

import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import java.lang.ref.WeakReference;

public class Presenter<T extends AppPath, V extends ComponentView> {

	private WeakReference<V> viewRef = new WeakReference<>(null);
	private final T path;

	public Presenter(T path) {
		this.path = path;
	}

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

	public T getPath() {
		return path;
	}

	@VisibleForTesting
	public V getViewForTest() {
		return viewRef.get();
	}
}
