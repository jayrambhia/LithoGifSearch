package com.fenchtose.lithogifsearch.models;

import android.support.annotation.VisibleForTesting;

import com.google.gson.annotations.SerializedName;

public class GifItem {

	public final String id;
	public final Images images;
	private final boolean isLiked;

	// For gson
	private GifItem() {
		id = "";
		images = Images.Empty();
		isLiked = false;
	}

	@VisibleForTesting
	public GifItem(String id, Images images, boolean isLiked) {
		this.id = id;
		this.images = images;
		this.isLiked = isLiked;
	}

	public String getId() {
		return id;
	}

	public String getImage() {
		return images.original.url;
	}

	public String getSmall() {
		return images.small.url;
	}

	public int getWidth() {
		return images.original.width;
	}

	public int getHeight() {
		return images.original.height;
	}

	public boolean isLiked() {
		return isLiked;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof GifItem)) {
			return false;
		}
		GifItem gif = (GifItem)obj;
		return id.equals(gif.id) && images.equals(gif.images);
	}

	public static class Images {
		public final Image original;
		@SerializedName("fixed_height_downsampled")
		public final Image small;

		public Images() {
			original = Image.Empty();
			small = Image.Empty();
		}

		public static Images Empty() {
			return new Images();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof Images)) {
				return false;
			}
			Images imgs = (Images)obj;
			return original.equals(imgs.original) && small.equals(imgs.small);
		}
	}

	public static class Image {
		public final String url;
		public final int width;
		public final int height;

		public Image() {
			this.url = "";
			this.width = 0;
			this.height = 0;
		}

		public static Image Empty() {
			return new Image();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof Image)) {
				return false;
			}
			Image img = (Image)obj;
			return url.equals(img.url) && width == img.width && height == img.height;
		}
	}
}
