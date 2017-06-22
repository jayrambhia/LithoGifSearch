package com.fenchtose.lithogifsearch.features.full_screen;

import com.fenchtose.lithogifsearch.base.Presenter;
import com.fenchtose.lithogifsearch.models.db.LikeStore;

public class FullScreenPresenter extends Presenter<FullScreenView> implements FullScreenView.Callback {

	private final LikeStore likeStore;

	public FullScreenPresenter(LikeStore likeStore) {
		this.likeStore = likeStore;
	}

	@Override
	public void attach(FullScreenView view) {
		super.attach(view);
		view.setCallback(this);
	}

	@Override
	public void detach(FullScreenView view) {
		super.detach(view);
		view.setCallback(null);
	}

	@Override
	public void onGifLiked(String gifId, boolean isLiked) {
		likeStore.setLiked(gifId, isLiked);
	}
}
