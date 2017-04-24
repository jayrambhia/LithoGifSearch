package com.fenchtose.lithogifsearch.models;

import com.google.gson.JsonObject;

public class GifItem {

	private final String id;
	private final String image;
	private final String small;
	private final int width;
	private final int height;
	private final boolean isLiked;

	public GifItem(JsonObject json, boolean isLiked) {
		this.id = json.get("id").getAsString();
		this.small = json.get("images").getAsJsonObject().get("fixed_height_downsampled").getAsJsonObject().get("url").getAsString();
		JsonObject image = json.get("images").getAsJsonObject().get("original").getAsJsonObject();
		this.image = image.get("url").getAsString();
		this.width = image.get("width").getAsInt();
		this.height = image.get("height").getAsInt();
		this.isLiked = isLiked;
	}

	public GifItem(GifItem gif, boolean isLiked) {
		this.id = gif.getId();
		this.image = gif.getImage();
		this.small = gif.getSmall();
		this.width = gif.getWidth();
		this.height = gif.getHeight();
		this.isLiked = isLiked;
	}

	public String getId() {
		return id;
	}

	public String getImage() {
		return image;
	}

	public String getSmall() {
		return small;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isLiked() {
		return isLiked;
	}

}
