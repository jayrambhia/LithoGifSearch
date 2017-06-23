package com.fenchtose.lithogifsearch.base;

public abstract class AppPath<T extends Presenter, V extends ComponentView> {

	private T presenter;
	private V view;

	private T createOrGetPresenter() {
		if (presenter == null) {
			presenter = createPresenter();
		}

		return presenter;
	}

	private V createOrGetView() {
		if (view == null) {
			view = createView();
		}

		return view;
	}

	public boolean hasPresenter() {
		return presenter != null;
	}

	public final V addToRoute() {
		presenter = createOrGetPresenter();
		view = createOrGetView();
		presenter.attach(view);
		return view;
	}

	public final void onViewAdded() {
		presenter.onAttached();
	}

	public void removeFromRoute() {
		presenter.detach(view);
	}

	public final void onViewRemoved() {
		presenter.onDetached();
	}

	public V getView() {
		return view;
	}

	public T getPresenter() {
		return presenter;
	}

	protected abstract V createView();
	protected abstract T createPresenter();
}
