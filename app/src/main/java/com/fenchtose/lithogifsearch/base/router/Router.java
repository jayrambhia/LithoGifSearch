package com.fenchtose.lithogifsearch.base.router;


import com.facebook.litho.LithoView;
import com.fenchtose.lithogifsearch.base.AppPath;
import com.fenchtose.lithogifsearch.base.ComponentView;
import com.fenchtose.lithogifsearch.base.Presenter;

import java.util.Stack;

public class Router {

	private final LithoView root;
	private final Stack<AppPath> history;
	private RouterCallback callback;

	private Router(LithoView root) {
		this.root = root;
		history = new Stack<>();
	}

	public static Router with(LithoView view) {
		return new Router(view);
	}

	public void go(AppPath path) {
		if (history.size() >= 1) {
			AppPath top = history.peek();
			if (top.equals(path)) {
				return;
			}
		}

		_move(path);
		history.push(path);
	}

	public boolean onBackRequested() {
		if (history.empty()) {
//			Log.e(TAG, "history is empty. We can't go back")
			return true;
		}

		boolean canTopGoBack = _canTopGoBack();
		if (canTopGoBack) {
			if (history.size() == 1) {
				return true;
			}

			goBack();
		}

		return false;
	}

	public boolean goBack() {

		if (history.size() > 1) {
			_moveBack();
			return true;
		}

		return false;
	}

	private boolean _canTopGoBack() {
		Presenter presenter = _getTopPresenter();
		return presenter == null || presenter.canGoBack();
	}

	private Presenter _getTopPresenter() {
		return history.peek().getPresenter();
	}

	private ComponentView _getTopView()  {
		return history.peek().getView();
	}

	private void _move(AppPath path) {
		ComponentView view = path.addToRoute();
		root.setComponentAsync(view.getComponent());
		path.onViewAdded();

		if (callback != null) {
			callback.movedTo(path);
		}
	}

	private void _moveBack() {
		AppPath path = history.pop();
		path.removeFromRoute();

		if (callback != null) {
			callback.removed(path);
		}

		if (!history.empty()) {
			AppPath top = history.peek();
			if (top != null) {
				_move(top);
			}
		}

		path.onViewRemoved();
	}

	public interface RouterCallback {
		void movedTo(AppPath path);
		void removed(AppPath path);
	}
}
