package com.fenchtose.lithogifsearch.models.db;


public interface LikeStore {
	void setLiked(String id, boolean liked);
	boolean isLiked(String id);
}
